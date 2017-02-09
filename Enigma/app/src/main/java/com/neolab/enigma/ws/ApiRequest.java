package com.neolab.enigma.ws;

import com.neolab.enigma.ws.core.ApiCallback;
import com.neolab.enigma.ws.core.ApiClient;
import com.neolab.enigma.ws.respone.LoginResponse;

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

    private ApiRequest() {
        // no instance
    }

    public static void login(String companyCode, String employeeCode, String employeePassword
            , ApiCallback<LoginResponse> callback) {
        ApiClient.getService().login(companyCode, employeeCode, employeePassword, callback);
    }

}
