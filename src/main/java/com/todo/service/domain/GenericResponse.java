package com.todo.service.domain;

/**
 * Class name : GenericResponse
 * @param <T> Model class to represent the generic response across the api to ensure the response format always stays
 *           in uniformity despite the type of requests they are dealing. this ensures the consumer understand the format
 *           well enough and doesn't need to create custom response reading for each API.
 */
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
