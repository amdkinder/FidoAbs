package uz.devops.fido.abs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.javlon.commons.result.HasResult;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO<T> implements BaseResultDTO {
    private T responseBody;
    private String msg;
    private int code = -1;
    private Boolean success;

    public ResultDTO(T data) {
        this.responseBody = data;
        this.success = true;
    }

    public ResultDTO(Boolean success, String msg) {
        this.msg = msg;
        this.success = success;
    }

    public boolean isSuccess() {
        return this.code == 0;
    }

    public HasResult toStatus() {
        return isSuccess() ? HasResult.SUCCESS : HasResult.UNKNOWN_ERROR;
    }

}
