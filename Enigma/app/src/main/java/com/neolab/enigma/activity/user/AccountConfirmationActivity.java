package com.neolab.enigma.activity.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.activity.BaseActivity;
import com.neolab.enigma.activity.LoginActivity;
import com.neolab.enigma.preference.EncryptionPreference;
import com.neolab.enigma.ui.EniEditText;
import com.neolab.enigma.util.EniValidateUtil;
import com.neolab.enigma.ws.ApiCode;
import com.neolab.enigma.ws.ApiRequest;
import com.neolab.enigma.ws.core.ApiCallback;
import com.neolab.enigma.ws.core.ApiError;
import com.neolab.enigma.ws.respone.user.UserAgreeTermResponse;
import com.neolab.enigma.ws.respone.user.UserChangePasswordResponse;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Confirm account information when account is login the first time
 *
 * @author Pika
 */
public class AccountConfirmationActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTitleTextView;
    private TextView mNameTextView;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mPasswordConfirmEditText;
    private Button mStartUsingServiceButton;
    private View mStartUsingServiceView;
    private View mBackTopView;
    private CheckBox mShowPasswordCheckBox;

    /** Name of user */
    private String mNameOfUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_confirmation);
        findView();
        initData();
        initEvent();
    }

    /**
     * Finds a view that was identified by the id attribute from the XML
     */
    private void findView() {
        mTitleTextView = (TextView) findViewById(R.id.title_textView);
        mNameTextView = (TextView) findViewById(R.id.user_account_confirmation_name_textView);
        mEmailEditText = (EniEditText) findViewById(R.id.user_account_confirmation_email_editText);
        mPasswordEditText = (EniEditText) findViewById(R.id.user_account_confirmation_password_editText);
        mPasswordConfirmEditText = (EniEditText) findViewById(R.id.user_account_confirmation_confirm_editText);
        mStartUsingServiceButton = (Button) findViewById(R.id.user_account_confirmation_start_using_button);
        mStartUsingServiceView = findViewById(R.id.user_account_confirmation_start_using_layout);
        mBackTopView = findViewById(R.id.user_account_confirmation_back_layout);
        mShowPasswordCheckBox = (CheckBox) findViewById(R.id.user_account_confirmation_show_password_checkbox);
    }

    /**
     * Initialized data
     */
    private void initData() {
        if (getIntent() != null) {
            mNameOfUser = getIntent().getStringExtra(EniConstant.NAME_KEY);
            mNameTextView.setText(mNameOfUser);
        }
        mTitleTextView.setText(getString(R.string.user_account_confirmation_title));
        mStartUsingServiceView.setEnabled(false);
    }

    /**
     * Initialized event
     */
    private void initEvent() {
        mBackTopView.setOnClickListener(this);
        mStartUsingServiceView.setOnClickListener(this);
        mEmailEditText.addTextChangedListener(updatePasswordTextWatcher);
        mPasswordEditText.addTextChangedListener(updatePasswordTextWatcher);
        mPasswordConfirmEditText.addTextChangedListener(updatePasswordTextWatcher);
        mShowPasswordCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mPasswordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mPasswordConfirmEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    mPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mPasswordConfirmEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                mPasswordEditText.setSelection(mPasswordEditText.length());
                mPasswordConfirmEditText.setSelection(mPasswordConfirmEditText.length());
            }
        });
    }

    /**
     * Enable start using service button when the text is changed
     */
    private TextWatcher updatePasswordTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String email = mEmailEditText.getText().toString();
            String password = mPasswordEditText.getText().toString();
            String passwordConfirm = mPasswordConfirmEditText.getText().toString();
            if (EniValidateUtil.isValidUserPassword(password, passwordConfirm) && (EniValidateUtil.isValidateEmail(email) || email.isEmpty())) {
                mStartUsingServiceButton.setEnabled(true);
                mStartUsingServiceView.setEnabled(true);
            } else {
                mStartUsingServiceButton.setEnabled(false);
                mStartUsingServiceView.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_account_confirmation_back_layout:
                finish();
                startActivity(LoginActivity.class, R.anim.animation_fade_in_left_to_right, R.anim.animation_fade_out_left_to_right);
                break;
            case R.id.user_account_confirmation_start_using_layout:
                eniShowLoading();
                EncryptionPreference preference = new EncryptionPreference(getApplicationContext());
                // Call api change password when user has agreed with terms and conditions
                if (preference.loginStatusCode == EniConstant.UserStatus.AGREED_TERM_AND_CONDITION){
                    String email = mEmailEditText.getText().toString();
                    String password = mPasswordEditText.getText().toString();
                    String passwordConfirm = mPasswordConfirmEditText.getText().toString();
                    changePasswordApi(email, password, passwordConfirm, mNameOfUser);
                } else {
                    doHttpsAgreeWithTerm();
                }
                break;
        }
    }

    /**
     * Call api agree with terms and conditions
     */
    private void doHttpsAgreeWithTerm() {
        ApiRequest.agreeUsingService(EniConstant.USER_AGREE_WITH_TERM_NUMBER, new ApiCallback<UserAgreeTermResponse>() {
            @Override
            public void failure(RetrofitError retrofitError, ApiError apiError) {
                eniCancelNowLoading();
                if (apiError == null) {
                    return;
                }
                Toast.makeText(AccountConfirmationActivity.this, apiError.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void success(UserAgreeTermResponse userAgreeTermResponse, Response response) {
                if (userAgreeTermResponse == null || userAgreeTermResponse.statusCode != ApiCode.SUCCESS) {
                    return;
                }
                String email = mEmailEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                String passwordConfirm = mPasswordConfirmEditText.getText().toString();
                changePasswordApi(email, password, passwordConfirm, mNameOfUser);
            }
        });
    }

    /**
     * Call api confirm account information when user agree with terms and conditions
     */
    private void changePasswordApi(final String email, final String password, final String passwordConfirm, final String name) {
        ApiRequest.confirmAccountInformation(email, password, passwordConfirm, name, new ApiCallback<UserChangePasswordResponse>() {
            @Override
            public void failure(RetrofitError retrofitError, ApiError apiError) {
                eniCancelNowLoading();
                if (apiError == null) {
                    return;
                }
                Toast.makeText(AccountConfirmationActivity.this, apiError.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void success(UserChangePasswordResponse userChangePasswordResponse, Response response) {
                eniCancelNowLoading();
                if (userChangePasswordResponse == null || userChangePasswordResponse.statusCode != ApiCode.SUCCESS) {
                    return;
                }
                Toast.makeText(getApplicationContext(), userChangePasswordResponse.message, Toast.LENGTH_SHORT).show();
                finish();
                startActivity(RegisterCompleteActivity.class);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(LoginActivity.class, R.anim.animation_fade_in_left_to_right, R.anim.animation_fade_out_left_to_right);
    }
}
