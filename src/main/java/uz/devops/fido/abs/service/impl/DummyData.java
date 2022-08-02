package uz.devops.fido.abs.service.impl;

import uz.devops.fido.abs.model.*;

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

    public static TransactionDTO documentDTO() {
        return TransactionDTO.builder()
            .type("106")
            .externalId("string_extId")
            .docNum("string_docNum")
            .docDate(LocalDate.EPOCH)
            .sender(Transmitter.builder()
                .account("sender_acc")
                .codeFilial("sender_code")
                .tax("sender_tax")
                .name("sender_name")
                .build())
            .recipient(Transmitter.builder()
                .account("recipient_acc")
                .codeFilial("recipient_code")
                .tax("recipient_tax")
                .name("recipient_name")
                .build())
            .purpose(Purpose.builder()
                .code("code")
                .name("name")
                .build())
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
            .amount("200")
            .clientId("6557883")
            .con("4")
            .purpose("test 200 popolnenie")
            .buy("110000")
            .externalCardNumber("8600302914327280")
            .dateExp("0225")
            .isMasked("Y")
            .convertionDateTime(LocalDate.EPOCH)
            .rollbackIs("0")
            .paymentType("22")
            .codeFilial("00491")
            .currency("840")
            .account("17403000900000491801")
            .cardNumber("5179860049916928")
            .build();
    }

    public static ConversionResultDTO conversionResultDTO() {
        return new ConversionResultDTO("244363");
    }


}
