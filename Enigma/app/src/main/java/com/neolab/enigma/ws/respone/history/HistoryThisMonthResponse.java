package com.neolab.enigma.ws.respone.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.neolab.enigma.dto.ws.history.HistoryDto;

/**
 * @author LongHV.
 */
public class HistoryThisMonthResponse {

    @SerializedName("status_code")
    public int statusCode;
    @Expose
    public String message;
    @Expose
    public HistoryDto data;
}
