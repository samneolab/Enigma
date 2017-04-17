package com.neolab.enigma.ws.respone.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Description
 *
 * @author Pika.
 */
public class ErrorUpdateInformationResponse {

    @SerializedName("status_code")
    public int code;
    @Expose
    public String message;
    @SerializedName("errors")
    public ErrorDetail error;

    public class ErrorDetail {

        @SerializedName("current_password")
        public List<String> currentPassword;

        @SerializedName("email")
        public List<String> email;
    }
}
