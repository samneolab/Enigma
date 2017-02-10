package com.neolab.enigma.dto.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The
 *
 * @author LongHV.
 */

public class UserDto {

    @Expose
    public int id;
    @SerializedName("company_id")
    public int companyId;
    @Expose
    public int code;
    @Expose
    public String email;
    @Expose
    public String name;
    @Expose
    public int status;
    @SerializedName("company")
    public CompanyDto companyDto;

}
