package uz.devops.fido.abs.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.devops.fido.abs.util.LocalDateJsonDeserializer;
import uz.devops.fido.abs.util.LocalDateJsonSerializer;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientInfoDTO implements Serializable {
    /**
     * Статус клиента
     */
    private String status;
    /**
     * Номер клиента
     */
    private String clientId;
    /**
     *
     */
    private String clientUid;
    /**
     * Код клиента
     */
    private String clientCode;
    /**
     * ИНН клиента
     */
    private String inn;
    /**
     * Персональный номер
     */
    private String pinfl;
    /**
     * Фамилия
     */
    private String lastName;
    /**
     * Имя
     */
    private String firstName;
    /**
     * Отчество
     */
    private String middleName;
    /**
     * Дата рождения
     */
    @JsonSerialize(using = LocalDateJsonSerializer.class)
    @JsonDeserialize(using = LocalDateJsonDeserializer.class)
    private LocalDate birthDate;
    /**
     * Место рождения
     */
    private String birthPlace;
    /**
     * Страна рождения. Справочник №018 «Страны и территории»
     */
    private String birthCountry;
    /**
     * Пол
     */
    private String gender;
    /**
     * Гражданство, Справочник №018 «Страны и территории»
     */
    private String citizenship;
    /**
     * Тип документа, Справочник № 008 «ВХйы удостоверяющих документов физического лица»
     */
    private String docType;
    /**
     * Серия паспорта
     */
    private String series;
    /**
     * Номер паспорта
     */
    private String number;
    /**
     * Дата выдачи
     */
    @JsonSerialize(using = LocalDateJsonSerializer.class)
    @JsonDeserialize(using = LocalDateJsonDeserializer.class)
    private LocalDate docIssueDate;
    /**
     * Дата окончания действия
     */
    private String docExpireDate;
    /**
     * Место выдачи документа
     */
    private String docIssuePlace;
    /**
     * Страна проживания, Справочник №018 «Страны и территории»
     */
    private String residenceCountry;
    /**
     * Область проживания, Справочник №016 «Области РУз»
     */
    private String residenceRegion;
    /**
     * Район проживания, Справочник №052 «Районы РУз»
     */
    private String residenceDistrict;
    /**
     * Адрес проживания
     */
    private String residenceAddress;
    /**
     * Телефон
     */
    private String phone;
    /**
     * Мобильный телефон
     */
    private String mobilePhone;
    /**
     * e-mail
     */
    private String email;
    /**
     * Семейное положение
     */
    private String maritalStatus;
    /**
     * Код филиала, Справочник №012
     */
    private String codeFilial;
}
