package uz.devops.fido.abs.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import uz.devops.fido.abs.model.*;
import uz.devops.fido.abs.service.FidoAbsService;

import java.util.List;

import static uz.devops.fido.abs.service.FidoAbsService.NAME;

@Slf4j
@Service(NAME)
@ConditionalOnProperty(prefix = "fido-abs", name = "simulate", havingValue = "true", matchIfMissing = true)
public class DummyFidoAbsServiceImpl implements FidoAbsService {

    public DummyFidoAbsServiceImpl() {
        log.debug("############### FidoAbs simulation is ON ###############");
    }


    @Override
    public ResultDTO<ClientInfoDTO> getClientInfo(String clientId) {
        var clientInfo = DummyData.clientInfo();
        return new ResultDTO<>(clientInfo);
    }

    @Override
    public ResultDTO<List<AccountDTO>> getActiveAccounts(String clientId) {
        var accountDTO = DummyData.accountDTO();
        return new ResultDTO<>(List.of(accountDTO));
    }

    @Override
    public ResultDTO<TransactionResultDTO.CreatedTransaction> createTransaction(TransactionDTO transactionDTO) {
        return new ResultDTO<>(DummyData.createdDocument());
    }

    @Override
    public ResultDTO<TransactionDTO> getTransaction(String transactionId) {
        var documentDTO = DummyData.transactionDTO();
        return new ResultDTO<>(documentDTO);
    }

    @Override
    public ResultDTO<?> deleteTransactionById(String transactionId) {
        return ResultDTO.builder()
            .msg("Успешно")
            .code(0)
            .success(true)
            .build();
    }

    @Override
    public ResultDTO<List<ExchangeRateDTO>> getExchangeRates(ExchangeRateCriteria criteria) {
        return new ResultDTO<>(List.of(DummyData.exchangeRateDTO()));
    }

    @Override
    public ResultDTO<ConversionResultDTO> internationalConversion(ConversionDTO conversionDTO) {
        return new ResultDTO<>(DummyData.conversionResultDTO());
    }


}
