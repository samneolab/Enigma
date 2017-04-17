package com.neolab.enigma.activity.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.neolab.enigma.R;
import com.neolab.enigma.activity.BaseActivity;
import com.neolab.enigma.activity.LoginActivity;
import com.neolab.enigma.ui.EniEditText;
import com.neolab.enigma.util.EniDialogUtil;
import com.neolab.enigma.util.EniValidateUtil;
import com.neolab.enigma.ws.ApiRequest;
import com.neolab.enigma.ws.core.ApiCallback;
import com.neolab.enigma.ws.core.ApiError;
import com.neolab.enigma.ws.respone.ApiResponse;
import com.neolab.enigma.ws.respone.ErrorResponse;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Display reset password via phone number
 *
 * @author Pika
 */
public class ResetPasswordViaPhoneActivity extends BaseActivity implements View.OnClickListener{

    private EditText mCompanyIdEditText;
    private EditText mEmployeeIdEditText;
    private EditText mEmployeeNameEditText;
    private EditText mPhoneNumberEditText;

    private Button mSendButton;
    private View mSendView;
    private View mBackView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reset_password_via_phone);
        findView();
        initData();
        initEvent();
    }

    private void findView() {
        mCompanyIdEditText = (EniEditText) findViewById(R.id.user_reset_password_via_phone_companyId_editText);
        mEmployeeIdEditText = (EniEditText) findViewById(R.id.user_reset_password_via_phone_employeeId_editText);
        mEmployeeNameEditText = (EniEditText) findViewById(R.id.user_reset_password_via_phone_name_editText);
        mPhoneNumberEditText = (EniEditText) findViewById(R.id.user_reset_password_via_phone_phone_number_editText);
        mSendButton = (Button) findViewById(R.id.user_reset_password_via_phone_send_button);
        mSendView = findViewById(R.id.user_reset_password_via_phone_send_layout);
        mBackView = findViewById(R.id.toolbar_back_textView);
    }

    private void initData() {
        mSendView.setEnabled(false);
        mSendButton.setEnabled(false);
    }

    private void initEvent() {
        mCompanyIdEditText.addTextChangedListener(userInformationTextWatcher);
        mEmployeeIdEditText.addTextChangedListener(userInformationTextWatcher);
        mEmployeeNameEditText.addTextChangedListener(userInformationTextWatcher);
        mPhoneNumberEditText.addTextChangedListener(userInformationTextWatcher);
        mSendView.setOnClickListener(this);
        mBackView.setOnClickListener(this);
    }

    /**
     * Enable send button when the text is changed
     */
    private TextWatcher userInformationTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String companyId = mCompanyIdEditText.getText().toString();
            String employeeId = mEmployeeIdEditText.getText().toString();
            String name = mEmployeeNameEditText.getText().toString();
            String phoneNumber = mPhoneNumberEditText.getText().toString();
            if (EniValidateUtil.isValidUserInfor(companyId)
                    && EniValidateUtil.isValidUserInfor(employeeId)
                    && EniValidateUtil.isValidUserInfor(name)
                    && EniValidateUtil.isValidUserInfor(phoneNumber)) {
                mSendView.setEnabled(true);
                mSendButton.setEnabled(true);
            } else {
                mSendView.setEnabled(false);
                mSendButton.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_reset_password_via_phone_send_layout:
                String companyCode = mCompanyIdEditText.getText().toString().trim();
                String employeeCode = mEmployeeIdEditText.getText().toString().trim();
                String employeeName = mEmployeeNameEditText.getText().toString().trim();
                String phoneNumber = mPhoneNumberEditText.getText().toString().trim();
                resetPasswordViaPhone(companyCode, employeeCode, employeeName, phoneNumber);
                break;
            case R.id.toolbar_back_textView:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(LoginActivity.class, R.anim.animation_fade_in_left_to_right, R.anim.animation_fade_out_left_to_right);
    }

    /**
     * Call api request reset password via phone number
     *
     * @param companyCode Company code
     * @param employeeCode Employee code
     * @param employeeName Employee name
     * @param phoneNumber Phone number
     */
    private void resetPasswordViaPhone(String companyCode, String employeeCode, String employeeName, String phoneNumber) {
        eniShowLoading();
        ApiRequest.resetPasswordViaPhone(companyCode, employeeCode, employeeName, phoneNumber, new ApiCallback<ApiResponse>() {
            @Override
            public void failure(RetrofitError retrofitError, ApiError apiError) {
                eniCancelNowLoading();
                if (retrofitError == null){
                    return;
                }
                ErrorResponse body = (ErrorResponse) retrofitError.getBodyAs(ErrorResponse.class);
                EniDialogUtil.showAlertDialog(getSupportFragmentManager(), null,  body.message, getClass().getName());
            }

            @Override
            public void success(ApiResponse apiResponse, Response response) {
                eniCancelNowLoading();
                if (apiResponse == null) {
                    return;
                }
                finish();
                startActivity(ResetPasswordViaPhoneCompleteActivity.class);
            }
        });
    }
}
