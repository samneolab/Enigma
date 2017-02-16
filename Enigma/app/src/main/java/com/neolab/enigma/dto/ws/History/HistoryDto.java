package com.neolab.enigma.dto.ws.History;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author LongHV.
 */
public class HistoryDto {

    @SerializedName("item")
    public List<SalaryRequestDto> salaryRequestDtoList;
    @SerializedName("total_payment")
    public int totalPayment;

}
