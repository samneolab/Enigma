package com.neolab.enigma.ws.respone.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Agree term and condition response
 *
 * @author Pika.
 */
public class UserAgreeTermResponse {

    @SerializedName("status_code")
    public int statusCode;
    @Expose
    public String message;

}
