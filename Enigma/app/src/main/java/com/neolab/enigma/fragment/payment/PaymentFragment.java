package com.neolab.enigma.fragment.payment;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.neolab.enigma.BuildConfig;
import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.dto.HeaderDto;
import com.neolab.enigma.dto.ws.payment.SalaryDto;
import com.neolab.enigma.fragment.BaseFragment;
import com.neolab.enigma.util.EniLogUtil;
import com.neolab.enigma.ws.ApiCode;
import com.neolab.enigma.ws.ApiRequest;
import com.neolab.enigma.ws.core.ApiCallback;
import com.neolab.enigma.ws.core.ApiError;
import com.neolab.enigma.ws.respone.ErrorResponse;
import com.neolab.enigma.ws.respone.payment.FeeResponse;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * The class process prepayment for user
 *
 * @author LongHV
 */
public class PaymentFragment extends BaseFragment implements View.OnClickListener {

    private EditText mMoneyPrepaymentEditText;
    private View mApplyPrepaymentLayout;
    private View mApplyPrepaymentButton;

    private SalaryDto mSalaryDto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_payment);
        findView();
        initData();
        initEvent();
        return view;
    }

    @Override
    protected void initData() {
        // Disable apply prepayment layout
        mApplyPrepaymentLayout.setEnabled(false);
        mApplyPrepaymentButton.setEnabled(false);
        getMaxPaymentRequest();
    }

    /**
     * The method is used to get maximum money for the prepayment
     */
    private void getMaxPaymentRequest() {
        eniShowNowLoading(getActivity());
        ApiRequest.getMaxMoneyPrepayment(new ApiCallback<FeeResponse>() {
            @Override
            public void failure(RetrofitError retrofitError, ApiError apiError) {
                eniCancelNowLoading();
                ErrorResponse body = (ErrorResponse) retrofitError.getBodyAs(ErrorResponse.class);
                if (body == null) {
                    return;
                }
                // User stopped service
                if (body.code == ApiCode.USER_STOPPED_SERVICE) {
                    goStopServiceScreen();
                } else {
                    Toast.makeText(getActivity(), apiError.getError().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void success(FeeResponse feeResponse, Response response) {
                eniCancelNowLoading();
                if (feeResponse.statusCode == ApiCode.SUCCESS) {
                    if (feeResponse.data != null) {
                        mSalaryDto = feeResponse.data;
                    }
                }
            }
        });
    }

    @Override
    protected void findView() {
        mApplyPrepaymentButton = findViewById(R.id.payment_apply_for_prepayment_button);
        mApplyPrepaymentLayout = findViewById(R.id.payment_apply_for_prepayment_layout);
        mMoneyPrepaymentEditText = findViewById(R.id.payment_money_payment_editText);
    }

    @Override
    protected void initEvent() {
        mApplyPrepaymentLayout.setOnClickListener(this);
        mMoneyPrepaymentEditText.setOnFocusChangeListener(moneyPrepaymentChangeListener);
        mMoneyPrepaymentEditText.addTextChangedListener(moneyPaymentTextWatcher);
    }

    /**
     * Handle event focus edittext
     */
    private View.OnFocusChangeListener moneyPrepaymentChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                v.setBackgroundResource(R.drawable.edittext_payment_payment_focus);
            } else {
                v.setBackgroundResource(R.drawable.edittext_payment_payment_normal);
            }
        }
    };

    /**
     * Enable apply prepayment button when the text is changed
     */
    private TextWatcher moneyPaymentTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (mSalaryDto == null) {
                return;
            }
            if (mMoneyPrepaymentEditText.getText().toString().length() > 0
                    && mSalaryDto.maxPayment > 0
                    && Integer.parseInt(mMoneyPrepaymentEditText.getText().toString().trim()) > 0) {
                mApplyPrepaymentLayout.setEnabled(true);
                mApplyPrepaymentButton.setEnabled(true);
            } else {
                mApplyPrepaymentLayout.setEnabled(false);
                mApplyPrepaymentButton.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

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
            case R.id.payment_apply_for_prepayment_layout:
                ConfirmPaymentFragment confirmPaymentFragment = new ConfirmPaymentFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(ConfirmPaymentFragment.KEY_FEE, mSalaryDto);
                bundle.putString(ConfirmPaymentFragment.KEY_AMOUNT_MONEY_PAYMENT, mMoneyPrepaymentEditText.getText().toString());
                confirmPaymentFragment.setArguments(bundle);
                replaceFragment(confirmPaymentFragment, true);
                break;
            default:
                break;
        }
    }
}
