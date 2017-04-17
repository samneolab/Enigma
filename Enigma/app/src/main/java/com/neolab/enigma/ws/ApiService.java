package com.neolab.enigma.ws;

import com.neolab.enigma.ws.core.ApiCallback;
import com.neolab.enigma.ws.respone.ApiResponse;
import com.neolab.enigma.ws.respone.history.CancelPaymentResponse;
import com.neolab.enigma.ws.respone.history.DetailPaymentResponse;
import com.neolab.enigma.ws.respone.history.MonthPaymentResponse;
import com.neolab.enigma.ws.respone.payment.CaptchaImageResponse;
import com.neolab.enigma.ws.respone.payment.FeeResponse;
import com.neolab.enigma.ws.respone.history.HistoryThisMonthResponse;
import com.neolab.enigma.ws.respone.login.LoginResponse;
import com.neolab.enigma.ws.respone.payment.MoneyPrepaymentResponse;
import com.neolab.enigma.ws.respone.payment.PaymentRequestResponse;
import com.neolab.enigma.ws.respone.announcement.AnnouncementDetailResponse;
import com.neolab.enigma.ws.respone.announcement.AnnouncementResponse;
import com.neolab.enigma.ws.respone.announcement.EmergencyAnnouncementResponse;
import com.neolab.enigma.ws.respone.payment.ValidateMoneyPaymentResponse;
import com.neolab.enigma.ws.respone.user.StopServiceResponse;
import com.neolab.enigma.ws.respone.user.TermUsingServiceResponse;
import com.neolab.enigma.ws.respone.user.UserAgreeTermResponse;
import com.neolab.enigma.ws.respone.user.UserChangePasswordResponse;
import com.neolab.enigma.ws.respone.user.UserLogoutResponse;
import com.neolab.enigma.ws.respone.user.UserUpdateInforResponse;
import com.neolab.enigma.ws.respone.user.UserInformationResponse;

import java.util.Map;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.QueryMap;

/**
 * A interface uses to request API.
 *
 * @author LongHV3
 */
public interface ApiService {

    String GET_CAPTCHA_IMAGE = "/captcha-images";
    String LOGIN = "/authenticate";
    String GET_EMERGENCY_ANNOUNCEMENT = "/announcements/emergency";
    String GET_ANNOUNCEMENT_LIST = "/announcements?per_page=3&publish_type=open";
    String GET_MONEY_AVAILABLE_FOR_PREPAYMENT = "/payment-request/available-payment";
    String GET_ANNOUNCEMENT_DETAIL = "/announcements/{id}";
    String GET_MAX_MONEY_PREPAYMENT = "/payment-request/fee-setting";
    String VALIDATE_MONEY_PREPAYMENT = "/payment-request/validate";
    String PAYMENT_REQUEST = "/payment-request";
    String GET_HISTORY_RECENT_PAYMENT_OF_MONTH = "/payment-request";
    String GET_DETAIL_PAYMENT = "/payment-request/{id}";
    String CANCEL_PAYMENT_REQUEST = "/payment-requests/{id}/status/3";
    String GET_MONTH_REQUEST_PAYMENT_LIST = "/payment-request/month";
    String GET_HISTORY_PAYMENT_OF_MONTH_LIST = "/payment-request";
    String GET_USER_INFORMATION = "/employee";
    String UPDATE_USER_INFORMATION = "/employee";
    String STOP_USING_SERVICE = "/employee/stop-service";
    String AGREE_USING_SERVICE = "/employee/agree";
    String CONFIRM_ACCOUNT_INFORMATION = "/employee/confirm";
    String LOGOUT = "/logout";
    String RESET_PASSWORD_VIA_EMAIL = "/password/email";
    String RESET_PASSWORD_VIA_PHONE = "/password/forgot";
    String GET_TERM_AND_CONDITION = "/employee/agree";

    @FormUrlEncoded
    @POST(LOGIN)
    void login(@Field(ApiParameter.COMPANY_CODE) String companyCode,
               @Field(ApiParameter.EMPLOYEE_CODE) String employeeCode,
               @Field(ApiParameter.EMPLOYEE_PASSWORD) String employeePassword,
               ApiCallback<LoginResponse> callback);

    @GET(GET_EMERGENCY_ANNOUNCEMENT)
    void getEmergencyAnnouncement(ApiCallback<EmergencyAnnouncementResponse> callback);

    @GET(GET_ANNOUNCEMENT_LIST)
    void getAnnouncementList(@QueryMap Map<String, String> params, ApiCallback<AnnouncementResponse> callback);

    @GET(GET_MONEY_AVAILABLE_FOR_PREPAYMENT)
    void getMoneyAvailableForPrepayment(ApiCallback<MoneyPrepaymentResponse> callback);

    @GET(GET_ANNOUNCEMENT_DETAIL)
    void getAnnouncementDetail(@Path("id") int id, ApiCallback<AnnouncementDetailResponse> callback);

    @GET(GET_MAX_MONEY_PREPAYMENT)
    void getMaxMoneyPrepayment(ApiCallback<FeeResponse> callback);

    @FormUrlEncoded
    @POST(VALIDATE_MONEY_PREPAYMENT)
    void validateMoneyPrepayment(@Field(ApiParameter.AMOUNT_OF_SALARY) int amountOfSalary, ApiCallback<ValidateMoneyPaymentResponse> callback);

    @FormUrlEncoded
    @POST(PAYMENT_REQUEST)
    void paymentRequest(@Field(ApiParameter.AMOUNT_OF_SALARY) int amountOfSalary, @Field(ApiParameter.CAPTCHA) String captcha,
                        @Field(ApiParameter.CAPCHA_ID) String captchaId, ApiCallback<PaymentRequestResponse> callback);

    @GET(GET_HISTORY_RECENT_PAYMENT_OF_MONTH)
    void getHistoryRecentPaymentOfMonth(ApiCallback<HistoryThisMonthResponse> callback);

    @GET(GET_DETAIL_PAYMENT)
    void getDetailPayment(@Path("id") int id, ApiCallback<DetailPaymentResponse> callback);

    @PUT(CANCEL_PAYMENT_REQUEST)
    void cancelPaymentRequest(@Path("id") int id, ApiCallback<CancelPaymentResponse> callback);

    @GET(GET_MONTH_REQUEST_PAYMENT_LIST)
    void getMonthRequestPaymentList(ApiCallback<MonthPaymentResponse> callback);

    @GET(GET_HISTORY_PAYMENT_OF_MONTH_LIST)
    void getListHistoryPaymentOfMonth(@QueryMap Map<String, Integer> value, ApiCallback<HistoryThisMonthResponse> callback);

    @GET(GET_USER_INFORMATION)
    void getUserInformation(ApiCallback<UserInformationResponse> callback);

    @PUT(UPDATE_USER_INFORMATION)
    @FormUrlEncoded
    void updateUserInformation(@Field(ApiParameter.EMAIL) String email, @Field(ApiParameter.IS_GETTING_ANNOUNCEMENT) int isGettingAnnouncement,
                               @Field(ApiParameter.CURRENT_PASSWORD) String currentPassword, ApiCallback<UserUpdateInforResponse> callback);

    @FormUrlEncoded
    @POST(STOP_USING_SERVICE)
    void stopUsingService(@Field(ApiParameter.PASSWORD) String password, ApiCallback<StopServiceResponse> callback);

    @FormUrlEncoded
    @POST(AGREE_USING_SERVICE)
    void agreeUsingService(@Field(ApiParameter.AGREE) int agree, ApiCallback<UserAgreeTermResponse> callback);

    @FormUrlEncoded
    @POST(CONFIRM_ACCOUNT_INFORMATION)
    void confirmAccountInformation(@Field(ApiParameter.EMAIL) String email, @Field(ApiParameter.PASSWORD) String password,
                                   @Field(ApiParameter.PASSWORD_CONFIRM) String passwordConfirm, @Field(ApiParameter.NAME) String name,
                                   ApiCallback<UserChangePasswordResponse> callback);

    @GET(LOGOUT)
    void logout(ApiCallback<UserLogoutResponse> callback);

    @FormUrlEncoded
    @POST(RESET_PASSWORD_VIA_EMAIL)
    void resetPasswordViaMail(@Field(ApiParameter.EMAIL) String email, @Field(ApiParameter.COMPANY_CODE) String companyCode,
                                   ApiCallback<ApiResponse> callback);

    @FormUrlEncoded
    @POST(RESET_PASSWORD_VIA_PHONE)
    void resetPasswordViaPhone(@Field(ApiParameter.COMPANY_CODE) String companyCode, @Field(ApiParameter.EMPLOYEE_CODE) String employeeCode,
                               @Field(ApiParameter.EMPLOYEE_NAME) String employeeName, @Field(ApiParameter.EMPLOYEE_PHONE) String phoneNumber,
                                ApiCallback<ApiResponse> callback);

    @GET(GET_TERM_AND_CONDITION)
    void getTermAndCondition(ApiCallback<TermUsingServiceResponse> callback);

    @GET(GET_CAPTCHA_IMAGE)
    void getCaptchaImage(ApiCallback<CaptchaImageResponse> callback);

}
