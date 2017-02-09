package com.neolab.enigma.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.neolab.enigma.BuildConfig;
import com.neolab.enigma.R;
import com.neolab.enigma.util.EniLogUtil;
import com.neolab.enigma.ws.ApiRequest;
import com.neolab.enigma.ws.core.ApiCallback;
import com.neolab.enigma.ws.core.ApiError;
import com.neolab.enigma.ws.respone.LoginResponse;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * The activity is used to user login
 *
 * @author LongHV
 */
public class LoginActivity extends BaseActivity implements OnClickListener{

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
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        findView();
    }

    /**
     * The method is used to find the views that was identified in layout
     */
    private void findView() {
        mTitleTextView = (TextView) findViewById(R.id.login_title_textView);
        mCompanyCodeEditText = (EditText) findViewById(R.id.login_company_code_editText);
        mEmployeeCodeEditText = (EditText) findViewById(R.id.login_employee_code_editText);
        mEmployeePasswordEditText = (EditText) findViewById(R.id.login_password_editText);
        mShowPasswordCheckBox = (CheckBox) findViewById(R.id.login_show_password_checkBox);
        mResetPasswordViaEmailLayout = (RelativeLayout) findViewById(R.id.login_reset_password_via_email_relativeLayout);
        mResetPasswordViaPhoneLayout = (RelativeLayout) findViewById(R.id.login_reset_password_via_phone_relativeLayout);
        mLoginButton = (Button) findViewById(R.id.login_button);

        mResetPasswordViaEmailLayout.setOnClickListener(this);
        mResetPasswordViaPhoneLayout.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
        mCompanyCodeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (BuildConfig.DEBUG) {
                    EniLogUtil.d(LoginActivity.class, "[onTextChanged] + length" + s.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                loginButtonClick();
                break;
        }
    }

    /**
     * Process click login button
     */
    private void loginButtonClick() {
        String companyCode = mCompanyCodeEditText.getText().toString();
        String employeeCode = mEmployeeCodeEditText.getText().toString();
        String employeePassword = mEmployeePasswordEditText.getText().toString();

        if (companyCode.length() > 0 && companyCode.length() <= 255
                && employeeCode.length() > 0 && employeeCode.length() <= 255
                && employeePassword.length() >=6 && employeePassword.length() <= 255) {
            doHttpRequestLogin(companyCode, employeeCode, employeePassword);
        }
    }

    /**
     * Execute the login request and display a dialog.
     *
     * @param companyCode Company code
     * @param employeeCode Employee code
     * @param employeePassword Employee password
     */
    private void doHttpRequestLogin(String companyCode, String employeeCode, String employeePassword){
        ApiRequest.login(companyCode, employeeCode, employeePassword, new ApiCallback<LoginResponse>() {
            @Override
            public void failure(RetrofitError retrofitError, ApiError apiError) {
            }

            @Override
            public void success(LoginResponse loginResponse, Response response) {

            }
        });

    }
}
