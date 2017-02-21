package com.neolab.enigma.fragment.payment;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.dto.HeaderDto;
import com.neolab.enigma.dto.ws.payment.SalaryDto;
import com.neolab.enigma.fragment.BaseFragment;
import com.neolab.enigma.util.EniLogUtil;
import com.neolab.enigma.util.EniUtil;
import com.neolab.enigma.ws.ApiCode;
import com.neolab.enigma.ws.ApiRequest;
import com.neolab.enigma.ws.core.ApiCallback;
import com.neolab.enigma.ws.core.ApiError;
import com.neolab.enigma.ws.respone.payment.PaymentRequestResponse;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author LongHV
 */
public class ConfirmPaymentFragment extends BaseFragment implements View.OnClickListener{

    public static final String KEY_FEE = "fee";
    public static final String KEY_AMOUNT_MONEY_PAYMENT = "amount_money_payment";

    private TextView mTitleTextView;
    private TextView mAmountSalaryPrepaymentTextView;
    private TextView mFeeUsageSystemTextView;
    private View mPrepaymentAmountExceededTextView;
    private FrameLayout mApplyPaymentLayout;

    private SalaryDto mSalaryDto;
    private int mAmountSalaryPrepayment;

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
        mApplyPaymentLayout = findViewById(R.id.confirm_payment_apply_layout);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mSalaryDto = bundle.getParcelable(KEY_FEE);
            mAmountSalaryPrepayment = Integer.parseInt(bundle.getString(KEY_AMOUNT_MONEY_PAYMENT)) * 1000;
            mAmountSalaryPrepaymentTextView.setText(EniUtil.convertMoneyFormat(mAmountSalaryPrepayment));
            mFeeUsageSystemTextView.setText(EniUtil.convertMoneyFormat(getFeeUsageSystem(mAmountSalaryPrepayment)));
            if (mAmountSalaryPrepayment <= mSalaryDto.maxPayment) {
                mPrepaymentAmountExceededTextView.setVisibility(View.GONE);
                mApplyPaymentLayout.setVisibility(View.VISIBLE);
                mTitleTextView.setText(getResources().getString(R.string.confirm_payment_confirm_apply_prepayment));
            } else {
                mPrepaymentAmountExceededTextView.setVisibility(View.VISIBLE);
                mApplyPaymentLayout.setVisibility(View.GONE);
                mTitleTextView.setText(getResources().getString(R.string.confirm_payment_please_check_information));
            }
        }
    }

    @Override
    protected void initEvent() {
        mApplyPaymentLayout.setOnClickListener(this);
    }

    @Override
    protected HeaderDto getHeaderTypeDto() {
        HeaderDto headerDto = new HeaderDto();
        headerDto.type = EniConstant.ToolbarType.DISPLAY_BACK_TITLE;
        headerDto.title = getResources().getString(R.string.payment_title);
        return headerDto;
    }

    /**
     * Calculate the fee usage system
     *
     * @param salaryPayment the salary has payment
     * @return fee usage system
     */
    private int getFeeUsageSystem(int salaryPayment) {
        return (int) (((mSalaryDto.transactionFeeRate + mSalaryDto.transactionKickbackRate) * salaryPayment / 100) + mSalaryDto.transferFee);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.confirm_payment_apply_layout:
                doPaymentRequest();
                break;
            default:
                break;
        }
    }

    /**
     * Call payment request salary
     */
    private void doPaymentRequest(){
        eniShowNowLoading(getActivity());
        ApiRequest.paymentRequest(mAmountSalaryPrepayment, new ApiCallback<PaymentRequestResponse>() {
            @Override
            public void failure(RetrofitError retrofitError, ApiError apiError) {
                eniCancelNowLoading();
            }

            @Override
            public void success(PaymentRequestResponse paymentRequestResponse, Response response) {
                eniCancelNowLoading();
                if (paymentRequestResponse.statusCode == ApiCode.PAYMENT_REQUEST_SUCCESS){
                    if (paymentRequestResponse.data == null) {
                        return;
                    }
                    CompletePaymentFragment fragment = new CompletePaymentFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(CompletePaymentFragment.KEY_PAYMENT_REQUEST, paymentRequestResponse.data);
                    fragment.setArguments(bundle);

                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_pop_enter, R.anim.fragment_pop_exit);
//                    manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    manager.executePendingTransactions();
                    transaction.replace(R.id.main_root_frameLayout, fragment);
                    transaction.commit();
                }
            }
        });
    }
}
