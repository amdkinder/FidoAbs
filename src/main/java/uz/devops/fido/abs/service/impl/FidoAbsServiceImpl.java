package uz.devops.fido.abs.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uz.devops.fido.abs.config.FidoAbsConfiguration;
import uz.devops.fido.abs.config.FidoAbsProperties;
import uz.devops.fido.abs.model.*;
import uz.devops.fido.abs.service.FidoAbsService;
import uz.javlon.commons.result.CommonResultData;
import uz.javlon.commons.result.HasData;
import uz.javlon.commons.result.HasResult;

import java.net.SocketTimeoutException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static uz.devops.fido.abs.service.FidoAbsService.NAME;

@Slf4j
@Service(NAME)
@ConditionalOnProperty(prefix = "fido-abs", name = "simulate", havingValue = "false")
public class FidoAbsServiceImpl implements FidoAbsService {

    private final RestTemplate restTemplate;
    private final FidoAbsProperties fidoAbsProperties;
    private final AuthorizationService authorizationService;

    private HttpHeaders httpHeaders;

    @Value("${fido-abs.config.base-uri}")
    private String baseUri;

    public FidoAbsServiceImpl(@Qualifier(FidoAbsConfiguration.ABS_REST_TEMPLATE) RestTemplate restTemplate, FidoAbsProperties fidoAbsProperties, AuthorizationService authorizationService) {
        this.restTemplate = restTemplate;
        this.fidoAbsProperties = fidoAbsProperties;
        this.authorizationService = authorizationService;
    }

    //TODO must make header
    public HttpHeaders getHttpHeaders() {
        if (httpHeaders == null) {
            httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setBearerAuth(authorizationService.getToken());
            httpHeaders.set("Accept-Language", "ru");
            httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        }
        httpHeaders.set("requestId", String.valueOf(System.currentTimeMillis()));
        return httpHeaders;
    }

    @Override
    public HasData<ClientInfoDTO> getClientInfo(String clientId) {
        log.debug("Request to get client info by client id: {}", clientId);
        var result = new CommonResultData<ClientInfoDTO>();
        var request = new HttpEntity<>(getHttpHeaders());
        try {
            var response = restTemplate.exchange(String.format("/1.0.0/get-customer/%s", clientId), HttpMethod.GET, request, ClientInfoResDTO.class);
            log.debug("Response from abs for get client info: {}, client id: {}", response, clientId);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null && response.getBody().getResponse() != null) {
                ClientInfoDTO clientInfoDTO = null;
                if (response.getBody().getResponse().size() > 0) {
                    clientInfoDTO = response.getBody().getResponse().get(0);
                }
                result.setData(clientInfoDTO);
                result.setStatus(HasResult.SUCCESS);

            } else {
                log.debug("");
                result.setStatus(HasResult.UNKNOWN_ERROR);
                result.setDetails("RESPONSE IS NOT SUCCESS");
            }
        } catch (ResourceAccessException e) {
            if (e.getCause() instanceof SocketTimeoutException) {
                log.error("Timeout error occurred : {}", e.getLocalizedMessage());
                return new CommonResultData<>(HasResult.TIMEOUT, null, e.getLocalizedMessage());
            }
            log.error("Resource access error occurred : {}", e.getLocalizedMessage());
            return new CommonResultData<>(HasResult.UNKNOWN_ERROR, null, NestedExceptionUtils.getMostSpecificCause(e).getLocalizedMessage());
        } catch (Exception e) {
            log.error("Can not exchange data from abs: {}", e.getMessage());
            return new CommonResultData<>(HasResult.UNKNOWN_ERROR, null, NestedExceptionUtils.getMostSpecificCause(e).getLocalizedMessage());
        }
        log.debug("RESULT to get client info by client id: {}, result: {}", clientId, result);
        return result;
    }

    @Override
    public HasData<List<AccountDTO>> getActiveAccounts(String clientId) {
        log.debug("Request to get client accounts by client id: {}", clientId);
        var result = new CommonResultData<List<AccountDTO>>();
        var request = new HttpEntity<>(getHttpHeaders());
        try {
            var response = restTemplate.exchange(String.format("/1.0.0/get-active-accounts/%s", clientId), HttpMethod.GET, request, AccountResDTO.class);
            log.debug("Response from abs for get client accounts: {}, client id: {}", response, clientId);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null && response.getBody().getResponseBody() != null) {
                result.setData(response.getBody().getResponseBody());
                result.setStatus(HasResult.SUCCESS);
                result.setDetails(response.getBody().getMsg());
            } else {
                log.debug("RESPONSE IS NOT SUCCESS");
                result.setStatus(HasResult.UNKNOWN_ERROR);
                result.setDetails("RESPONSE IS NOT SUCCESS");
            }
        } catch (ResourceAccessException e) {
            if (e.getCause() instanceof SocketTimeoutException) {
                log.error("Timeout error occurred : {}", e.getLocalizedMessage());
                return new CommonResultData<>(HasResult.TIMEOUT, null, e.getLocalizedMessage());
            }
            log.error("Resource access error occurred : {}", e.getLocalizedMessage());
            return new CommonResultData<>(HasResult.UNKNOWN_ERROR, null, NestedExceptionUtils.getMostSpecificCause(e).getLocalizedMessage());
        } catch (Exception e) {
            log.error("Can not exchange data from abs: {}", e.getMessage());
            return new CommonResultData<>(HasResult.UNKNOWN_ERROR, null, NestedExceptionUtils.getMostSpecificCause(e).getLocalizedMessage());
        }
        log.debug("RESULT to get client info by client id: {}, result: {}", clientId, result);
        return result;
    }

    public HasData<TransactionResultDTO.CreatedTransaction> createTransaction(AbsTranDTO transactionDTO) {
        var result = createTransactions(List.of(transactionDTO));
        return new CommonResultData<>(HasResult.SUCCESS, result.getData().get(0));
    }

    @Override
    public HasData<List<TransactionResultDTO.CreatedTransaction>> createTransactions(List<AbsTranDTO> tranList) {
        log.debug("Request to create document: {}", tranList);
        var result = new CommonResultData<List<TransactionResultDTO.CreatedTransaction>>();
        var request = new HttpEntity<>(new TransactionReqDTO(tranList), getHttpHeaders());
        try {
            var response = restTemplate.exchange(
                "/1.0.0/transactions",
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<ResultDTO<TransactionResultDTO>>() {
                });
            log.debug("Response to create document: {}", response);
            if (response.getStatusCode().is2xxSuccessful()
                && response.getBody() != null
                && response.getBody().getResponseBody() != null
                && response.getBody().getResponseBody().getCreatedDocuments() != null) {
                var tranResults = response.getBody().getResponseBody().getCreatedDocuments();
                result.setData(tranResults);
                result.setStatus(HasResult.SUCCESS);
            } else {
                log.debug("RESPONSE IS NOT SUCCESS");
                result.setStatus(HasResult.UNKNOWN_ERROR);
                result.setDetails("RESPONSE IS NOT SUCCESS");
            }
        } catch (ResourceAccessException e) {
            if (e.getCause() instanceof SocketTimeoutException) {
                log.error("Timeout error occurred : {}", e.getLocalizedMessage());
                return new CommonResultData<>(HasResult.TIMEOUT, null, e.getLocalizedMessage());
            }
            log.error("Resource access error occurred : {}", e.getLocalizedMessage());
            return new CommonResultData<>(HasResult.UNKNOWN_ERROR, null, NestedExceptionUtils.getMostSpecificCause(e).getLocalizedMessage());
        } catch (Exception e) {
            log.error("Can not exchange data from abs: {}", e.getMessage());
            return new CommonResultData<>(HasResult.UNKNOWN_ERROR, null, NestedExceptionUtils.getMostSpecificCause(e).getLocalizedMessage());
        }
        log.debug("Result to create document: {}", result);
        return result;
    }

    @Override
    public HasData<AbsTranDTO> getTransaction(String transactionId) {
        log.debug("Request to get document by transaction id: {}", transactionId);
        var result = new CommonResultData<AbsTranDTO>();
        var request = new HttpEntity<>(getHttpHeaders());

        try {
            var response = restTemplate.exchange(
                String.format("/1.0.0/transactions/%s", transactionId),
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<ResultDTO<AbsTranDTO>>() {
                });
            log.debug("Response to get document: {}", response);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                result.setData(response.getBody().getResponseBody());
                result.setStatus(HasResult.SUCCESS);
            } else {
                log.debug("RESPONSE IS NOT SUCCESS");
                result.setStatus(HasResult.UNKNOWN_ERROR);
                result.setDetails("RESPONSE IS NOT SUCCESS");
            }
        } catch (ResourceAccessException e) {
            if (e.getCause() instanceof SocketTimeoutException) {
                log.error("Timeout error occurred : {}", e.getLocalizedMessage());
                return new CommonResultData<>(HasResult.TIMEOUT, null, e.getLocalizedMessage());
            }
            log.error("Resource access error occurred : {}", e.getLocalizedMessage());
            return new CommonResultData<>(HasResult.UNKNOWN_ERROR, null, NestedExceptionUtils.getMostSpecificCause(e).getLocalizedMessage());
        } catch (Exception e) {
            log.error("Can not exchange data from abs: {}", e.getMessage());
            return new CommonResultData<>(HasResult.UNKNOWN_ERROR, null, NestedExceptionUtils.getMostSpecificCause(e).getLocalizedMessage());
        }
        log.debug("Result to get document: {}", result);
        return result;
    }

    @Override
    public HasData<ResultDTO<?>> cancelTransaction(String transactionId) {
        log.debug("Request to cancel transaction by id: {}", transactionId);
        var result = new CommonResultData<ResultDTO<?>>();
        var request = new HttpEntity<>(getHttpHeaders());
        try {
            var response = restTemplate.exchange(String.format("/1.0.0/transactions/%s", transactionId), HttpMethod.DELETE, request, ResultDTO.class);
            log.debug("Response to cancel transaction by id: {}", response);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                result.setData(response.getBody());
                var success = 0 == response.getBody().getCode();
                result.setStatus(success ? HasResult.SUCCESS : HasResult.UNKNOWN_ERROR);
            } else {
                log.debug("RESPONSE IS NOT SUCCESS");
                result.setStatus(HasResult.UNKNOWN_ERROR);
                result.setDetails("RESPONSE IS NOT SUCCESS");
            }
        } catch (ResourceAccessException e) {
            if (e.getCause() instanceof SocketTimeoutException) {
                log.error("Timeout error occurred : {}", e.getLocalizedMessage());
                return new CommonResultData<>(HasResult.TIMEOUT, null, e.getLocalizedMessage());
            }
            log.error("Resource access error occurred : {}", e.getLocalizedMessage());
            return new CommonResultData<>(HasResult.UNKNOWN_ERROR, null, NestedExceptionUtils.getMostSpecificCause(e).getLocalizedMessage());
        } catch (Exception e) {
            log.error("Can not exchange data from abs: {}", e.getMessage());
            return new CommonResultData<>(HasResult.UNKNOWN_ERROR, null, NestedExceptionUtils.getMostSpecificCause(e).getLocalizedMessage());
        }
        log.debug("Result to cancel transaction: {}", result);
        return result;
    }

    @Override
    public HasData<List<ExchangeRateDTO>> getExchangeRates(ExchangeRateCriteria criteria) {
        log.debug("Request to get exchange rates by criteria: {}", criteria);
        var result = new CommonResultData<List<ExchangeRateDTO>>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        var request = new HttpEntity<>(getHttpHeaders());
        var uri = UriComponentsBuilder.fromUriString(fidoAbsProperties.getConfig().getBaseUri() + "/1.0.0/international-card/get-list-exchange-rates").queryParam("dateCross", criteria.getDateCross().format(formatter)).queryParam("currencyCode", criteria.getCurrencyCode()).build();
        try {
            var response = restTemplate.exchange(
                uri.toUri(),
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<ResultDTO<ExchangeResultDTO<List<ExchangeRateDTO>>>>() {
                });
            log.debug("Response to get exchange rates: {}", response);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                result.setData(response.getBody().getResponseBody().getData());
                result.setStatus(HasResult.SUCCESS);
            } else {
                log.debug("RESPONSE IS NOT SUCCESS");
                result.setStatus(HasResult.UNKNOWN_ERROR);
                result.setDetails("RESPONSE IS NOT SUCCESS");
            }
        } catch (ResourceAccessException e) {
            if (e.getCause() instanceof SocketTimeoutException) {
                log.error("Timeout error occurred : {}", e.getLocalizedMessage());
                return new CommonResultData<>(HasResult.TIMEOUT, null, e.getLocalizedMessage());
            }
            log.error("Resource access error occurred : {}", e.getLocalizedMessage());
            return new CommonResultData<>(HasResult.UNKNOWN_ERROR, null, NestedExceptionUtils.getMostSpecificCause(e).getLocalizedMessage());
        } catch (Exception e) {
            log.error("Can not exchange data from abs: {}", e.getMessage());
            return new CommonResultData<>(HasResult.UNKNOWN_ERROR, null, NestedExceptionUtils.getMostSpecificCause(e).getLocalizedMessage());
        }
        log.debug("Result to exchange rates: {}", result);
        return result;
    }

    @Override
    public HasData<ConversionResultDTO> internationalConversion(ConversionDTO conversionDTO) {
        log.debug("Request to international conversion: {}", conversionDTO);
        var result = new CommonResultData<ConversionResultDTO>();
        var request = new HttpEntity<>(conversionDTO, getHttpHeaders());
        try {
            var response = restTemplate.exchange(
                "/1.0.0/international-card/conversion",
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<ResultDTO<ConversionResultDTO>>() {
                });

            log.debug("Response to international conversion: {}", response);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                result.setData(response.getBody().getResponseBody());
                result.setStatus(response.getBody().toStatus());
            } else {
                log.debug("RESPONSE IS NOT SUCCESS");
                result.setStatus(HasResult.UNKNOWN_ERROR);
                result.setDetails("RESPONSE IS NOT SUCCESS");
            }
        } catch (ResourceAccessException e) {
            if (e.getCause() instanceof SocketTimeoutException) {
                log.error("Timeout error occurred : {}", e.getLocalizedMessage());
                return new CommonResultData<>(HasResult.TIMEOUT, null, e.getLocalizedMessage());
            }
            log.error("Resource access error occurred : {}", e.getLocalizedMessage());
            return new CommonResultData<>(HasResult.UNKNOWN_ERROR, null, NestedExceptionUtils.getMostSpecificCause(e).getLocalizedMessage());
        } catch (Exception e) {
            log.error("Can not exchange data from abs: {}", e.getMessage());
            return new CommonResultData<>(HasResult.UNKNOWN_ERROR, null, NestedExceptionUtils.getMostSpecificCause(e).getLocalizedMessage());
        }
        log.debug("Result to international conversion: {}", result);
        return result;
    }


}
