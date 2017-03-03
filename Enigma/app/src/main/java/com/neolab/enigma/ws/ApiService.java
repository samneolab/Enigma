package com.neolab.enigma.ws;

import com.neolab.enigma.ws.core.ApiCallback;
import com.neolab.enigma.ws.respone.history.CancelPaymentResponse;
import com.neolab.enigma.ws.respone.history.DetailPaymentResponse;
import com.neolab.enigma.ws.respone.history.MonthPaymentResponse;
import com.neolab.enigma.ws.respone.payment.FeeResponse;
import com.neolab.enigma.ws.respone.history.HistoryThisMonthResponse;
import com.neolab.enigma.ws.respone.login.LoginResponse;
import com.neolab.enigma.ws.respone.payment.MoneyPrepaymentResponse;
import com.neolab.enigma.ws.respone.payment.PaymentRequestResponse;
import com.neolab.enigma.ws.respone.announcement.AnnouncementDetailResponse;
import com.neolab.enigma.ws.respone.announcement.AnnouncementResponse;
import com.neolab.enigma.ws.respone.announcement.EmergencyAnnouncementResponse;
import com.neolab.enigma.ws.respone.user.StopServiceResponse;
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


    @FormUrlEncoded
    @POST("/authenticate")
    void login(@Field(ApiParameter.COMPANY_CODE) String companyCode,
               @Field(ApiParameter.EMPLOYEE_CODE) String employeeCode,
               @Field(ApiParameter.EMPLOYEE_PASSWORD) String employeePassword,
               ApiCallback<LoginResponse> callback);

    @GET("/announcements/emergency")
    void getEmergencyAnnouncement(ApiCallback<EmergencyAnnouncementResponse> callback);

    @GET("/announcements?per_page=3&publish_type=open")
    void getAnnouncementList(@QueryMap Map<String, String> params, ApiCallback<AnnouncementResponse> callback);

    @GET("/payment-request/available-payment")
    void getMoneyAvailableForPrepayment(ApiCallback<MoneyPrepaymentResponse> callback);

    @GET("/announcements/{id}")
    void getAnnouncementDetail(@Path("id") int id, ApiCallback<AnnouncementDetailResponse> callback);

    @GET("/payment-request/fee-setting")
    void getMaxMoneyPrepayment(ApiCallback<FeeResponse> callback);

    @FormUrlEncoded
    @POST("/payment-request")
    void paymentRequest(@Field(ApiParameter.AMOUNT_OF_SALARY) int amountOfSalary, ApiCallback<PaymentRequestResponse> callback);

    @GET("/payment-request")
    void getHistoryRecentPaymentOfMonth(ApiCallback<HistoryThisMonthResponse> callback);

    @GET("/payment-request/{id}")
    void getDetailPayment(@Path("id") int id, ApiCallback<DetailPaymentResponse> callback);

    @PUT("/payment-requests/{id}/status/3")
    void cancelPaymentRequest(@Path("id") int id, ApiCallback<CancelPaymentResponse> callback);

    @GET("/payment-request/month")
    void getMonthRequestPaymentList(ApiCallback<MonthPaymentResponse> callback);

    @GET("/payment-request")
    void getListHistoryPaymentOfMonth(@QueryMap Map<String, Integer> value, ApiCallback<HistoryThisMonthResponse> callback);

    @GET("/employee")
    void getUserInformation(ApiCallback<UserInformationResponse> callback);

    @PUT("/employee")
    @FormUrlEncoded
    void updateUserInformation(@Field(ApiParameter.EMAIL) String email, @Field(ApiParameter.IS_GETTING_ANNOUNCEMENT) int isGettingAnnouncement,
                               ApiCallback<UserUpdateInforResponse> callback);

    @FormUrlEncoded
    @POST("/employee/stop-service")
    void stopUsingService(@Field(ApiParameter.PASSWORD) String password, ApiCallback<StopServiceResponse> callback);
}
