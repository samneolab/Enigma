package com.neolab.enigma.dto.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author LongHV.
 */

public class DataLoginResponse {

    @Expose
    public String token;
    @SerializedName("user_info")
    public UserDto userDto;
    @SerializedName("csrf_token")
    public String csrfToken;

}
