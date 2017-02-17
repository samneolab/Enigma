package com.neolab.enigma.ws.respone.login;

import com.google.gson.annotations.Expose;
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
    @Expose
    public String message;
    @Expose
    public DataLoginResponse data;

}
