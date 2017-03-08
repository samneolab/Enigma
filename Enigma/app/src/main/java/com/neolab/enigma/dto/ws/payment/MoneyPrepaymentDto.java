package com.neolab.enigma.dto.ws.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author LongHV.
 */
public class MoneyPrepaymentDto {

    @SerializedName("remain_payment")
    public int remainPayment;
    @Expose
    public int id;
    @SerializedName("amount_of_salary")
    public int amountOfSalary;

}
