package com.neolab.enigma.ws.respone;

import com.google.gson.annotations.SerializedName;
import com.neolab.enigma.dto.user.DataLoginResponse;


/**
 * Login response.
 *
 * @author LongHV
 */
public class LoginResponse {

    @SerializedName("status_code")
    public int statusCode;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public DataLoginResponse data;

}
