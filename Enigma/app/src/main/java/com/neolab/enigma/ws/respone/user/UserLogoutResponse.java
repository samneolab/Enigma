package com.neolab.enigma.ws.respone.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Logout response
 *
 * @author Pika.
 */
public class UserLogoutResponse {

    @SerializedName("status_code")
    public int statusCode;
    @Expose
    public String message;

}
