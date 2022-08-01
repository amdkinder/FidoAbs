package uz.devops.fido.abs.service;

import uz.devops.fido.abs.model.*;

import java.util.List;

public interface FidoAbsService {

    String NAME = "fidoAbsService";

    ClientInfoDTO getClientInfo(String clientId);

    List<AccountDTO> getActiveAccounts(String clientId);

    DocumentResultDTO  createDocument(DocumentDTO documentDTO);

    DocumentDTO getDocument(String transactionId);

    Boolean deleteTransactionById(String transactionId);

    List<ExchangeRateDTO> getExchangeRates(ExchangeRateCriteria criteria);

    ConversionResultDTO internationalConversion(ConversionDTO conversionDTO);

}
