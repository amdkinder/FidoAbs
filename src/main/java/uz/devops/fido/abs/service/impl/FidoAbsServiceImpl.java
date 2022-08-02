package uz.devops.fido.abs.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uz.devops.fido.abs.config.FidoAbsConfiguration;
import uz.devops.fido.abs.model.*;
import uz.devops.fido.abs.service.FidoAbsService;

import java.util.List;

import static uz.devops.fido.abs.service.FidoAbsService.NAME;

@Slf4j
@Service(NAME)
@ConditionalOnProperty(prefix = "fido-abs", name = "simulate", havingValue = "false")
public class FidoAbsServiceImpl implements FidoAbsService {

    private final RestTemplate restTemplate;

    public FidoAbsServiceImpl(@Qualifier(FidoAbsConfiguration.ABS_REST_TEMPLATE) RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResultDTO<ClientInfoDTO> getClientInfo(String clientId) {
        log.debug("Request to get client info by client id: {}", clientId);
        var result = new ResultDTO<ClientInfoDTO>();
        try {
            var response = restTemplate.getForEntity(String.format("/1.0.0/get-customer/%s", clientId), ClientInfoResDTO.class);
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
        try {
            var response = restTemplate.getForEntity(String.format("/1.0.0/get-active-accounts/%s", clientId), AccountResDTO.class);
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
        try {
            var response = restTemplate.postForEntity("/1.0.0/transactions", transactionDTO, TransactionResultDTO.class);
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
        try {
            var response = restTemplate.getForEntity(String.format("/1.0.0/transactions/%s", transactionId), TransactionDTO.class);
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
        try {
            var response = restTemplate.getForEntity(String.format("/1.0.0/transactions/%s", transactionId), ResultDTO.class);
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
        var uri = UriComponentsBuilder.fromHttpUrl("/1.0.0/international-card/get-list-exchange-rates").queryParam("dateCross", criteria.getDateCross()).queryParam("currencyCode", criteria.getCurrencyCode()).build();
        try {
            var response = restTemplate.postForEntity(uri.toUri(), criteria, ExchangeRateDTO[].class);
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
        try {
            var response = restTemplate.postForEntity("/1.0.0/international-card/conversion", conversionDTO, ConversionResultDTO.class);
            log.debug("Response to international conversion: {}", response);
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
        log.debug("Result to international conversion: {}", result);
        return result;
    }


}
