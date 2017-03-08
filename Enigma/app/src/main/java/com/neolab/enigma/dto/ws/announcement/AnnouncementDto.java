package com.neolab.enigma.dto.ws.announcement;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author LongHV.
 */
public class AnnouncementDto {

    @Expose
    public int id;
    @Expose
    public String title;
    @Expose
    public String content;
    @SerializedName("start_time_at")
    public String startTime;
    @SerializedName("is_emergency")
    public boolean isEmergency;

}
