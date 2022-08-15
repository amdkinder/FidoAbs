package uz.devops.fido.abs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import uz.devops.fido.abs.config.FidoAbsConfiguration;
import uz.devops.fido.abs.config.FidoAbsProperties;
import uz.devops.fido.abs.config.TestConfig;
import uz.devops.fido.abs.model.*;
import uz.devops.fido.abs.service.impl.AuthorizationService;
import uz.devops.fido.abs.service.impl.DummyData;

import java.net.URI;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {TestConfig.class})
class FidoAbsServiceTest {

    @Autowired
    @Qualifier(FidoAbsConfiguration.ABS_REST_TEMPLATE)
    private RestTemplate fidoAbsRestTemplate;

    @Autowired
    private FidoAbsService fidoAbsService;

    @MockBean
    private AuthorizationService authorizationService;

    private ObjectMapper mapper;
    private MockRestServiceServer mockServer;
    @Autowired
    private FidoAbsProperties fidoAbsProperties;
    private FidoAbsProperties.Config config;

    @SneakyThrows
    public void initialMock() {
        mapper = new ObjectMapper();
        mockServer = MockRestServiceServer.createServer(fidoAbsRestTemplate);
        config = fidoAbsProperties.getConfig();
        when(authorizationService.getToken()).thenReturn(config.getToken());

    }

    @Test
    @SneakyThrows
    void getClientInfo() {
        initialMock();
        mockServer.expect(ExpectedCount.manyTimes(),
                requestTo(new URI(config.getBaseUri() + "/1.0.0/get-customer/" + DummyData.clientId())))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.writeValueAsString(new ClientInfoResDTO(List.of(DummyData.clientInfo()))))
            );

        var result = fidoAbsService.getClientInfo(DummyData.clientId());
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getData()).isEqualTo(DummyData.clientInfo());

    }

    @Test
    @SneakyThrows
    void getActiveAccounts() {
        initialMock();
        mockServer.expect(ExpectedCount.manyTimes(),
                requestTo(new URI(config.getBaseUri() + "/1.0.0/get-active-accounts/" + DummyData.clientId())))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.writeValueAsString(new AccountResDTO(List.of(DummyData.accountDTO()))))
            );

        var result = fidoAbsService.getActiveAccounts(DummyData.clientId());
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getData()).isNotNull();
        assertThat(result.getData().size()).isEqualTo(1);
        assertThat(result.getData().get(0)).isEqualTo(DummyData.accountDTO());
    }

    @Test
    @SneakyThrows
    void createDocument() {
        initialMock();
        mockServer.expect(ExpectedCount.manyTimes(),
                requestTo(new URI(config.getBaseUri() + "/1.0.0/transactions")))
            .andExpect(method(HttpMethod.POST))
            .andRespond(withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.writeValueAsString(
                    ResultDTO.builder().code(0).responseBody(new TransactionResultDTO(List.of(DummyData.createdDocument()))).build()))
            );

        var result = fidoAbsService.createTransactions(DummyData.transactionDTO());
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getData()).isNotNull();
        assertThat(result.getData().get(0)).isEqualTo(DummyData.createdDocument());
    }

    @Test
    @SneakyThrows
    void getDocument() {
        initialMock();
        mockServer.expect(ExpectedCount.manyTimes(),
                requestTo(new URI(config.getBaseUri() + "/1.0.0/transactions/" + DummyData.transactionId())))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.writeValueAsString(
                    ResultDTO.builder()
                        .code(0)
                        .responseBody(DummyData.transactionDTO())
                        .build()
                ))
            );
        var result = fidoAbsService.getTransaction(DummyData.transactionId());
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getData()).isNotNull();
        assertThat(result.getData()).isEqualTo(DummyData.transactionDTO());
    }

    @Test
    @SneakyThrows
    void deleteTransactionById() {
        initialMock();
        mockServer.expect(ExpectedCount.manyTimes(),
                requestTo(new URI(config.getBaseUri() + "/1.0.0/transactions/" + DummyData.transactionId())))
            .andExpect(method(HttpMethod.DELETE))
            .andRespond(withStatus(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.writeValueAsString(new ResultDTO<>(false, "Transaction Data not found")))
            );
        var result = fidoAbsService.cancelTransaction(DummyData.transactionId());
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getData()).isNull();
    }

    @Test
    @SneakyThrows
    void getExchangeRates() {
        var formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        var criteria = DummyData.criteria();
        initialMock();
        mockServer.expect(ExpectedCount.manyTimes(),
                requestTo(new URI(config.getBaseUri() + "/1.0.0/international-card/get-list-exchange-rates?dateCross="
                    + criteria.getDateCross().format(formatter)
                    + "&currencyCode="
                    + criteria.getCurrencyCode())))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.writeValueAsString(
                    new ResultDTO<>(new ExchangeResultDTO<>(List.of(DummyData.exchangeRateDTO())))
                ))
            );
        var result = fidoAbsService.getExchangeRates(criteria);
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getData()).isNotNull();
        assertThat(result.getData().get(0)).isEqualTo(DummyData.exchangeRateDTO());
    }

    @Test
    @SneakyThrows
    void internationalConversion() {
        initialMock();
        mockServer.expect(ExpectedCount.manyTimes(),
                requestTo(new URI(config.getBaseUri() + "/1.0.0/international-card/conversion")))
            .andExpect(method(HttpMethod.POST))
            .andRespond(withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapper.writeValueAsString(
                    ResultDTO.builder()
                        .code(0)
                        .responseBody(DummyData.conversionResultDTO())
                        .build()
                ))
            );
        var result = fidoAbsService.internationalConversion(DummyData.conversionDTO());
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getData()).isNotNull();
        assertThat(result.getData()).isEqualTo(DummyData.conversionResultDTO());
    }
}
