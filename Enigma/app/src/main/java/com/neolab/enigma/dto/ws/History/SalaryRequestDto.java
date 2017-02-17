package com.neolab.enigma.dto.ws.history;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author LongHV.
 */
public class SalaryRequestDto {

    @Expose
    public int id;
    @SerializedName("amount_of_salary")
    public int amountOfSalary;
    @Expose
    public int total;
    @SerializedName("total_payment")
    public int totalPayment;
    @Expose
    public int status;
    @SerializedName("created_at")
    public String createDate;
    @SerializedName("applied_at")
    public String appliedDate;
    @SerializedName("status_object")
    public StatusRequestDto statusRequestDto;

    private SalaryRequestDto(Parcel in) {
        id = in.readInt();
        amountOfSalary = in.readInt();
        total = in.readInt();
        totalPayment = in.readInt();
        status = in.readInt();
        createDate = in.readString();
        appliedDate = in.readString();
    }

}
