package uz.devops.fido.abs.service.impl;

import uz.devops.fido.abs.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DummyData {
    public static ClientInfoDTO clientInfo() {
        return ClientInfoDTO.builder()
            .status("A")
            .clientId("368124")
            .clientUid("")
            .clientCode("04942014")
            .inn("496154737")
            .lastName("OOOABP3B")
            .firstName("Abdurahim")
            .middleName("Otkirbekov")
            .birthDate(LocalDate.of(1998, 6, 16))
            .gender("2")
            .citizenship("860")
            .docType("6")
            .series("CN")
            .number("4016310")
            .docIssueDate(LocalDate.EPOCH)
            .docIssuePlace("QIBRAY")
            .residenceRegion("12")
            .residenceDistrict("058")
            .phone("0")
            .email("0")
            .codeFilial("09008")
            .build();
    }

    public static AccountDTO accountDTO() {
        return AccountDTO.builder()
            .nameAcc("QVZXARZ ОЙУЙЕЧЫСОН-IA AEOLTIAOON ЮВ Д ТСНСНС QVNZ NO")
            .account("14901000499024198001")
            .codeCurrency("000")
            .saldo("-11700000000")
            .codeFilial("00439")
            .codeCoa("91809")
            .condition("A")
            .id("4345889")
            .build();
    }

    public static AbsTranDTO transactionDTO() {
        return AbsTranDTO.builder()
            .type("106")
            .externalId("string_extId")
            .sender(Transmitter.builder()
                .account("17403000300000491643")
                .codeFilial("00491")
                .tax("")
                .name("Tranzit 22616")
                .build())
            .recipient(Transmitter.builder()
                .account("22616000660702149001")
                .codeFilial("00491")
                .tax("")
                .name("Kasimov F")
                .build())
            .purpose(Purpose.builder()
                .code("00634")
                .name("пополнение тест")
                .build())
            .amount(new BigDecimal(1000))
            .build();
    }

    public static TransactionResultDTO.CreatedTransaction createdDocument() {
        return new TransactionResultDTO.CreatedTransaction("transactionId", "extId");
    }

    public static ExchangeRateDTO exchangeRateDTO() {
        return ExchangeRateDTO.builder()
            .currencyCode("643")
            .rateSal("13000")
            .rateParch("8000")
            .rateCB("10860")
            .build();
    }

    public static ConversionDTO conversionDTO() {
        return ConversionDTO.builder()
            .indicator("CR")
            .amount(BigDecimal.valueOf(2000))
            .clientId("6557883")
            .con("4")
            .purpose("test")
            .buy("1090000")
            .externalCardNumber("8600302914327280")
            .dateExp("CR")
            .isMasked("Y")
            .convertionDateTime(LocalDate.of(2022, 5, 20))
            .rollbackIs("0")
            .paymentType("22")
            .codeFilial("00491")
            .currency("840")
            .account("17403000900000491801")
            .cardNumber("5179860049916928")
            .build();
    }
    /*
    "currency": "840",
  "clientId": "6557883",
  "account": "17403000900000491801",
  "codeFilial": "00491",
  "purpose": "test",
  "indicator": "CR",
  "paymentType": "22",
  "convertionDateTime": "20.05.2022",
  "cardNumber": "5179860049916928",
  "dateExp": "CR",
  "externalCardNumber": "8600302914327280",
  "con": "4",
  "buy": "1090000",
  "isMasked": "Y",
  "rollbackIs": "0"
     */

    public static ExchangeRateCriteria criteria() {
        return new ExchangeRateCriteria(LocalDate.EPOCH, "ALL");
    }

    public static ConversionResultDTO conversionResultDTO() {
        return new ConversionResultDTO("244363");
    }

    public static String clientId() {
        return "06557883";
    }

    public static String transactionId() {
        return "955726066";
    }

}
