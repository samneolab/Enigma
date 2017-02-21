package com.neolab.enigma.dto.ws.history;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author LongHV.
 */
public class SalaryRequestDto implements Parcelable{

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

    public static final Creator<SalaryRequestDto> CREATOR = new Creator<SalaryRequestDto>() {
        @Override
        public SalaryRequestDto createFromParcel(Parcel in) {
            return new SalaryRequestDto(in);
        }

        @Override
        public SalaryRequestDto[] newArray(int size) {
            return new SalaryRequestDto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(amountOfSalary);
        dest.writeInt(total);
        dest.writeInt(totalPayment);
        dest.writeInt(status);
        dest.writeString(createDate);
        dest.writeString(appliedDate);
    }
}
