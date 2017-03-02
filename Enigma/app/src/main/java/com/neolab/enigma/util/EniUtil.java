package com.neolab.enigma.util;

import com.neolab.enigma.EniConstant;
import com.neolab.enigma.dto.ws.payment.SalaryDto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Enigma Utility process decisions on specific conditions.
 *
 * @author LongHV.
 */
public class EniUtil {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    /**
     * The method is used to check validate email address
     *
     * @param emailStr email
     * @return true if email address is validate, otherwise false
     */
    public static boolean isValidateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    /**
     * Validate user information to enable login button
     *
     * @param employeePassword Employee password
     * @return true if user information is valid, otherwise false
     */
    public static boolean isValidPassword(String employeePassword) {
        return ((employeePassword.length() >= EniConstant.MIN_LENGTH_FOR_PASSWORD_CODE && employeePassword.length() <= EniConstant.MAX_LENGTH_FOR_PASSWORD_CODE));
    }

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
