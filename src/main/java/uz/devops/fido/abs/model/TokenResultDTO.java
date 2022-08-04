package uz.devops.fido.abs.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.devops.fido.abs.util.TimestampToInstantDeserializer;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResultDTO {
    private int sub;
    @JsonDeserialize(using = TimestampToInstantDeserializer.class)
    private Instant exp;
    @JsonDeserialize(using = TimestampToInstantDeserializer.class)
    private Instant iat;
    private Boolean isRefreshToken;

    public boolean isExpireNow() {
        return 0 > Instant.now().compareTo(this.exp.plus(Duration.ofSeconds(10)));
    }
}
