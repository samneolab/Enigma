package com.neolab.enigma.activity.user;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.activity.BaseActivity;
import com.neolab.enigma.activity.LoginActivity;
import com.neolab.enigma.ws.ApiRequest;
import com.neolab.enigma.ws.core.ApiCallback;
import com.neolab.enigma.ws.core.ApiError;
import com.neolab.enigma.ws.respone.user.TermUsingServiceResponse;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Display term and condition using service
 *
 * @author Pika
 */
public class TermServiceActivity extends BaseActivity implements View.OnClickListener {

    private CheckBox mAgreeCheckBox;
    private TextView mTitleTextView;
    private View mAgreeTermLayout;
    private Button mAgreeButton;
    private View mBackLayout;
    private WebView mTermAndConditionWebView;

    private String mNameOfUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_term_service);
        findView();
        initData();
        initEvent();
    }

    /**
     * Finds a view that was identified by the id attribute from the XML
     */
    private void findView() {
        mTitleTextView = (TextView) findViewById(R.id.title_textView);
        mAgreeCheckBox = (CheckBox) findViewById(R.id.user_term_service_agree_term_using_checkBox);
        mAgreeTermLayout = findViewById(R.id.user_term_service_agree_term_layout);
        mAgreeButton = (Button) findViewById(R.id.user_term_service_agree_term_button);
        mBackLayout = findViewById(R.id.user_term_service_back_layout);
        mTermAndConditionWebView = (WebView) findViewById(R.id.user_term_service_term_using_webView);
    }

    /**
     * Initialize
     */
    private void initData() {
        if (getIntent() != null){
            mNameOfUser = getIntent().getStringExtra(EniConstant.NAME_KEY);
        }

        mTitleTextView.setText(getString(R.string.user_term_service_title));
        mAgreeTermLayout.setEnabled(false);
        mAgreeButton.setEnabled(false);
        getTermsAndConditionUsingService();
    }

    private void getTermsAndConditionUsingService() {
        eniShowLoading();
        ApiRequest.getTermAndCondition(new ApiCallback<TermUsingServiceResponse>() {
            @Override
            public void failure(RetrofitError retrofitError, ApiError apiError) {
                eniCancelNowLoading();
            }

            @Override
            public void success(TermUsingServiceResponse termUsingServiceResponse, Response response) {
                eniCancelNowLoading();
                if (termUsingServiceResponse == null) {
                    return;
                }
                String termAndCondition = String.valueOf(Html
                        .fromHtml("<![CDATA[<body>"
                                + termUsingServiceResponse.data.pageContent
                                + "</body>]]>"));
                WebSettings webSettings = mTermAndConditionWebView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
                if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    webSettings.setAllowFileAccessFromFileURLs(true);
                    webSettings.setAllowUniversalAccessFromFileURLs(true);
                }
                mTermAndConditionWebView.loadData(termAndCondition, "text/html; charset=UTF-8", null);
            }
        });
    }

    /**
     * Handle button listener
     */
    private void initEvent() {
        mBackLayout.setOnClickListener(this);
        mAgreeTermLayout.setOnClickListener(this);

        mAgreeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mAgreeTermLayout.setEnabled(true);
                    mAgreeButton.setEnabled(true);
                } else {
                    mAgreeTermLayout.setEnabled(false);
                    mAgreeButton.setEnabled(false);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(LoginActivity.class, R.anim.animation_fade_in_left_to_right, R.anim.animation_fade_out_left_to_right);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_term_service_agree_term_layout:
                Intent intent = new Intent(TermServiceActivity.this, AccountConfirmationActivity.class);
                intent.putExtra(EniConstant.NAME_KEY, mNameOfUser);
                finish();
                startActivity(intent);
                overridePendingTransition(R.anim.animation_fade_in_right_to_left, R.anim.animation_fade_out_right_to_left);
                break;
            case R.id.user_term_service_back_layout:
                finish();
                startActivity(LoginActivity.class, R.anim.animation_fade_in_left_to_right, R.anim.animation_fade_out_left_to_right);
                break;
            default:
                break;
        }
    }
}
