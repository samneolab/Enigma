package com.neolab.enigma.ws.respone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
