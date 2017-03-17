package com.neolab.enigma.ws.respone.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.neolab.enigma.ws.respone.ApiResponse;

import java.util.List;

/**
 * Captcha error response
 *
 * @author Pika.
 */
public class CaptchaErrorResponse extends ApiResponse {

    @SerializedName("errors")
    public Error error;

    public class Error {

        @Expose
        public List<String> captcha;
    }

}
