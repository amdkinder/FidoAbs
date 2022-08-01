package uz.devops.fido.abs.service;

import uz.devops.fido.abs.model.*;

import java.util.List;

public interface FidoAbsService {

    String NAME = "fidoAbsService";

    /**
     * Поиск клиента в системе ИАБС по clientId (Физ)
     *
     * @param clientId (физ) клиент ид
     * @return Информация клиента
     */
    ClientInfoResDTO getClientInfo(String clientId);

    /**
     * Список активных счетов клиента (Физ+Юр)
     *
     * @param clientId
     * @return
     */
    AccountResDTO<List<AccountDTO>> getActiveAccounts(String clientId);

    /**
     * Формирование разовых платежей (Физ+Юр)
     *
     * @param documentDTO
     * @return
     */
    DocumentResultDTO createDocument(DocumentDTO documentDTO);

    /**
     * Получить состояние проводки (Физ+Юр)
     *
     * @param transactionId
     * @return
     */
    DocumentDTO getDocument(String transactionId);

    /**
     * Отмена транзакции (Физ+Юр)
     *
     * @param transactionId
     * @return
     */
    Boolean deleteTransactionById(String transactionId);

    /**
     * Список курс валют
     *
     * @param criteria
     * @return
     */
    List<ExchangeRateDTO> getExchangeRates(ExchangeRateCriteria criteria);

    /**
     * Конвертация
     *
     * @param conversionDTO
     * @return
     */
    ConversionResultDTO internationalConversion(ConversionDTO conversionDTO);

}
