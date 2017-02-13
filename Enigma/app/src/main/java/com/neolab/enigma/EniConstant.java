package com.neolab.enigma;

/**
 * @author LongHV.
 */

public class EniConstant {

    /***************************************
     * Login minimum and maximum character
     ***************************************/
    public static final int MAX_LENGTH_FOR_COMPANY_CODE = 255;
    public static final int MIN_LENGTH_FOR_COMPANY_CODE = 1;
    public static final int MAX_LENGTH_FOR_EMPLOYEE_CODE = 255;
    public static final int MIN_LENGTH_FOR_EMPLOYEE_CODE = 1;
    public static final int MAX_LENGTH_FOR_PASSWORD_CODE = 255;
    public static final int MIN_LENGTH_FOR_PASSWORD_CODE = 6;

    /**
     * User status after login
     */
    public static class UserStatus{
        public static final int CREATE_ACCOUNT = 1;
        public static final int FIRST_LOGIN = 2;
        public static final int AGREE_TERM_AND_CONDITION = 3;
        public static final int NORMAL = 4;
        public static final int STOP_SERVICE = 5;
    }

    public EniConstant(){
    }

}
