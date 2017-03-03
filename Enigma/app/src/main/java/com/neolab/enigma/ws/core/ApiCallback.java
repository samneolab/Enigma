package com.neolab.enigma.ws.core;

import com.neolab.enigma.BuildConfig;
import com.neolab.enigma.util.EniDialogUtil;
import com.neolab.enigma.util.EniLogUtil;
import com.neolab.enigma.ws.respone.ErrorResponse;

import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * A custom Callback.
 *
 * @param <T> expected response type
 */
public abstract class ApiCallback<T> implements Callback<T> {

    public abstract void failure(RetrofitError retrofitError, ApiError apiError);

    @Override
    public void failure(RetrofitError error) {
        if (BuildConfig.DEBUG) {
            EniLogUtil.d(getClass(), "[failure ] RetrofitError kind:" + error.getKind());
        }
        switch (error.getKind()) {
            case NETWORK:
                failure(error, ApiError.ERROR_NETWORK);
                break;
            default:
                failure(error, ApiError.ERROR_PLEASE_TRY_LATER);
        }
    }
}
