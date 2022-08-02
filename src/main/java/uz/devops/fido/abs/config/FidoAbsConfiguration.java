package uz.devops.fido.abs.config;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
//import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(FidoAbsProperties.class)
@RequiredArgsConstructor
public class FidoAbsConfiguration {
    private final FidoAbsProperties fidoAbsProperties;
    public static final String ABS_REST_TEMPLATE = "fidoAbsRestTemplate";

    @Bean(ABS_REST_TEMPLATE)
    public RestTemplate fidoAbsRestTemplate() {
        var config = fidoAbsProperties.getConfig();
        return new RestTemplateBuilder()
            .rootUri(config.getBaseUri())
            .setReadTimeout(Duration.ofSeconds(config.getReadTimeout()))
            .setConnectTimeout(Duration.ofSeconds(config.getConnectTimeout()))
            .defaultHeader("Authorization", "Bearer " + config.getPassword())
            .build();
    }


}
