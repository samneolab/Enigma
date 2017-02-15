package com.neolab.enigma.ws;

import com.neolab.enigma.ws.core.ApiCallback;
import com.neolab.enigma.ws.core.ApiClient;
import com.neolab.enigma.ws.respone.FeeResponse;
import com.neolab.enigma.ws.respone.LoginResponse;
import com.neolab.enigma.ws.respone.MoneyPrepaymentResponse;
import com.neolab.enigma.ws.respone.PaymentRequestResponse;
import com.neolab.enigma.ws.respone.announcement.AnnouncementDetailResponse;
import com.neolab.enigma.ws.respone.announcement.AnnouncementResponse;
import com.neolab.enigma.ws.respone.announcement.EmergencyAnnouncementResponse;

/**
 * Use to request data from server.
 *
 * @author LongHV3
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
     * @param companyCode Company code
     * @param employeeCode Employee code
     * @param employeePassword Employee password
     * @param callback LoginResponse
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
    public static void getAnnouncementList(ApiCallback<AnnouncementResponse> callback) {
        ApiClient.getService().getAnnouncementList(callback);
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
     * @param callback Callback
     */
    public static void paymentRequest(int amountOfSalary, ApiCallback<PaymentRequestResponse> callback){
        ApiClient.getService().paymentRequest(amountOfSalary, callback);
    }

}
