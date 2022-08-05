package uz.devops.fido.abs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO<T> implements BaseResultDTO {
    private T data;
    private T responseBody;
    private String msg;
    private int code = -1;
    private Boolean success;

    public ResultDTO(T data) {
        this.data = data;
        this.success = true;
    }

    public ResultDTO(Boolean success, String msg) {
        this.msg = msg;
        this.success = success;
    }

    public boolean isSuccess() {
        return this.code == 0;
    }

}
