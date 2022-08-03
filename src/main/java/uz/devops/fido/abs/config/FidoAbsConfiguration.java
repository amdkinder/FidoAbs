package uz.devops.fido.abs.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
@EnableConfigurationProperties(FidoAbsProperties.class)
@RequiredArgsConstructor
public class FidoAbsConfiguration {
    private final FidoAbsProperties fidoAbsProperties;
    public static final String ABS_REST_TEMPLATE = "fidoAbsRestTemplate";

    @Bean(ABS_REST_TEMPLATE)
    public RestTemplate fidoAbsRestTemplate() {
        var httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(fidoAbsProperties.getConfig().getConnectTimeout());
        httpRequestFactory.setReadTimeout(fidoAbsProperties.getConfig().getReadTimeout());
        var restTemplate = new RestTemplate(httpRequestFactory);
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(fidoAbsProperties.getConfig().getBaseUri()));

        return restTemplate;
    }




}
