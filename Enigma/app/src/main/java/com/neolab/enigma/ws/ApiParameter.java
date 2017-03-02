package com.neolab.enigma.ws;

/**
 * Parameters use to request API.
 *
 * @author LongHV
 */
public final class ApiParameter {

    /***************************************
     * Login Parameter
     ***************************************/
    public static final String COMPANY_CODE = "company_code";
    public static final String EMPLOYEE_CODE = "employee_code";
    public static final String EMPLOYEE_PASSWORD = "employee_password";
    public static final String AMOUNT_OF_SALARY = "amount_of_salary";

    /***************************************
     * User information Parameter
     ***************************************/
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String IS_GETTING_ANNOUNCEMENT = "is_getting_announcement";
    public static final String PASSWORD = "password";

    public static final String YEAR_REQUEST_PAYMENT = "year";
    public static final String MONTH_REQUEST_PAYMENT = "month";

    public static final String PAGE = "page";
    public static final String PER_PAGE = "per_page";
    public static final String PUBLISH_TYPE = "publish_type";
    public static final String OPEN = "open";

    private ApiParameter() {
    }

}
