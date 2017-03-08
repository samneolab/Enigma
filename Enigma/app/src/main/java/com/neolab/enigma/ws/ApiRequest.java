package com.neolab.enigma.ws;

import com.neolab.enigma.ws.core.ApiCallback;
import com.neolab.enigma.ws.core.ApiClient;
import com.neolab.enigma.ws.respone.ApiResponse;
import com.neolab.enigma.ws.respone.announcement.AnnouncementDetailResponse;
import com.neolab.enigma.ws.respone.announcement.AnnouncementResponse;
import com.neolab.enigma.ws.respone.announcement.EmergencyAnnouncementResponse;
import com.neolab.enigma.ws.respone.history.CancelPaymentResponse;
import com.neolab.enigma.ws.respone.history.DetailPaymentResponse;
import com.neolab.enigma.ws.respone.history.HistoryThisMonthResponse;
import com.neolab.enigma.ws.respone.history.MonthPaymentResponse;
import com.neolab.enigma.ws.respone.login.LoginResponse;
import com.neolab.enigma.ws.respone.payment.FeeResponse;
import com.neolab.enigma.ws.respone.payment.MoneyPrepaymentResponse;
import com.neolab.enigma.ws.respone.payment.PaymentRequestResponse;
import com.neolab.enigma.ws.respone.user.StopServiceResponse;
import com.neolab.enigma.ws.respone.user.UserAgreeTermResponse;
import com.neolab.enigma.ws.respone.user.UserChangePasswordResponse;
import com.neolab.enigma.ws.respone.user.UserLogoutResponse;
import com.neolab.enigma.ws.respone.user.UserUpdateInforResponse;
import com.neolab.enigma.ws.respone.user.UserInformationResponse;

import java.util.Map;

/**
 * Use to request data from server.
 *
 * @author Pika
 */
public final class ApiRequest {

    /**
     * A callback for result request
     */
    public interface Callback {
        void onSuccess();

        void onFailure();
    }

    /**
     * Constructor
     */
    private ApiRequest() {
    }

    /**
     * Login request
     *
     * @param companyCode      Company code
     * @param employeeCode     Employee code
     * @param employeePassword Employee password
     * @param callback         LoginResponse
     */
    public static void login(String companyCode, String employeeCode, String employeePassword
            , ApiCallback<LoginResponse> callback) {
        ApiClient.getService().login(companyCode, employeeCode, employeePassword, callback);
    }

    /**
     * Get emergency announcement
     *
     * @param callback Announcement callback
     */
    public static void getEmergencyAnnouncement(ApiCallback<EmergencyAnnouncementResponse> callback) {
        ApiClient.getService().getEmergencyAnnouncement(callback);
    }

    /**
     * Get announcement list
     *
     * @param callback Announcement list callback
     */
    public static void getAnnouncementList(Map<String, String> params, ApiCallback<AnnouncementResponse> callback) {
        ApiClient.getService().getAnnouncementList(params, callback);
    }

    /**
     * Get money available for prepayment
     *
     * @param callback MoneyPrepaymentDto callback
     */
    public static void getMoneyAvailableForPrepayment(ApiCallback<MoneyPrepaymentResponse> callback) {
        ApiClient.getService().getMoneyAvailableForPrepayment(callback);
    }

    /**
     * Get announcement detail
     *
     * @param callback Announcement detail callback
     */
    public static void getAnnouncementDetail(int id, ApiCallback<AnnouncementDetailResponse> callback) {
        ApiClient.getService().getAnnouncementDetail(id, callback);
    }

    /**
     * Get announcement detail
     *
     * @param callback Announcement detail callback
     */
    public static void getMaxMoneyPrepayment(ApiCallback<FeeResponse> callback) {
        ApiClient.getService().getMaxMoneyPrepayment(callback);
    }

    /**
     * Request prepayment salary
     *
     * @param amountOfSalary the salary has requested
     * @param callback       Callback
     */
    public static void paymentRequest(int amountOfSalary, ApiCallback<PaymentRequestResponse> callback) {
        ApiClient.getService().paymentRequest(amountOfSalary, callback);
    }

    /**
     * Get history payment salary of month
     *
     * @param callback Callback
     */
    public static void getHistoryRecentPaymentOfMonth(Map<String, Integer> value, ApiCallback<HistoryThisMonthResponse> callback) {
        if (value.size() == 0) {
            // Get recent history payment of this month
            ApiClient.getService().getHistoryRecentPaymentOfMonth(callback);
        } else {
            // Get recent history payment of month with year and month
            ApiClient.getService().getListHistoryPaymentOfMonth(value, callback);
        }
    }

    /**
     * Get information detail of payment
     *
     * @param id id of request payment
     * @param callback DetailPaymentResponse
     */
    public static void getDetailPayment(int id, ApiCallback<DetailPaymentResponse> callback) {
        ApiClient.getService().getDetailPayment(id, callback);
    }

    /**
     * Cancel payment request
     *
     * @param id requestId
     * @param callback CancelPaymentResponse
     */
    public static void cancelPaymentRequest(int id, ApiCallback<CancelPaymentResponse> callback){
        ApiClient.getService().cancelPaymentRequest(id, callback);
    }

    /**
     * Get month request payment list
     *
     * @param callback Callback
     */
    public static void getMonthRequestPaymentList(ApiCallback<MonthPaymentResponse> callback){
        ApiClient.getService().getMonthRequestPaymentList(callback);
    }

    /**
     * Get user information
     *
     * @param callback user information response
     */
    public static void getUserInformation(ApiCallback<UserInformationResponse> callback){
        ApiClient.getService().getUserInformation(callback);
    }

    /**
     * Update user information
     *
     * @param email email
     * @param isGettingAnnouncement set value that get from get user information api
     * @param callback user information response
     */
    public static void updateUserInformation(String email, int isGettingAnnouncement, ApiCallback<UserUpdateInforResponse> callback){
        ApiClient.getService().updateUserInformation(email, isGettingAnnouncement, callback);
    }

    /**
     * Stop using service api
     *
     * @param password email
     * @param callback stop using service response
     */
    public static void stopUsingService(String password, ApiCallback<StopServiceResponse> callback){
        ApiClient.getService().stopUsingService(password, callback);
    }


    /**
     * Agree with terms and conditions to use service
     *
     * @param agree 1 is agree, 0 isn't agree
     * @param callback Callback
     */
    public static void agreeUsingService(final int agree, final ApiCallback<UserAgreeTermResponse> callback){
        ApiClient.getService().agreeUsingService(agree, callback);
    }

    /**
     * Confirm account information before using service
     *
     * @param email email
     * @param password password
     * @param passwordConfirm password confirm
     * @param name name
     * @param callback Callback
     */
    public static void confirmAccountInformation(final String email, final String password,
                                                 final String passwordConfirm, final String name,final ApiCallback<UserChangePasswordResponse> callback){
        ApiClient.getService().confirmAccountInformation(email, password, passwordConfirm, name, callback);
    }

    /**
     * Logout
     *
     * @param callback callback
     */
    public static void logout(ApiCallback<UserLogoutResponse> callback) {
        ApiClient.getService().logout(callback);
    }

    /**
     * Reset password via email
     *
     * @param email Email
     * @param companyCode company Code
     * @param callback Callback
     */
    public static void resetPasswordViaMail(String email, String companyCode, ApiCallback<ApiResponse> callback) {
        ApiClient.getService().resetPasswordViaMail(email, companyCode, callback);
    }

    /**
     * Reset password via phone number
     *
     * @param companyCode Company code
     * @param employeeCode Employee code
     * @param employeeName Employee name
     * @param phoneNumber Phone Number
     * @param callback Callback
     */
    public static void resetPasswordViaPhone(String companyCode, String employeeCode, String employeeName, int phoneNumber, ApiCallback<ApiResponse> callback) {
        ApiClient.getService().resetPasswordViaPhone(companyCode, employeeCode, employeeName, phoneNumber, callback);
    }

}
