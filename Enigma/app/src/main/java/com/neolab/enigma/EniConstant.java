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

    public static final String EMPTY = "";

    /**
     * User status after login
     */
    public static class UserStatus {
        public static final int CREATE_ACCOUNT = 1;
        public static final int FIRST_LOGIN = 2;
        public static final int AGREE_TERM_AND_CONDITION = 3;
        public static final int NORMAL = 4;
        public static final int STOP_SERVICE = 5;
    }

    /**
     * The class manage display item on toolbar
     */
    public static class ToolbarType {
        /** Visible only logo and drawer menu */
        public static final int HOME = 1;
        /** Visible only back button, logo and drawer menu */
        public static final int DISPLAY_BACK_LOGO_DRAWER = 2;
        /** Visible only back button, title */
        public static final int DISPLAY_BACK_TITLE = 3;
        /** Visible only back button, title and drawer menu */
        public static final int DISPLAY_BACK_TITLE_DRAWER = 4;
        /** Visible only title */
        public static final int ONLY_TITLE = 5;
    }

    public static final class MenuItem {
        public static final int TOP_ITEM = 1;
        public static final int USER_INFORMATION = 2;
        public static final int LOGOUT = 3;
    }

    /** Request salary is applying, waiting system */
    public static final int HISTORY_SALARY_REQUEST_APPLYING = 1;
    /** Parenthesis start*/
    public static final String PARENTHESIS_START = "(";
    /** Parenthesis end */
    public static final String PARENTHESIS_END = ")";
    /** Space */
    public static final String SPACE = " ";
    /** Large space */
    public static final String LARGE_SPACE = "  ";

    public EniConstant(){
    }

}
