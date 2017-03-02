package com.neolab.enigma.ws.respone.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Response when user update information
 *
 * @author Pika.
 */
public class UserUpdateInforResponse {

    @SerializedName("status_code")
    public int statusCode;
    @Expose
    public String message;
}
