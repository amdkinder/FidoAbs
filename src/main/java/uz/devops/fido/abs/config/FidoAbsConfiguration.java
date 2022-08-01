package uz.devops.fido.abs.config;



import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(FidoAbsProperties.class)
public class FidoAbsConfiguration {

//    @Bean("fidoAbsRestTemplate")
//    public RestTemplate fidoAbsRestTemplate(RestTemplateBuilder restTemplateBuilder, FidoAbsProperties fidoAbsProperties) {
//        return restTemplateBuilder
//                .setReadTimeout(fidoAbsProperties.getReadTimeout())
//                .setConnectTimeout(fidoAbsProperties.getConnectTimeout())
//                .basicAuthorization(fidoAbsProperties.getLogin(), fidoAbsProperties.getPassword())
//                .build();
//    }



}
