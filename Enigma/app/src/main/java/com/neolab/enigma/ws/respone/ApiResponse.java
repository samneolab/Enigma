package com.neolab.enigma.ws.respone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Api response default
 *
 * @author Pika.
 */
public class ApiResponse {

    @SerializedName("status_code")
    public int code;
    @Expose
    public String message;

}
