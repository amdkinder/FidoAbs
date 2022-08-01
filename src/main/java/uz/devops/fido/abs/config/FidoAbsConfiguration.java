package uz.devops.fido.abs.config;



import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.context.annotation.Bean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
//import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(FidoAbsProperties.class)
public class FidoAbsConfiguration {

    @Bean("fidoAbsRestTemplate")
    public RestTemplate fidoAbsRestTemplate(RestTemplateBuilder restTemplateBuilder, FidoAbsProperties fidoAbsProperties) {
        var credentials = fidoAbsProperties.getCredentials();
        return restTemplateBuilder
                .setReadTimeout(Duration.ofSeconds(fidoAbsProperties.getReadTimeout()))
                .setConnectTimeout(Duration.ofSeconds(fidoAbsProperties.getConnectTimeout()))
//                .basicAuthorization(credentials.getUsername(), credentials.getPassword())
            .defaultHeader("Authorization", "Bearer " + credentials.getPassword())
                .build();
    }



}
