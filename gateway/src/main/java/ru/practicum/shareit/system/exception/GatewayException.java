package ru.practicum.shareit.system.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;

public class GatewayException extends RuntimeException {
    private HttpStatusCode status;
    private HttpHeaders headers;
    private Object body;

    public GatewayException(HttpStatusCode status, HttpHeaders headers, Object body) {
        super("Ошибка при конвертации ответа шлюзу от сервера. status: " + status.toString());
        this.status = status;
        this.headers = headers;
        this.body = body;
    }

    public HttpStatusCode getStatus() {
        return status;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public Object getBody() {
        return body;
    }
}