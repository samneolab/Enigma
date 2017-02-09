package com.neolab.enigma.dto.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author LongHV.
 */

public class UserCompanyDto {

    @Expose
    public int id;
    @Expose
    public String name;
    @SerializedName("name_kana")
    public String nameKana;
    @Expose
    public String code;

}
