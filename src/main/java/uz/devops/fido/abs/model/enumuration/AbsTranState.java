package uz.devops.fido.abs.model.enumuration;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AbsTranState {
    SCHEDULED(0, "Запланирован"),
    SMP(1, "Принят СМП"),
    INTRODUCED(11, "Введен"),
    ADJUSTED(12, "Скорректирован"),
    REMOVED(13, "Удален"),
    CANCELED(14, "Аннулирован"),
    ON_REMOVE(15, "На удаление"),
    ON_REVISION(16, "На доработку"),
    KILLED(18, "Забракован"),
    FAULT(19, " Ошибки"),
    POSTPONED(21, "Отложен"),
    APPROVED(31, "Утвержден"),
    APPROVED_GLB(32, "Утвержден Глб"),
    HELD(41, "Проведен"),
    ANOR(42, "Anor"),
    SEND_KS(50, "Отправлен в КЦ"),
    ACCEPTED_KS(51, "Принят КЦ"),
    SENT_VERIFICATION(53, "Отправлен на верификацию"),
    ACCEPTED_VERIFICATION(54, "Принят к верификации"),
    AUTO_PAYMENT(55, "Автооплата"),
    CHANGED_SPECIAL_ACCOUNT(60, "Заменен на спецсчет"),
    IMPORT_FILE(62, "Импортирован из файла"),
    UNKNOWN(-1, "Unknown");

    private final int code;

    private final String caption;

    AbsTranState(int code, String caption) {
        this.code = code;
        this.caption = caption;
    }

    public static AbsTranState getStateFromCode(String codeStr) {
        if (codeStr == null) {
            return null;
        }
        return getStateFromCode(Integer.parseInt(codeStr));
    }

    public static AbsTranState getStateFromCode(int code) {
        return Arrays.stream(values()).filter(it -> it.code == code).findFirst().orElse(UNKNOWN);
    }
}
