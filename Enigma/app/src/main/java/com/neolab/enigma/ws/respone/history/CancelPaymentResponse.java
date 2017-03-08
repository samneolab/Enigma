package com.neolab.enigma.ws.respone.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author LongHV.
 */
public class CancelPaymentResponse {

    @SerializedName("status_code")
    public int statusCode;
    @Expose
    public String message;

}
