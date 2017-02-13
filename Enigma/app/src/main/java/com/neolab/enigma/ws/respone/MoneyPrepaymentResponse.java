package com.neolab.enigma.ws.respone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.neolab.enigma.dto.MoneyPrepaymentDto;

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
