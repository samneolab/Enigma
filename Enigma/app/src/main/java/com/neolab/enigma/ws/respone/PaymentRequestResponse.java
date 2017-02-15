package com.neolab.enigma.ws.respone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.neolab.enigma.dto.ws.payment.PaymentRequestDto;

/**
 * Payment Request Response class
 *
 * @author LongHV.
 */
public class PaymentRequestResponse {

    @SerializedName("status_code")
    public int statusCode;
    @Expose
    public String message;
    @Expose
    public PaymentRequestDto data;

}
