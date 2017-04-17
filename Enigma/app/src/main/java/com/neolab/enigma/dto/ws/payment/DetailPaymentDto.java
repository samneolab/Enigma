package com.neolab.enigma.dto.ws.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author LongHV.
 */

public class DetailPaymentDto {

    @Expose
    public int id;
    @SerializedName("amount_of_salary")
    public int amountOfSalary;
    @SerializedName("amount_of_transaction_fee")
    public int amountOfTransactionFee;
    @SerializedName("amount_of_kickback_fee")
    public int amountOfKichbackFee;
    @SerializedName("amount_of_transfer_fee")
    public int amountOfTransferFee;
    @SerializedName("amount_of_consumption_tax")
    public int amountOfConsumptionTax;
    @SerializedName("total_fee_include_tax")
    public int totalFeeIncludeTax;
    @Expose
    public int total;
    @Expose
    public int status;
    @Expose
    public String comment;
    @SerializedName("applied_at")
    public String appliedAt;
    @SerializedName("created_at")
    public String createAt;

}