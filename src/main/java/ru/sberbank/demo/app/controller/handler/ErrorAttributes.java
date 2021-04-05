package ru.sberbank.demo.app.controller.handler;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;


class ErrorAttributes extends DefaultErrorAttributes {

    private final String currentApiVersion;
    private final String sendReportUri;

    public ErrorAttributes(final String currentApiVersion, final String sendReportUri) {
        this.currentApiVersion = currentApiVersion;
        this.sendReportUri = sendReportUri;
    }

    @Override
    public Map<String, Object> getErrorAttributes(final WebRequest webRequest, ErrorAttributeOptions options) {
        final Map<String, Object> defaultErrorAttributes = super.getErrorAttributes(webRequest, options);
        final ExceptionResponse exceptionResponse = ExceptionResponse.fromDefaultAttributeMap(
                currentApiVersion, defaultErrorAttributes, sendReportUri
        );
        return exceptionResponse.toAttributeMap();
    }

}
