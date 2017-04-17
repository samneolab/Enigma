package com.neolab.enigma.fragment.history;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.dto.HeaderDto;
import com.neolab.enigma.dto.ws.payment.DetailPaymentDto;
import com.neolab.enigma.fragment.BaseFragment;
import com.neolab.enigma.util.EniFormatUtil;
import com.neolab.enigma.util.EniLogUtil;
import com.neolab.enigma.util.EniUtil;
import com.neolab.enigma.ws.ApiCode;
import com.neolab.enigma.ws.ApiRequest;
import com.neolab.enigma.ws.core.ApiCallback;
import com.neolab.enigma.ws.core.ApiError;
import com.neolab.enigma.ws.respone.ErrorResponse;
import com.neolab.enigma.ws.respone.history.CancelPaymentResponse;
import com.neolab.enigma.ws.respone.history.DetailPaymentResponse;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailHistoryPaymentFragment extends BaseFragment implements View.OnClickListener {

    public static final String KEY_REQUEST_PAYMENT_ID = "mRequestId";

    private TextView mDateRequestTextView;
    private TextView mSalaryRequestTextView;
    private TextView mReceivedMoneyTextView;
    private TextView mTotalFeeIncludeTaxTextView;
    private View mBackLayout;
    private View mWithdrawRequestLayout;
    private View mWithdrawRequestButton;
    private int mRequestId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_history_detail_payment);
        findView();
        initData();
        initEvent();
        return view;
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mRequestId = bundle.getInt(KEY_REQUEST_PAYMENT_ID);
            getDetailPayment(mRequestId);
        }
    }

    @Override
    protected void findView() {
        mDateRequestTextView = findViewById(R.id.history_detail_date_request_textView);
        mSalaryRequestTextView = findViewById(R.id.history_detail_salary_request_textView);
        mBackLayout = findViewById(R.id.history_detail_back_layout);
        mWithdrawRequestLayout = findViewById(R.id.history_detail_withdraw_request_layout);
        mWithdrawRequestButton = findViewById(R.id.history_detail_withdraw_request_button);
        mReceivedMoneyTextView = findViewById(R.id.history_detail_received_money_textView);
        mTotalFeeIncludeTaxTextView = findViewById(R.id.history_detail_total_fee_include_tax_textView);

    }

    @Override
    protected void initEvent() {
        mBackLayout.setOnClickListener(this);
        mWithdrawRequestLayout.setOnClickListener(this);
    }

    @Override
    protected HeaderDto getHeaderTypeDto() {
        HeaderDto headerDto = new HeaderDto();
        headerDto.type = EniConstant.ToolbarType.DISPLAY_BACK_TITLE;
        headerDto.title = getResources().getString(R.string.detail_history_title);
        return headerDto;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.history_detail_back_layout:
                getActivity().onBackPressed();
                break;
            case R.id.history_detail_withdraw_request_layout:
                eniShowDialog(getActivity(), getString(R.string.detail_history_i_will_do_processing), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cancelPaymentRequest();
                    }
                }, null);
                break;
            default:
                break;
        }
    }

    /**
     * The method is used to get detail of payment
     *
     * @param requestId mRequestId
     */
    private void getDetailPayment(int requestId) {
        eniShowNowLoading(getActivity());
        ApiRequest.getDetailPayment(requestId, new ApiCallback<DetailPaymentResponse>() {
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
                    Toast.makeText(getActivity(), apiError.getError().getMessage(), Toast.LENGTH_SHORT).show();
                    mWithdrawRequestLayout.setClickable(false);
                    mWithdrawRequestButton.setEnabled(false);
                }
            }

            @Override
            public void success(DetailPaymentResponse detailPaymentResponse, Response response) {
                eniCancelNowLoading();
                mWithdrawRequestLayout.setClickable(true);
                mWithdrawRequestButton.setEnabled(true);
                if (detailPaymentResponse.statusCode != ApiCode.SUCCESS) {
                    return;
                }
                if (detailPaymentResponse.data == null) {
                    return;
                }
                DetailPaymentDto detailPaymentDto = detailPaymentResponse.data;
                mDateRequestTextView.setText(getString(R.string.detail_history_date_apply)
                        + EniConstant.SPACE + EniFormatUtil.getDateRequestPaymentWithFormat(detailPaymentDto.appliedAt));
                mSalaryRequestTextView.setText(EniFormatUtil.convertMoneyFormat(detailPaymentDto.total));
                mReceivedMoneyTextView.setText(String.valueOf(detailPaymentDto.amountOfSalary));
                mTotalFeeIncludeTaxTextView.setText(String.valueOf(detailPaymentDto.totalFeeIncludeTax));
            }
        });
    }

    /**
     * Call api to cancel payment that has requested
     */
    private void cancelPaymentRequest(){
        eniShowNowLoading(getActivity());
        ApiRequest.cancelPaymentRequest(mRequestId, new ApiCallback<CancelPaymentResponse>() {
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
                    Toast.makeText(getActivity(), apiError.getError().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void success(CancelPaymentResponse cancelPaymentResponse, Response response) {
                eniCancelNowLoading();
                EniLogUtil.d(getClass(), cancelPaymentResponse.message);
                if (cancelPaymentResponse.statusCode != ApiCode.SUCCESS){
                    return;
                }
                CompleteWithdrawPaymentFragment fragment = new CompleteWithdrawPaymentFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_pop_enter, R.anim.fragment_pop_exit);
//                manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                manager.executePendingTransactions();
                transaction.replace(R.id.main_root_frameLayout, fragment);
                transaction.commit();
//                replaceFragment(fragment, false);
            }
        });
    }
}
