package uz.devops.fido.abs.model.enumuration;

import lombok.Getter;

import java.util.Arrays;

public enum AbsTranState {
    SCHEDULED, UNKNOWN;

    @Getter
    private int code;


    public static AbsTranState getStateFromCode(String codeStr) {
        if (codeStr == null) {
            return null;
        }
        return getStateFromCode(Integer.parseInt(codeStr));
    }

    public static AbsTranState getStateFromCode(int code) {
        return  Arrays.stream(values()).filter(it -> it.code == code).findFirst().orElse(UNKNOWN);
    }
}
/*
0 Запланирован - Scheduled
1 Принят СМП - SMP
11 Введен - Introduced
12 Скорректирован - Adjusted
13 Удален - Removed
14 Аннулирован - Canceled
15 На удаление - For removal
16 На доработку - For revision
18 Забракован - Killed
19 Ошибки - Fault
21 Отложен
31 Утвержден
32 Утвержден Глб
41 Проведен
42 ANOR
50 Отправлен в КЦ
51 Принят КЦ
53 Отправлен на верификацию
54 Принят к верификации
55 Автооплата
60 Заменен на спецсчет
62 Импортирован из файла
 */
