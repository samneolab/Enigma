package com.neolab.enigma.ws.respone.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Stop using service response
 *
 * @author Pika.
 */
public class StopServiceResponse {

    @SerializedName("status_code")
    public int statusCode;
    @Expose
    public String message;
}
