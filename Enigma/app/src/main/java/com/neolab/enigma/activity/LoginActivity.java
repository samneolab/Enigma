package com.neolab.enigma.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.activity.user.AccountConfirmationActivity;
import com.neolab.enigma.activity.user.AccountPendingActivity;
import com.neolab.enigma.activity.user.TermServiceActivity;
import com.neolab.enigma.activity.user.UserStoppedServiceActivity;
import com.neolab.enigma.dto.user.UserDto;
import com.neolab.enigma.preference.EncryptionPreference;
import com.neolab.enigma.util.EniDialogUtil;
import com.neolab.enigma.util.EniEncryptionUtil;
import com.neolab.enigma.util.EniValidateUtil;
import com.neolab.enigma.ws.ApiCode;
import com.neolab.enigma.ws.ApiRequest;
import com.neolab.enigma.ws.core.ApiCallback;
import com.neolab.enigma.ws.core.ApiError;
import com.neolab.enigma.ws.respone.ErrorResponse;
import com.neolab.enigma.ws.respone.login.LoginErrorResponse;
import com.neolab.enigma.ws.respone.login.LoginResponse;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * The activity is used to user login
 *
 * @author LongHV
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

    /** Title toolbar textView */
    private TextView mTitleTextView;

    /** Company code editText */
    private EditText mCompanyCodeEditText;

    /** Employee code editText */
    private EditText mEmployeeCodeEditText;

    /** Employee password editText */
    private EditText mEmployeePasswordEditText;

    /** Show password checkbox */
    private CheckBox mShowPasswordCheckBox;

    /** Reset password via email layout */
    private RelativeLayout mResetPasswordViaEmailLayout;

    /** Reset password via phone layout */
    private RelativeLayout mResetPasswordViaPhoneLayout;

    /** Login Button */
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (EniEncryptionUtil.isLogin(this)) {
            finish();
            startActivity(MainActivity.class);
        }
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        findView();
        initData();
    }

    /**
     * The method is used to initialized data
     */
    private void initData() {
        mTitleTextView.setText(getString(R.string.login));
        mResetPasswordViaEmailLayout.setOnClickListener(this);
        mResetPasswordViaPhoneLayout.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);

        mCompanyCodeEditText.addTextChangedListener(userInformationTextWatcher);
        mEmployeeCodeEditText.addTextChangedListener(userInformationTextWatcher);
        mEmployeePasswordEditText.addTextChangedListener(userInformationTextWatcher);
        mShowPasswordCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mEmployeePasswordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    mEmployeePasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                mEmployeePasswordEditText.setSelection(mEmployeePasswordEditText.length());
            }
        });
    }

    /**
     * The method is used to find the views that was identified in layout
     */
    private void findView() {
        mTitleTextView = (TextView) findViewById(R.id.title_textView);
        mCompanyCodeEditText = (EditText) findViewById(R.id.login_company_code_editText);
        mEmployeeCodeEditText = (EditText) findViewById(R.id.login_employee_code_editText);
        mEmployeePasswordEditText = (EditText) findViewById(R.id.login_password_editText);
        mShowPasswordCheckBox = (CheckBox) findViewById(R.id.login_show_password_checkBox);
        mResetPasswordViaEmailLayout = (RelativeLayout) findViewById(R.id.login_reset_password_via_email_relativeLayout);
        mResetPasswordViaPhoneLayout = (RelativeLayout) findViewById(R.id.login_reset_password_via_phone_relativeLayout);
        mLoginButton = (Button) findViewById(R.id.login_button);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                loginButtonClick();
                break;
        }
    }

    /**
     * Enable login button when the text is changed
     */
    private TextWatcher userInformationTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String companyCode = mCompanyCodeEditText.getText().toString();
            String employeeCode = mEmployeeCodeEditText.getText().toString();
            String employeePassword = mEmployeePasswordEditText.getText().toString();
            if (EniValidateUtil.isValidUserInfor(companyCode, employeeCode, employeePassword)) {
                mLoginButton.setEnabled(true);
            } else {
                mLoginButton.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    /**
     * Process click login button
     */
    private void loginButtonClick() {
        String companyCode = mCompanyCodeEditText.getText().toString();
        String employeeCode = mEmployeeCodeEditText.getText().toString();
        String employeePassword = mEmployeePasswordEditText.getText().toString();

        if (EniValidateUtil.isValidUserInfor(companyCode, employeeCode, employeePassword)) {
            doHttpRequestLogin(companyCode, employeeCode, employeePassword);
        }
    }

    /**
     * Execute the login request and display a dialog.
     *
     * @param companyCode      Company code
     * @param employeeCode     Employee code
     * @param employeePassword Employee password
     */
    private void doHttpRequestLogin(String companyCode, String employeeCode, String employeePassword) {
        eniShowLoading();
        ApiRequest.login(companyCode, employeeCode, employeePassword, new ApiCallback<LoginResponse>() {
            @Override
            public void failure(RetrofitError retrofitError, ApiError apiError) {
                eniCancelNowLoading();
                ErrorResponse body = (ErrorResponse) retrofitError.getBodyAs(ErrorResponse.class);
                if (body == null) {
                    return;
                }
                // User stopped service or account don't approve
                if (body.code == ApiCode.USER_STOPPED_SERVICE_OR_ACCOUNT_UNAPPROVE) {
                    LoginErrorResponse errorResponse = (LoginErrorResponse) retrofitError.getBodyAs(LoginErrorResponse.class);
                    transferScreen(errorResponse.loginStateResponse.status);
                } else {
                    EniDialogUtil.showAlertDialog(getSupportFragmentManager(), null,  body.message, getClass().getName());
                }
            }

            @Override
            public void success(LoginResponse loginResponse, Response response) {
                eniCancelNowLoading();
                if (loginResponse == null) {
                    return;
                }
                UserDto userDto = loginResponse.data.userDto;
                // Save token and user information
                EncryptionPreference encryptionPreference = new EncryptionPreference(getApplicationContext());
                encryptionPreference.token = loginResponse.data.token;
                encryptionPreference.userId = String.valueOf(userDto.id);
                encryptionPreference.isUserLogin = true;
                encryptionPreference.loginStatusCode = userDto.status;
                encryptionPreference.write();
                transferScreen(userDto.status, userDto.name);
            }
        });
    }

    /**
     * Transfer to new screen corresponding
     *
     * @param status account state
     */
    private void transferScreen(final int status) {
        switch (status) {
            case EniConstant.UserStatus.CREATE_ACCOUNT:
                finish();
                startActivity(AccountPendingActivity.class);
                break;
            case EniConstant.UserStatus.STOP_SERVICE:
                finish();
                startActivity(UserStoppedServiceActivity.class);
                break;
            default:
                break;
        }
    }

    /**
     * Transfer to new screen corresponding
     *
     * @param status Account state
     * @param name Name of user
     */
    private void transferScreen(final int status, final String name) {
        Intent intent;
        switch (status) {
            case EniConstant.UserStatus.APPROVED:
                intent = new Intent(LoginActivity.this, TermServiceActivity.class);
                intent.putExtra(EniConstant.NAME_KEY, name);
                finish();
                startActivity(intent);
                overridePendingTransition(R.anim.animation_fade_in_right_to_left, R.anim.animation_fade_out_right_to_left);
                break;
            case EniConstant.UserStatus.AGREED_TERM_AND_CONDITION:
                intent = new Intent(LoginActivity.this, AccountConfirmationActivity.class);
                intent.putExtra(EniConstant.NAME_KEY, name);
                finish();
                startActivity(intent);
                overridePendingTransition(R.anim.animation_fade_in_right_to_left, R.anim.animation_fade_out_right_to_left);
                break;
            case EniConstant.UserStatus.MEMBER:
                finish();
                startActivity(MainActivity.class);
                break;
            default:
                break;
        }
    }
}
