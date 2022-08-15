package uz.devops.fido.abs.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.SpringBootTest;
import uz.devops.fido.abs.config.TestConfig;
import uz.devops.fido.abs.service.FidoAbsService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@RunWith(SpringRunner.class)
@Slf4j
@ConditionalOnProperty(prefix = "fido-abs", name = "test-realdata", havingValue = "true", matchIfMissing = true)
@SpringBootTest(classes = {TestConfig.class})
public class FidoAbsServiceImplTest {

    @Autowired
    private FidoAbsService fidoAbsService;

    @BeforeAll
    static void setUp() {
        //Do something on starting
    }

    @AfterAll
    static void tearDown() {
        //Do something on finishing
    }

    @Test
    public void testSend() {
//        String response = fidoAbsService.send("Hi, Javlon");
//        assertEquals("Response for : Hi, Javlon", response);
    }




    @Test
    void getClientInfo() {
        var result = fidoAbsService.getClientInfo(DummyData.clientId());
        log.info("Response from fido abs: {}", result);
        assertThat(result).isNotNull();
    }

    @Test
    void getActiveAccounts() {
        var result = fidoAbsService.getActiveAccounts(DummyData.clientId());
        log.info("Response from fido abs: {}", result);
        assertThat(result).isNotNull();
    }

    @Test
    void createTransaction() {
        var result = fidoAbsService.createTransactions(List.of(DummyData.transactionDTO()));
        log.info("Response from fido abs: {}", result);
        assertThat(result).isNotNull();
    }

    @Test
    void getTransaction() {
        var result = fidoAbsService.getTransaction(DummyData.transactionId());
        log.info("Response from fido abs: {}", result);
        assertThat(result).isNotNull();
    }

    @Test
    void deleteTransactionById() {
        var result = fidoAbsService.cancelTransaction(DummyData.transactionId());
        log.info("Response from fido abs: {}", result);
        assertThat(result).isNotNull();
    }

    @Test
    void getExchangeRates() {
        var result = fidoAbsService.getExchangeRates(DummyData.criteria());
        log.info("Response from fido abs: {}", result);
        assertThat(result).isNotNull();
        assertThat(result.isSuccess()).isTrue();
    }

    @Test
    void internationalConversion() {
        var result = fidoAbsService.internationalConversion(DummyData.conversionDTO());
        assertThat(result).isNotNull();
        assertThat(result.isSuccess()).isTrue();
        log.info("result: {}", result);
        assertThat(result.getData()).isNotNull();
    }
}
