package com.neolab.enigma.ws.respone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.neolab.enigma.ws.respone.login.LoginErrorResponse;

import java.lang.reflect.Type;

import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.Converter;

/**
 * Error response
 *
 * @author Pika.
 */
public class ErrorResponse {

    @SerializedName("status_code")
    public int code;
    @Expose
    public String message;

}
