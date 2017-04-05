package com.neolab.enigma.fragment.payment;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.dto.HeaderDto;
import com.neolab.enigma.fragment.BaseFragment;
import com.neolab.enigma.util.EniFormatUtil;
import com.neolab.enigma.ws.ApiCode;
import com.neolab.enigma.ws.ApiRequest;
import com.neolab.enigma.ws.core.ApiCallback;
import com.neolab.enigma.ws.core.ApiError;
import com.neolab.enigma.ws.respone.ErrorResponse;
import com.neolab.enigma.ws.respone.payment.CaptchaErrorResponse;
import com.neolab.enigma.ws.respone.payment.CaptchaImageResponse;
import com.neolab.enigma.ws.respone.payment.PaymentRequestResponse;
import com.neolab.enigma.ws.respone.payment.ValidateMoneyPaymentResponse;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Confirm information prepayment
 *
 * @author Pika Long
 */
public class ConfirmPaymentFragment extends BaseFragment implements View.OnClickListener {

    public static final String KEY_AMOUNT_MONEY_PAYMENT = "amount_money_payment";

    /** Captcha image width */
    private static final int CAPTCHA_IMAGE_WIDTH = 800;
    /** Captcha image height */
    private static final int CAPTCHA_IMAGE_HEIGHT = 200;

    private View mPrepaymentAmountExceededTextView;
    private View mCaptchaLayout;
    private View mRefreshCaptchaView;

    private TextView mTitleTextView;
    private TextView mAmountSalaryPrepaymentTextView;
    private TextView mFeeUsageSystemTextView;
    private TextView mConsumptionTaxTextView;
    private TextView mRecivedMoneyTextView;
    private TextView mCaptchaMessageErrorTextView;

    private EditText mCaptchaCodeEditText;
    private FrameLayout mApplyPaymentLayout;
    private Button mApplyPaymentButton;
    private ImageView mCaptchaImageView;

    private int mAmountSalaryPrepayment;
    private String mCaptchaId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_confirm_payment);
        findView();
        initData();
        initEvent();
        return view;
    }

    @Override
    protected void findView() {
        mTitleTextView = findViewById(R.id.confirm_payment_title_textView);
        mAmountSalaryPrepaymentTextView = findViewById(R.id.confirm_payment_salary_prepayment_textView);
        mPrepaymentAmountExceededTextView = findViewById(R.id.confirm_payment_prepayment_amount_exceeded);
        mFeeUsageSystemTextView = findViewById(R.id.confirm_payment_fee_usage_system_textView);
        mConsumptionTaxTextView = findViewById(R.id.confirm_payment_consumption_tax_textView);
        mRecivedMoneyTextView = findViewById(R.id.confirm_payment_received_money_textView);
        mApplyPaymentLayout = findViewById(R.id.confirm_payment_apply_layout);
        mApplyPaymentButton = findViewById(R.id.confirm_payment_apply_button);
        mCaptchaImageView = findViewById(R.id.confirm_payment_captcha_imageView);
        mCaptchaLayout = findViewById(R.id.confirm_payment_captcha_layout);
        mCaptchaMessageErrorTextView = findViewById(R.id.confirm_payment_captcha_error_textView);
        mRefreshCaptchaView = findViewById(R.id.confirm_payment_refresh_captcha_code_layout);
        mCaptchaCodeEditText = findViewById(R.id.confirm_payment_captcha_code_editText);
    }

    @Override
    protected void initData() {
        mApplyPaymentLayout.setEnabled(false);
        mApplyPaymentButton.setEnabled(false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mAmountSalaryPrepayment = Integer.parseInt(bundle.getString(KEY_AMOUNT_MONEY_PAYMENT)) * 1000;
            mAmountSalaryPrepaymentTextView.setText(EniFormatUtil.convertMoneyFormat(mAmountSalaryPrepayment));
            doValidateSalaryPayment(mAmountSalaryPrepayment);
        }
    }

    /**
     * Call api validate salary payment
     *
     * @param amountOfSalary Amount of salary
     */
    private void doValidateSalaryPayment(int amountOfSalary) {
        eniShowNowLoading(getActivity());
        ApiRequest.validateMoneyPrepayment(amountOfSalary, new ApiCallback<ValidateMoneyPaymentResponse>() {
            @Override
            public void failure(RetrofitError retrofitError, ApiError apiError) {
                eniCancelNowLoading();
                ErrorResponse body = (ErrorResponse) retrofitError.getBodyAs(ErrorResponse.class);
                if (body == null) {
                    return;
                }
                // User stopped service
                if (body.code == ApiCode.USER_STOPPED_SERVICE) {
                    goStopServiceScreen(body.message);
                } else {
                    mApplyPaymentLayout.setEnabled(false);
                    mApplyPaymentButton.setEnabled(false);
                    mPrepaymentAmountExceededTextView.setVisibility(View.VISIBLE);
                    mCaptchaLayout.setVisibility(View.GONE);
                    mTitleTextView.setText(getResources().getString(R.string.confirm_payment_please_check_information));
                }
            }

            @Override
            public void success(ValidateMoneyPaymentResponse validateMoneyPaymentResponse, Response response) {
                if (validateMoneyPaymentResponse == null) {
                    return;
                }
                requestCaptchaImage();
                mPrepaymentAmountExceededTextView.setVisibility(View.GONE);
                mCaptchaLayout.setVisibility(View.VISIBLE);
                mTitleTextView.setText(getResources().getString(R.string.confirm_payment_confirm_apply_prepayment));
                mFeeUsageSystemTextView.setText(String.valueOf(validateMoneyPaymentResponse.data.totalFee));
                mConsumptionTaxTextView.setText(String.valueOf(validateMoneyPaymentResponse.data.amountOfConsumptionTax));
                mRecivedMoneyTextView.setText(String.valueOf(validateMoneyPaymentResponse.data.receivedMoney));
            }
        });
    }

    /**
     * Call api to get captcha image
     */
    private void requestCaptchaImage() {
        ApiRequest.getCaptchaImage(new ApiCallback<CaptchaImageResponse>() {
            @Override
            public void failure(RetrofitError retrofitError, ApiError apiError) {
                eniCancelNowLoading();
                CaptchaErrorResponse body = (CaptchaErrorResponse) retrofitError.getBodyAs(CaptchaErrorResponse.class);
                if (body == null) {
                    return;
                }
                if (body.error.captcha != null && body.error.captcha.size() > 0) {
                    mCaptchaMessageErrorTextView.setVisibility(View.VISIBLE);
                    mCaptchaMessageErrorTextView.setText(body.error.captcha.get(0));
                }
                mApplyPaymentLayout.setEnabled(false);
                mApplyPaymentButton.setEnabled(false);
            }

            @Override
            public void success(final CaptchaImageResponse captchaImageResponse, Response response) {
                if (captchaImageResponse == null) {
                    return;
                }
                mCaptchaMessageErrorTextView.setVisibility(View.GONE);
                CaptchaImageResponse.CaptchaImage captchaImage = captchaImageResponse.data;
                mCaptchaId = captchaImage.captchaId;
                Picasso.with(getActivity()).load(captchaImage.captchaUrl)
                        .resize(CAPTCHA_IMAGE_WIDTH, CAPTCHA_IMAGE_HEIGHT)
                        .into(mCaptchaImageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                eniCancelNowLoading();
                            }

                            @Override
                            public void onError() {
                                // Reload image
                                Picasso.with(getActivity()).load(captchaImageResponse.data.captchaUrl)
                                        .resize(CAPTCHA_IMAGE_WIDTH, CAPTCHA_IMAGE_HEIGHT)
                                        .into(mCaptchaImageView);
                            }
                        });
            }
        });
    }

    @Override
    protected void initEvent() {
        mApplyPaymentLayout.setOnClickListener(this);
        mRefreshCaptchaView.setOnClickListener(this);
        mCaptchaCodeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mCaptchaCodeEditText.getText().toString().trim().length() > 0) {
                    mApplyPaymentLayout.setEnabled(true);
                    mApplyPaymentButton.setEnabled(true);
                } else {
                    mApplyPaymentLayout.setEnabled(false);
                    mApplyPaymentButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected HeaderDto getHeaderTypeDto() {
        HeaderDto headerDto = new HeaderDto();
        headerDto.type = EniConstant.ToolbarType.DISPLAY_BACK_TITLE;
        headerDto.title = getResources().getString(R.string.payment_title);
        return headerDto;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_payment_apply_layout:
                doPaymentRequest();
                break;
            case R.id.confirm_payment_refresh_captcha_code_layout:
                eniShowNowLoading(getActivity());
                requestCaptchaImage();
                break;
            default:
                break;
        }
    }

    /**
     * Call payment request salary
     */
    private void doPaymentRequest() {
        eniShowNowLoading(getActivity());
        String captchaCode = mCaptchaCodeEditText.getText().toString().trim();
        ApiRequest.paymentRequest(mAmountSalaryPrepayment, captchaCode, mCaptchaId, new ApiCallback<PaymentRequestResponse>() {
            @Override
            public void failure(RetrofitError retrofitError, ApiError apiError) {
                eniCancelNowLoading();
                CaptchaErrorResponse body = (CaptchaErrorResponse) retrofitError.getBodyAs(CaptchaErrorResponse.class);
                if (body == null) {
                    return;
                }
                // User stopped service
                if (body.code == ApiCode.USER_STOPPED_SERVICE) {
                    goStopServiceScreen(body.message);
                } else if (body.error.captcha != null && body.error.captcha.size() > 0) {
                    mCaptchaMessageErrorTextView.setVisibility(View.VISIBLE);
                    mCaptchaMessageErrorTextView.setText(body.error.captcha.get(0));
                } else {
                    Toast.makeText(getActivity(), apiError.getError().getMessage(), Toast.LENGTH_SHORT).show();
                }
                mApplyPaymentLayout.setEnabled(false);
                mApplyPaymentButton.setEnabled(false);

            }

            @Override
            public void success(PaymentRequestResponse paymentRequestResponse, Response response) {
                eniCancelNowLoading();
                if (paymentRequestResponse.statusCode == ApiCode.PAYMENT_REQUEST_SUCCESS) {
                    if (paymentRequestResponse.data == null) {
                        return;
                    }
                    CompletePaymentFragment fragment = new CompletePaymentFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(CompletePaymentFragment.KEY_PAYMENT_REQUEST, paymentRequestResponse.data);
                    fragment.setArguments(bundle);
                    replaceFragment(fragment, false);
                }
            }
        });
    }
}
