package com.neolab.enigma.ws.respone.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.neolab.enigma.ws.respone.ApiResponse;

/**
 * get captcha image response
 *
 * @author Pika.
 */
public class CaptchaImageResponse extends ApiResponse{

    @Expose
    public CaptchaImage data;

    public class CaptchaImage {

        @SerializedName("captcha_id")
        public String captchaId;

        @SerializedName("captcha_url")
        public String captchaUrl;
    }
}
