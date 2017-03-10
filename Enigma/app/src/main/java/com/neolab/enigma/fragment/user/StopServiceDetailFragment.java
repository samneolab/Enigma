package com.neolab.enigma.fragment.user;


import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.dto.HeaderDto;
import com.neolab.enigma.fragment.BaseFragment;
import com.neolab.enigma.util.EniDialogUtil;
import com.neolab.enigma.util.EniEncryptionUtil;
import com.neolab.enigma.util.EniValidateUtil;
import com.neolab.enigma.ws.ApiCode;
import com.neolab.enigma.ws.ApiRequest;
import com.neolab.enigma.ws.core.ApiCallback;
import com.neolab.enigma.ws.core.ApiError;
import com.neolab.enigma.ws.respone.ErrorResponse;
import com.neolab.enigma.ws.respone.user.StopServiceResponse;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Confirm stop service fragment
 *
 * @author Pika
 */
public class StopServiceDetailFragment extends BaseFragment implements View.OnClickListener, DialogInterface.OnClickListener{

    private EditText mPasswordEditText;
    private View mBackLayout;
    private View mStopUsingServiceLayout;
    private Button mStopUsingServiceButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_user_stop_service_detail);
        findView();
        initData();
        initEvent();
        return view;
    }

    @Override
    protected void findView() {
        mPasswordEditText = findViewById(R.id.user_stop_service_detail_password_editText);
        mBackLayout = findViewById(R.id.user_stop_service_detail_back_frameLayout);
        mStopUsingServiceLayout = findViewById(R.id.user_stop_service_detail_stop_using_frameLayout);
        mStopUsingServiceButton = findViewById(R.id.user_stop_service_detail_stop_using_button);

    }

    @Override
    protected void initData() {
        mStopUsingServiceLayout.setEnabled(false);
    }

    @Override
    protected void initEvent() {
        mBackLayout.setOnClickListener(this);
        mStopUsingServiceLayout.setOnClickListener(this);
        mPasswordEditText.addTextChangedListener(verifyPasswordTextWatcher);
    }

    @Override
    protected HeaderDto getHeaderTypeDto() {
        HeaderDto headerDto = new HeaderDto();
        headerDto.title = getString(R.string.user_stop_service_title);
        headerDto.type = EniConstant.ToolbarType.DISPLAY_BACK_TITLE;
        return headerDto;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_stop_service_detail_back_frameLayout:
                onBackPressed();
                break;
            case R.id.user_stop_service_detail_stop_using_frameLayout:
                String password = mPasswordEditText.getText().toString().trim();
                if (EniValidateUtil.isValidPassword(password)) {
                    stopUsingServiceApi(password);
                }
                break;
        }
    }

    /**
     * The method is used to call api stop using service
     */
    private void stopUsingServiceApi(String password) {
        eniShowNowLoading(getActivity());
        ApiRequest.stopUsingService(password, new ApiCallback<StopServiceResponse>() {
            @Override
            public void failure(RetrofitError retrofitError, ApiError apiError) {
                eniCancelNowLoading();
                if (retrofitError == null) {
                    return;
                }

                ErrorResponse body = (ErrorResponse) retrofitError.getBodyAs(ErrorResponse.class);
                if (body == null) {
                    return;
                }
                // User stopped service
                if (body.code == ApiCode.USER_STOPPED_SERVICE) {
                    goStopServiceScreen();
                } else {
                    EniDialogUtil.showAlertDialog(getFragmentManager(), getParentFragment(), body.message, getClass().getName());
                }
            }

            @Override
            public void success(StopServiceResponse stopServiceResponse, Response response) {
                eniCancelNowLoading();
                if (stopServiceResponse == null){
                    return;
                }
                EniEncryptionUtil.resetDataForLogout(getActivity());
                CompleteStopServiceFragment topFragment = new CompleteStopServiceFragment();
                replaceFragment(topFragment, false);
            }
        });
    }


    /**
     * Enable stop using service button when the text is changed
     */
    private final TextWatcher verifyPasswordTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String password = mPasswordEditText.getText().toString().trim();
            if (EniValidateUtil.isValidPassword(password)) {
                mStopUsingServiceLayout.setEnabled(true);
                mStopUsingServiceButton.setEnabled(true);
            } else {
                mStopUsingServiceLayout.setEnabled(false);
                mStopUsingServiceButton.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                break;
        }
    }
}
