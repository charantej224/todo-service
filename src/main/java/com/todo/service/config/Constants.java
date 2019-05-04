package com.todo.service.config;

/**
 * Application constants.
 */
public final class Constants {

    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String FAILED = "FAILED";
    public static final String SUCCESS = "SUCCESS";


    private Constants() {
    }
}
