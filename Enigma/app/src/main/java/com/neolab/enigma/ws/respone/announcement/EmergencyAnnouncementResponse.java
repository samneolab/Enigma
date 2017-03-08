package com.neolab.enigma.ws.respone.announcement;

import com.google.gson.annotations.SerializedName;
import com.neolab.enigma.dto.ws.announcement.AnnouncementDto;

/**
 * The class get response for emergency announcement request
 *
 * @author LongHV.
 */

public class EmergencyAnnouncementResponse {

    @SerializedName("status_code")
    public int statusCode;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public AnnouncementDto mAnnouncementDto;

}
