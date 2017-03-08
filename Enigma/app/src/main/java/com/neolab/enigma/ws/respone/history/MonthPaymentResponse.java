package com.neolab.enigma.ws.respone.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.neolab.enigma.dto.ws.history.MonthPaymentDto;

import java.util.List;

/**
 * Month request payment list
 *
 * @author LongHV.
 */
public class MonthPaymentResponse {

    @SerializedName("status_code")
    public int statusCode;
    @Expose
    public String message;
    @Expose
    public List<MonthPaymentDto> data;

}
