package ru.sberbank.demo.app.controller.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Slf4j
class ErrorAttributes extends DefaultErrorAttributes {

    private final String currentApiVersion;
    private final String sendReportUri;

    public ErrorAttributes(final String currentApiVersion, final String sendReportUri) {
        this.currentApiVersion = currentApiVersion;
        this.sendReportUri = sendReportUri;
    }

    @Override
    public Map<String, Object> getErrorAttributes(final WebRequest webRequest, final boolean includeStackTrace) {
        final Map<String, Object> defaultErrorAttributes = super.getErrorAttributes(webRequest, false);
        defaultErrorAttributes.put("detailMessage", super.getError(webRequest).getMessage());
        final ErrorResponse errorResponse = ErrorResponse.fromDefaultAttributeMap(
                currentApiVersion, defaultErrorAttributes, sendReportUri
        );
        return errorResponse.toAttributeMap();
    }

}
