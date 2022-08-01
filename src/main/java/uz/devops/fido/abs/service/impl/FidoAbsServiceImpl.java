package uz.devops.fido.abs.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.beans.factory.annotation.Qualifier;
import uz.devops.fido.abs.config.FidoAbsProperties;
import uz.devops.fido.abs.service.FidoAbsService;
import lombok.extern.slf4j.Slf4j;

import static uz.devops.fido.abs.service.FidoAbsService.NAME;

@Slf4j
@Service(NAME)
@ConditionalOnProperty(
        prefix = "fido-abs",
        name = "simulate",
        havingValue = "false"
)
public class FidoAbsServiceImpl implements FidoAbsService {

    private final FidoAbsProperties fidoAbsProperties;
//    private final RestTemplate fidoAbsRestTemplate;

    @Autowired
    public FidoAbsServiceImpl(FidoAbsProperties fidoAbsProperties/*, @Qualifier("fidoAbsRestTemplate") RestTemplate fidoAbsRestTemplate*/) {
        this.fidoAbsProperties = fidoAbsProperties;
//        this.fidoAbsRestTemplate = fidoAbsRestTemplate;
    }

//    @Override
//    public String send(String request) {
//        log.debug("Request : {}, name : {}", request, fidoAbsProperties.getName());
//        return "Response for : " + request;
//    }

}
