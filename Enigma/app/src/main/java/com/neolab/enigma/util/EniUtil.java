package com.neolab.enigma.util;

import com.neolab.enigma.dto.ws.payment.SalaryDto;

/**
 * Enigma Utility process decisions on specific conditions.
 *
 * @author LongHV.
 */
public class EniUtil {

    /**
     * Calculate the fee usage system
     *
     * @param salaryPayment the salary has payment
     * @return fee usage system
     */
    public static int getFeeUsageSystem(SalaryDto salaryDto, int salaryPayment) {
        return (int) (((salaryDto.transactionFeeRate + salaryDto.transactionKickbackRate) * salaryPayment / 100) + salaryDto.transferFee);
    }

}
