package com.neolab.enigma.dto.ws.payment;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author LongHV.
 */

public class PaymentRequestDto implements Parcelable{

    @Expose
    public int id;
    @Expose
    public int total;
    @SerializedName("complete_message")
    public String completeMessage;

    private PaymentRequestDto(Parcel in) {
        id = in.readInt();
        total = in.readInt();
        completeMessage = in.readString();
    }

    public static final Creator<PaymentRequestDto> CREATOR = new Creator<PaymentRequestDto>() {
        @Override
        public PaymentRequestDto createFromParcel(Parcel in) {
            return new PaymentRequestDto(in);
        }

        @Override
        public PaymentRequestDto[] newArray(int size) {
            return new PaymentRequestDto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(total);
        dest.writeString(completeMessage);
    }
}
