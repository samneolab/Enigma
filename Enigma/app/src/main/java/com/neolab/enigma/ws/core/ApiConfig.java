package com.neolab.enigma.ws.core;

import android.content.Context;

import lombok.Builder;
import lombok.Value;

/**
 * A configure is used to create {@link ApiClient}.
 *
 * @author LongHV3
 */
@Value
@Builder
public class ApiConfig {
    private Context context;
    private String baseUrl;
}
