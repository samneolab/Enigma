package com.neolab.enigma.ws;

import com.neolab.enigma.ws.core.ApiCallback;
import com.neolab.enigma.ws.respone.FeeResponse;
import com.neolab.enigma.ws.respone.LoginResponse;
import com.neolab.enigma.ws.respone.MoneyPrepaymentResponse;
import com.neolab.enigma.ws.respone.PaymentRequestResponse;
import com.neolab.enigma.ws.respone.announcement.AnnouncementDetailResponse;
import com.neolab.enigma.ws.respone.announcement.AnnouncementResponse;
import com.neolab.enigma.ws.respone.announcement.EmergencyAnnouncementResponse;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * A interface uses to request API.
 *
 * @author LongHV3
 */
public interface ApiService {


    @FormUrlEncoded
    @POST("/authenticate")
    void login(@Field(ApiParameter.COMPANY_CODE) String companyCode,
               @Field(ApiParameter.EMPLOYEE_CODE) String employeeCode,
               @Field(ApiParameter.EMPLOYEE_PASSWORD) String employeePassword,
               ApiCallback<LoginResponse> callback);

    @GET("/announcements/emergency")
    void getEmergencyAnnouncement(ApiCallback<EmergencyAnnouncementResponse> callback);

    @GET("/announcements?per_page=3&publish_type=open")
    void getAnnouncementList(ApiCallback<AnnouncementResponse> callback);

    @GET("/payment-request/available-payment")
    void getMoneyAvailableForPrepayment(ApiCallback<MoneyPrepaymentResponse> callback);

    @GET("/announcements/{id}")
    void getAnnouncementDetail(@Path("id") int id, ApiCallback<AnnouncementDetailResponse> callback);

    @GET("/payment-request/fee-setting")
    void getMaxMoneyPrepayment(ApiCallback<FeeResponse> callback);

    @FormUrlEncoded
    @POST("/payment-request")
    void paymentRequest(@Field(ApiParameter.AMOUNT_OF_SALARY) int amountOfSalary, ApiCallback<PaymentRequestResponse> callback);
}
