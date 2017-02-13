package com.neolab.enigma.ws.respone.announcement;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.neolab.enigma.dto.ws.announcement.AnnouncementDto;

import java.util.List;

/**
 * @author LongHV.
 */

public class DataAnnouncementResponse {

    @Expose
    public int total;
    @SerializedName("per_page")
    public int perPage;
    @SerializedName("current_page")
    public int currentPage;
    @SerializedName("last_page")
    public int lastPage;
    @SerializedName("next_page_url")
    public String nextPageUrl;
    @SerializedName("prev_page_url")
    public String prevPageUrl;
    @Expose
    public int from;
    @Expose
    public int to;
    @SerializedName("items")
    public List<AnnouncementDto> announcementDtoList;

}
