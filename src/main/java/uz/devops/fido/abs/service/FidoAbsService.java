package uz.devops.fido.abs.service;

import uz.devops.fido.abs.model.*;
import uz.javlon.commons.result.HasData;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public interface FidoAbsService {

    String NAME = "fidoAbsService";

    /**
     * Поиск клиента в системе ИАБС по clientId (Физ)
     *
     * @param clientId (физ) клиент ид Размерность (12)
     * @return Информация клиента
     */
    HasData<ClientInfoDTO> getClientInfo(@Valid @Size(max = 12) @NotBlank String clientId);


    /**
     * Список активных счетов клиента (Физ+Юр)
     *
     * @param clientId
     * @return
     */
    HasData<List<AccountDTO>> getActiveAccounts(@Valid @Size(max = 12) @NotBlank String clientId);

    /**
     * Формирование разовых платежей (Физ+Юр)
     *
     * @param transactionDTO
     * @return
     */
//    HasData<TransactionResultDTO.CreatedTransaction> createTransaction(@Valid @NotNull AbsTranDTO transactionDTO);

    /**
     *
     * @param transactionDTO
     * @return
     */
    HasData<List<TransactionResultDTO.CreatedTransaction>> createTransactions(@Valid @NotNull AbsTranDTO ...transactionDTO);

    /**
     * Получить состояние проводки (Физ+Юр)
     *
     * @param transactionId
     * @return
     */
    HasData<AbsTranDTO> getTransaction(@NotBlank @NotNull String transactionId);

    /**
     * Отмена транзакции (Физ+Юр)
     *
     * @param transactionId
     * @return
     */
    HasData<?> cancelTransaction(@Valid @NotNull String transactionId);

    /**
     * Список курс валют
     *
     * @param criteria
     * @return
     */
    HasData<List<ExchangeRateDTO>> getExchangeRates(@Valid @NotNull ExchangeRateCriteria criteria);

    /**
     * Конвертация
     *
     * @param conversionDTO
     * @return
     */
    HasData<ConversionResultDTO> internationalConversion(@Valid @NotNull ConversionDTO conversionDTO);

}
