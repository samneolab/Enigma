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
 * Reset password via email screen
 *
 * @author Pika
 */
public class ResetPasswordViaEmailActivity extends BaseActivity implements View.OnClickListener{

    private EditText mCompanyCodeEditText;
    private EditText mEmailEditText;
    private View mSendLayout;
    private Button mSendButton;
    private View mBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reset_password_via_email);
        findView();
        initData();
        initEvent();
    }

    private void findView() {
        mCompanyCodeEditText = (EniEditText) findViewById(R.id.user_reset_password_via_email_company_code_editText);
        mEmailEditText = (EniEditText) findViewById(R.id.user_reset_password_via_email_email_editText);
        mSendLayout = findViewById(R.id.user_reset_password_via_email_send_layout);
        mSendButton = (Button) findViewById(R.id.user_reset_password_via_email_send_button);
        mBackLayout = findViewById(R.id.toolbar_back_textView);
    }

    private void initData() {
        mSendLayout.setEnabled(false);
        mSendButton.setEnabled(false);
    }

    private void initEvent() {
        mCompanyCodeEditText.addTextChangedListener(userInformationTextWatcher);
        mEmailEditText.addTextChangedListener(userInformationTextWatcher);
        mSendLayout.setOnClickListener(this);
        mBackLayout.setOnClickListener(this);
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
            String companyCode = mCompanyCodeEditText.getText().toString();
            String emailAddress = mEmailEditText.getText().toString();
            if (EniValidateUtil.isValidUserInfor(companyCode) && EniValidateUtil.isValidateEmail(emailAddress)) {
                mSendLayout.setEnabled(true);
                mSendButton.setEnabled(true);
            } else {
                mSendLayout.setEnabled(false);
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
            case R.id.user_reset_password_via_email_send_layout:
                String email = mEmailEditText.getText().toString().trim();
                String companyCode = mCompanyCodeEditText.getText().toString().trim();
                resetPasswordViaEmail(email, companyCode);
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
     * Call api to reset password via email
     *
     * @param email Email
     * @param companyCode Company code
     */
    private void resetPasswordViaEmail(String email, String companyCode) {
        eniShowLoading();
        ApiRequest.resetPasswordViaMail(email, companyCode, new ApiCallback<ApiResponse>() {
            @Override
            public void failure(RetrofitError retrofitError, ApiError apiError) {
                eniCancelNowLoading();
                if (retrofitError == null) {
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
                startActivity(ResetPasswordViaEmailCompleteActivity.class);
            }
        });
    }
}
