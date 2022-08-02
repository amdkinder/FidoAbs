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
    public ClientInfoResDTO getClientInfo(String clientId) {
        var clientInfo = DummyData.clientInfo();
        return new ClientInfoResDTO(List.of(clientInfo));
    }

    @Override
    public AccountResDTO<List<AccountDTO>> getActiveAccounts(String clientId) {
        var accountDTO = DummyData.accountDTO();
        return new AccountResDTO<>(List.of(accountDTO));
    }

    @Override
    public DocumentResultDTO createDocument(DocumentDTO documentDTO) {
        return new DocumentResultDTO(List.of(DummyData.createdDocument()));
    }

    @Override
    public DocumentDTO getDocument(String transactionId) {
        var documentDTO = DummyData.documentDTO();
        return documentDTO;
    }

    @Override
    public BaseResultDTO deleteTransactionById(String transactionId) {
        return ResultDTO.builder()
            .msg("Успешно")
            .code(0)
            .build();
    }

    @Override
    public List<ExchangeRateDTO> getExchangeRates(ExchangeRateCriteria criteria) {
        return List.of(DummyData.exchangeRateDTO());
    }

    @Override
    public ConversionResultDTO internationalConversion(ConversionDTO conversionDTO) {
        return DummyData.conversionResultDTO();
    }


}
