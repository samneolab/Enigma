package com.neolab.enigma.dto.ws.payment;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @author LongHV.
 */
public class SalaryDto implements Parcelable {

    @SerializedName("upper_limit_rate")
    public int upperLimitRate;
    @SerializedName("upper_limit_total_per_day")
    public long upperLimitTotalPerDay;
    @SerializedName("insurance_fee")
    public long insuranceFee;
    @SerializedName("transaction_fee_rate")
    public float transactionFeeRate;
    @SerializedName("transaction_kickback_rate")
    public float transactionKickbackRate;
    @SerializedName("transfer_fee")
    public long transferFee;
    @SerializedName("remain_payment")
    public long remainPayment;
    @SerializedName("max_payment")
    public long maxPayment;

    protected SalaryDto(Parcel in) {
        upperLimitRate = in.readInt();
        upperLimitTotalPerDay = in.readLong();
        insuranceFee = in.readLong();
        transactionFeeRate = in.readFloat();
        transactionKickbackRate = in.readFloat();
        transferFee = in.readLong();
        remainPayment = in.readLong();
        maxPayment = in.readLong();
    }

    public static final Creator<SalaryDto> CREATOR = new Creator<SalaryDto>() {
        @Override
        public SalaryDto createFromParcel(Parcel in) {
            return new SalaryDto(in);
        }

        @Override
        public SalaryDto[] newArray(int size) {
            return new SalaryDto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(upperLimitRate);
        dest.writeLong(upperLimitTotalPerDay);
        dest.writeLong(insuranceFee);
        dest.writeFloat(transactionFeeRate);
        dest.writeFloat(transactionKickbackRate);
        dest.writeLong(transferFee);
        dest.writeLong(remainPayment);
        dest.writeLong(maxPayment);
    }
}
