package com.neolab.enigma.ws.respone.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * User change password response
 *
 * @author Pika.
 */
public class UserChangePasswordResponse {

    @SerializedName("status_code")
    public int statusCode;
    @Expose
    public String message;

}
