package uz.devops.fido.abs.service.impl;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uz.devops.fido.abs.config.TestConfig;
import uz.devops.fido.abs.service.FidoAbsService;

//@RunWith(SpringRunner.class)
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
}
