package com.neolab.enigma.ws.respone.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Description
 *
 * @author Pika.
 */
public class LoginErrorResponse {

    @SerializedName("status_code")
    public int code;
    @Expose
    public String message;
    @SerializedName("errors")
    public LoginStateResponse loginStateResponse;

    public class LoginStateResponse {
        @Expose
        public int status;
    }
}
