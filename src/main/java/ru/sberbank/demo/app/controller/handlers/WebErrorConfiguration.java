package ru.sberbank.demo.app.controller.handlers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(name = "app.errors.attributes", havingValue = "true")
@Configuration
public class WebErrorConfiguration {

    @Value("${app.api.version}")
    private String currentApiVersion;
    @Value("${app.sendreport.uri}")
    private String sendReportUri;

    @Bean
    public org.springframework.boot.web.servlet.error.ErrorAttributes errorAttributes() {
        return new ErrorAttributes(currentApiVersion, sendReportUri);
    }

}
