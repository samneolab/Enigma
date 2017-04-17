package com.neolab.enigma.ws.respone.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Validate money payment response
 *
 * @author Pika.
 */
public class ValidateMoneyPaymentResponse {

    @SerializedName("status_code")
    public int code;
    @Expose
    public String message;
    @Expose
    public InformationPayment data;

    /**
     * Information about payment class
     */
    public class InformationPayment {

        @SerializedName("amount_of_salary")
        public int amountOfSalary;
        @SerializedName("is_valid")
        public boolean isValid;
        @SerializedName("received_money")
        public int receivedMoney;
        @SerializedName("total_fee_include_tax")
        public int totalFeeIncludeTax;
    }

}
