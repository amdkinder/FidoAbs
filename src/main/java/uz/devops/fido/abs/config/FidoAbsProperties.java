package uz.devops.fido.abs.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Setter
@Getter
@ConfigurationProperties(prefix = "fido-abs")
public class FidoAbsProperties {
    private Config config;
    private Boolean simulate;
    private Map<String, String> accCardPattern;
    private Map<String, String> reverseAccCardPattern;
    private AbsConstants constants;

    @Data
    public static class Config {
        private String username;
        private String password;
        private String token;
        private int readTimeout = 5;
        private int connectTimeout = 5;
        private String baseUri;
    }

    @Data
    public static class AbsConstants {
        private String codeFilial;
        private String con;
    }

}
