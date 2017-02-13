package com.neolab.enigma.ws.respone.announcement;

import com.google.gson.annotations.SerializedName;

/**
 * @author LongHV.
 */
public class AnnouncementResponse {

    @SerializedName("status_code")
    public int statusCode;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public DataAnnouncementResponse data;

}
