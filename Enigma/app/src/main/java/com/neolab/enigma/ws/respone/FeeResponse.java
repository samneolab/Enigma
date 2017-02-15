package com.neolab.enigma.ws.respone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.neolab.enigma.dto.ws.payment.SalaryDto;

/**
 * Fee setting response
 *
 * @author LongHV.
 */
public class FeeResponse {

    @SerializedName("status_code")
    public int statusCode;
    @Expose
    public String message;
    @Expose
    public SalaryDto data;

}
