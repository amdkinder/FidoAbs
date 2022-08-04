package uz.devops.fido.abs.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uz.devops.fido.abs.config.FidoAbsConfiguration;
import uz.devops.fido.abs.config.FidoAbsProperties;
import uz.devops.fido.abs.model.*;
import uz.devops.fido.abs.service.FidoAbsService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

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
        }
        httpHeaders.set("requestId", String.valueOf(System.currentTimeMillis()));
        return httpHeaders;
    }

    @Override
    public ResultDTO<ClientInfoDTO> getClientInfo(String clientId) {
        log.debug("Request to get client info by client id: {}", clientId);
        var result = new ResultDTO<ClientInfoDTO>();
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
                result.setSuccess(true);

            } else {
                log.debug("");
                result.setSuccess(false);
                result.setMsg("RESPONSE IS NOT SUCCESS");
            }
        } catch (Exception e) {
            result.setException(e);
            result.setMsg(e.getMessage());
            result.setSuccess(false);
        }
        log.debug("RESULT to get client info by client id: {}, result: {}", clientId, result);
        return result;
    }

    @Override
    public ResultDTO<List<AccountDTO>> getActiveAccounts(String clientId) {
        log.debug("Request to get client accounts by client id: {}", clientId);
        var result = new ResultDTO<List<AccountDTO>>();
        var request = new HttpEntity<>(getHttpHeaders());
        try {
            var response = restTemplate.exchange(String.format("/1.0.0/get-active-accounts/%s", clientId), HttpMethod.GET, request, AccountResDTO.class);
            log.debug("Response from abs for get client accounts: {}, client id: {}", response, clientId);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null && response.getBody().getResponseBody() != null) {
                result.setData(response.getBody().getResponseBody());
                result.setSuccess(true);
                result.setCode(response.getBody().getCode());
                result.setMsg(response.getBody().getMsg());
            } else {
                log.debug("RESPONSE IS NOT SUCCESS");
                result.setSuccess(false);
                result.setMsg("RESPONSE IS NOT SUCCESS");
            }
        } catch (Exception e) {
            result.setException(e);
            result.setMsg(e.getMessage());
            result.setSuccess(false);
        }
        log.debug("RESULT to get client info by client id: {}, result: {}", clientId, result);
        return result;
    }

    @Override
    public ResultDTO<TransactionResultDTO.CreatedTransaction> createTransaction(TransactionDTO transactionDTO) {
        log.debug("Request to create document: {}", transactionDTO);
        var result = new ResultDTO<TransactionResultDTO.CreatedTransaction>();
        var request = new HttpEntity<>(new TransactionReqDTO(transactionDTO), getHttpHeaders());
        try {
            var response = restTemplate.exchange("/1.0.0/transactions", HttpMethod.POST, request, TransactionResultDTO.class);
            log.debug("Response to create document: {}", response);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null && response.getBody().getCreatedDocument() != null) {
                TransactionResultDTO.CreatedTransaction createdDoc = null;
                if (response.getBody().getCreatedDocument().size() > 0) {
                    createdDoc = response.getBody().getCreatedDocument().get(0);
                }
                result.setData(createdDoc);
                result.setSuccess(true);
            } else {
                log.debug("RESPONSE IS NOT SUCCESS");
                result.setSuccess(false);
                result.setMsg("RESPONSE IS NOT SUCCESS");
            }
        } catch (Exception e) {
            result.setException(e);
            result.setMsg(e.getMessage());
            result.setSuccess(false);
        }
        log.debug("Result to create document: {}", result);
        return result;
    }

    @Override
    public ResultDTO<TransactionDTO> getTransaction(String transactionId) {
        log.debug("Request to get document by transaction id: {}", transactionId);
        var result = new ResultDTO<TransactionDTO>();
        var request = new HttpEntity<>(getHttpHeaders());

        try {
            var response = restTemplate.exchange(String.format("/1.0.0/transactions/%s", transactionId), HttpMethod.GET, request, TransactionDTO.class);
            log.debug("Response to get document: {}", response);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                result.setData(response.getBody());
                result.setSuccess(true);
            } else {
                log.debug("RESPONSE IS NOT SUCCESS");
                result.setSuccess(false);
                result.setMsg("RESPONSE IS NOT SUCCESS");
            }
        } catch (Exception e) {
            result.setException(e);
            result.setMsg(e.getMessage());
            result.setSuccess(false);
        }
        log.debug("Result to get document: {}", result);
        return result;
    }

    @Override
    public ResultDTO<?> deleteTransactionById(String transactionId) {
        log.debug("Request to cancel transaction by id: {}", transactionId);
        var result = new ResultDTO<>();
        var request = new HttpEntity<>(getHttpHeaders());
        try {
            var response = restTemplate.exchange(String.format("/1.0.0/transactions/%s", transactionId), HttpMethod.DELETE, request, ResultDTO.class);
            log.debug("Response to cancel transaction by id: {}", response);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                result = response.getBody();
                result.setSuccess(true);
            } else {
                log.debug("RESPONSE IS NOT SUCCESS");
                result.setSuccess(false);
                result.setMsg("RESPONSE IS NOT SUCCESS");
            }
        } catch (Exception e) {
            result.setException(e);
            result.setMsg(e.getMessage());
            result.setSuccess(false);
        }
        log.debug("Result to cancel transaction: {}", result);
        return result;
    }

    @Override
    public ResultDTO<List<ExchangeRateDTO>> getExchangeRates(ExchangeRateCriteria criteria) {
        log.debug("Request to get exchange rates by criteria: {}", criteria);
        var result = new ResultDTO<List<ExchangeRateDTO>>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        var request = new HttpEntity<>(getHttpHeaders());
        var uri = UriComponentsBuilder.fromUriString(fidoAbsProperties.getConfig().getBaseUri() + "/1.0.0/international-card/get-list-exchange-rates")
            .queryParam("dateCross", criteria.getDateCross().format(formatter))
            .queryParam("currencyCode", criteria.getCurrencyCode())
            .build();
        try {
            var response = restTemplate.exchange(uri.toUri(), HttpMethod.GET, request, ExchangeRateDTO[].class);
            log.debug("Response to get exchange rates: {}", response);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                result = new ResultDTO<>(List.of(response.getBody()));
                result.setSuccess(true);
            } else {
                log.debug("RESPONSE IS NOT SUCCESS");
                result.setSuccess(false);
                result.setMsg("RESPONSE IS NOT SUCCESS");
            }
        } catch (Exception e) {
            result.setException(e);
            result.setMsg(e.getMessage());
            result.setSuccess(false);
        }
        log.debug("Result to exchange rates: {}", result);
        return result;
    }

    @Override
    public ResultDTO<ConversionResultDTO> internationalConversion(ConversionDTO conversionDTO) {
        log.debug("Request to international conversion: {}", conversionDTO);
        var result = new ResultDTO<ConversionResultDTO>();
        var request = new HttpEntity<>(conversionDTO, getHttpHeaders());
        try {
            var response = restTemplate.exchange("/1.0.0/international-card/conversion", HttpMethod.POST, request, ConversionResultDTO.class);
            log.debug("Response to international conversion: {}", response);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                result.setData(response.getBody());
                result.setSuccess(true);
            } else if (response.getStatusCode().is4xxClientError()) {
                log.debug("Unauthorized client");
                result.setSuccess(false);
                result.setMsg("Unauthorized client");
            } else {
                log.debug("RESPONSE IS NOT SUCCESS");
                result.setSuccess(false);
                result.setMsg("RESPONSE IS NOT SUCCESS");
            }
        } catch (Exception e) {
            result.setException(e);
            result.setMsg(e.getMessage());
            result.setSuccess(false);
        }
        log.debug("Result to international conversion: {}", result);
        return result;
    }


}
