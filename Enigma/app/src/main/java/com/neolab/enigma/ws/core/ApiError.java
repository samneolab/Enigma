package com.neolab.enigma.ws.core;

import lombok.Builder;
import lombok.Value;

/**
 * A custom Error.
 *
 * @author LongHV3
 */
@Value
@Builder
public class ApiError {
    public static final ApiError ERROR_NETWORK = ApiError.builder()
            .error(Error.ERROR_NETWORK)
            .build();

    public static final ApiError ERROR_PLEASE_TRY_LATER = ApiError.builder()
            .error(Error.ERROR_PLEASE_TRY_LATER)
            .build();

    private Error error;

    /**
     * Define Error
     */
    @Value
    @Builder
    public static class Error {
        public static final Error ERROR_NETWORK = Error.builder()
                .message("インターネット接続がありません") // there is no internet connection
                .build();

        public static final Error ERROR_PLEASE_TRY_LATER = Error.builder()
                .message("時間をおいてもう一度やり直してください。") // error, please try it later.
                .build();

        private String code;
        private String message;
    }
}
