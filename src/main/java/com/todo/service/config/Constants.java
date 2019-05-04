package com.todo.service.config;

/**
 * Application constants.
 */
public final class Constants {

    public static final String SECRET = "NWM4NjQxNDM0NTk4NmY1OGMwMjcwNDk0ZGZiMDNiODFhMGNiMGQ4ZTZiYmU5ZWZmZTdkZjlmZjY3Y2YwYWRkYmRjNjA5MTkzMjA3Y2NjNzBkNjQ2ZjA4YTJiOWFkODFlZDEzZWU4ZTk1ZjBjNDJkMGZkOWEzOTViZjVkM2IwMGE=";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    
    private Constants() {
    }
}
