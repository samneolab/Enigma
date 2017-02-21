package com.neolab.enigma.dto.ws.history;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

/**
 * Month request payment list
 *
 * @author LongHV.
 */
public class MonthPaymentDto implements Parcelable{

    @Expose
    public int year;
    @Expose
    public int month;

    protected MonthPaymentDto(Parcel in) {
        year = in.readInt();
        month = in.readInt();
    }

    public static final Creator<MonthPaymentDto> CREATOR = new Creator<MonthPaymentDto>() {
        @Override
        public MonthPaymentDto createFromParcel(Parcel in) {
            return new MonthPaymentDto(in);
        }

        @Override
        public MonthPaymentDto[] newArray(int size) {
            return new MonthPaymentDto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(year);
        dest.writeInt(month);
    }
}
