package com.neolab.enigma.ws;

import com.neolab.enigma.ws.core.ApiCallback;
import com.neolab.enigma.ws.respone.LoginResponse;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

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
}
