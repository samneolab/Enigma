package com.neolab.enigma.ws.respone.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.neolab.enigma.dto.user.DataLoginResponse;
import com.neolab.enigma.dto.user.UserDto;

import java.lang.reflect.Array;
import java.util.List;


/**
 * Login response.
 *
 * @author LongHV
 */
public class UserInformationResponse {

    @SerializedName("status_code")
    public int statusCode;
    @Expose
    public List<String> message;
    @Expose
    public UserDto data;

}
