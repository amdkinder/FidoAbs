package uz.devops.fido.abs.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.devops.fido.abs.config.FidoAbsConfiguration;
import uz.devops.fido.abs.config.FidoAbsProperties;
import uz.devops.fido.abs.model.AuthRequestDTO;
import uz.devops.fido.abs.model.TokenResponseDTO;
import uz.devops.fido.abs.model.TokenResultDTO;

import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Service
public class AuthorizationService {

    private TokenResponseDTO tokenResponseDTO;
    private final RestTemplate restTemplate;
    private final FidoAbsProperties fidoAbsProperties;
    private HttpHeaders httpHeaders;
    private final Base64.Decoder decoder;
    private final ObjectMapper objectMapper;
    private TokenResultDTO tokenResultDTO;

    public AuthorizationService(@Qualifier(FidoAbsConfiguration.ABS_REST_TEMPLATE) RestTemplate restTemplate, FidoAbsProperties fidoAbsProperties) {
        this.restTemplate = restTemplate;
        this.fidoAbsProperties = fidoAbsProperties;
        this.objectMapper = new ObjectMapper();
        decoder = Base64.getDecoder();
    }

    public HttpHeaders getHttpHeaders() {
        if (httpHeaders == null) {
            httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.set("Accept-Language", "ru");
        }
        httpHeaders.set("requestId", UUID.randomUUID().toString());
        return httpHeaders;
    }

    public void getTokenPayload() {
        log.debug("Request to get token from abs: {}", fidoAbsProperties.getConfig());
        var reqBody = new AuthRequestDTO(
            fidoAbsProperties.getConfig().getUsername(),
            fidoAbsProperties.getConfig().getPassword()
        );
        var request = new HttpEntity<>(reqBody, getHttpHeaders());
        try {
            var response = restTemplate.exchange("/getToken", HttpMethod.POST, request, TokenResponseDTO.class);
            log.debug("Response from token: {}", response);
            if (response.getStatusCode().is2xxSuccessful()
                && response.getBody() != null
                && response.getBody() instanceof TokenResponseDTO) {
                log.debug("Response is success");
                var token = response.getBody().getToken();
                parseToken(token);
                tokenResponseDTO = response.getBody();
            }
        } catch (Exception e) {
            log.debug("Can not get token: {}", e.getMessage());
            e.printStackTrace();
        }

    }

    private void parseToken(String token) {
        var chunks = token.split("\\.");
        var header = new String(decoder.decode(chunks[0]));
        var body = new String(decoder.decode(chunks[1]));
        log.debug("Token header: {}, \nToken body: {}", header, body);
        try {
            tokenResultDTO = objectMapper.readValue(body, TokenResultDTO.class);
        } catch (JsonProcessingException e) {
            log.debug("Request to parse token: {}", e.getMessage());
        }
    }



    public String getToken() {
        if (tokenResponseDTO == null || tokenResultDTO == null || tokenResultDTO.isExpireNow()) {
            getTokenPayload();
        }
        return tokenResponseDTO.getToken();
    }
}
