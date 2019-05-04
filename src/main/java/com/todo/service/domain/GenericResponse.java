package com.todo.service.domain;

public class GenericResponse<T> {

    private String statusMessage;
    private T responseBody;

    public GenericResponse(String statusMessage, T responseBody) {
        this.statusMessage = statusMessage;
        this.responseBody = responseBody;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public T getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(T responseBody) {
        this.responseBody = responseBody;
    }
}
