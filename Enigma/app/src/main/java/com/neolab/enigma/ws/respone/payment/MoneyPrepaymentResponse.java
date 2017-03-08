package com.neolab.enigma.ws.respone.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.neolab.enigma.dto.ws.payment.MoneyPrepaymentDto;

/**
 * @author LongHV.
 */
public class MoneyPrepaymentResponse {

    @SerializedName("status_code")
    public int statusCode;
    @Expose
    public String message;
    @Expose
    public MoneyPrepaymentDto data;

}
