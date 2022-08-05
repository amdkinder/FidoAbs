package uz.devops.fido.abs.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import uz.devops.fido.abs.model.*;
import uz.devops.fido.abs.service.FidoAbsService;
import uz.javlon.commons.result.CommonResultData;
import uz.javlon.commons.result.HasData;

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
    public HasData<ClientInfoDTO> getClientInfo(String clientId) {
        var clientInfo = DummyData.clientInfo();
        return new CommonResultData<>(clientInfo);
    }

    @Override
    public HasData<List<AccountDTO>> getActiveAccounts(String clientId) {
        var accountDTO = DummyData.accountDTO();
        return new CommonResultData<>(List.of(accountDTO));
    }

    @Override
    public HasData<TransactionResultDTO.CreatedTransaction> createTransaction(AbsTranDTO transactionDTO) {
        return new CommonResultData<>(DummyData.createdDocument());
    }

    @Override
    public HasData<AbsTranDTO> getTransaction(String transactionId) {
        var documentDTO = DummyData.transactionDTO();
        return new CommonResultData<>(documentDTO);
    }

    @Override
    public HasData<ResultDTO<?>> cancelTransaction(String transactionId) {
        return new CommonResultData<>(ResultDTO.builder().msg("Успешно").code(0).success(true).build());
    }

    @Override
    public HasData<List<ExchangeRateDTO>> getExchangeRates(ExchangeRateCriteria criteria) {
        return new CommonResultData<>(List.of(DummyData.exchangeRateDTO()));
    }

    @Override
    public HasData<ConversionResultDTO> internationalConversion(ConversionDTO conversionDTO) {
        return new CommonResultData<>(DummyData.conversionResultDTO());
    }


}
