package com.neolab.enigma.util;

import com.neolab.enigma.EniConstant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description
 *
 * @author Pika.
 */
public class EniValidateUtil {

    /** Pattern valid email address regex */
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    /**
     * Validate user information to enable login button
     *
     * @param companyCode      Company code
     * @param employeeCode     Employee code
     * @param employeePassword Employee password
     * @return true if user information is valid, otherwise false
     */
    public static boolean isValidUserInfor(String companyCode, String employeeCode, String employeePassword) {
        return ((companyCode.length() >= EniConstant.MIN_LENGTH_FOR_COMPANY_CODE && companyCode.length() <= EniConstant.MAX_LENGTH_FOR_COMPANY_CODE)
                && (employeeCode.length() >= EniConstant.MIN_LENGTH_FOR_EMPLOYEE_CODE && employeeCode.length() <= EniConstant.MAX_LENGTH_FOR_EMPLOYEE_CODE)
                && (employeePassword.length() >= EniConstant.MIN_LENGTH_FOR_PASSWORD_CODE && employeePassword.length() <= EniConstant.MAX_LENGTH_FOR_PASSWORD_CODE));
    }

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
}
