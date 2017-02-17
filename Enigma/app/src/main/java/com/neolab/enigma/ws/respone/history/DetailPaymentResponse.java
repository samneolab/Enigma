package com.neolab.enigma.ws.respone.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.neolab.enigma.dto.ws.payment.DetailPaymentDto;

/**
 * Detail payment response class
 *
 * @author LongHV.
 */
public class DetailPaymentResponse {

    @SerializedName("status_code")
    public int statusCode;
    @Expose
    public String message;
    @Expose
    public DetailPaymentDto data;


}
