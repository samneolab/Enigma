package com.neolab.enigma.fragment.history;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.dto.HeaderDto;
import com.neolab.enigma.dto.ws.History.SalaryRequestDto;
import com.neolab.enigma.fragment.BaseFragment;
import com.neolab.enigma.util.EniUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailHistoryPaymentFragment extends BaseFragment implements View.OnClickListener{

    public static final String KEY_SALARY_REQUEST = "salaryRequestDto";

    private TextView mDateRequestTextView;
    private TextView mSalaryRequestTextView;
    private TextView mFeeUsageSystemTextView;
    private View mBackLayout;
    private View mWithdrawRequestLayout;

    private SalaryRequestDto mSalaryRequestDto;

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
            mSalaryRequestDto = bundle.getParcelable(KEY_SALARY_REQUEST);
            mDateRequestTextView.setText(getString(R.string.detail_history_date_apply)
                    + EniConstant.SPACE + EniUtil.getDateRequestPaymentWithFormat(mSalaryRequestDto.appliedDate));
            mSalaryRequestTextView.setText(EniUtil.convertMoneyFormat(mSalaryRequestDto.amountOfSalary));
            // Calculate fee usage system
            int feeUsageSystem = mSalaryRequestDto.total - mSalaryRequestDto.amountOfSalary;
            mFeeUsageSystemTextView.setText(EniUtil.convertMoneyFormat(feeUsageSystem));
        }
    }

    @Override
    protected void findView() {
        mDateRequestTextView = findViewById(R.id.history_detail_date_request_textView);
        mSalaryRequestTextView = findViewById(R.id.history_detail_salary_request_textView);
        mFeeUsageSystemTextView = findViewById(R.id.history_detail_fee_usage_system_textView);
        mBackLayout = findViewById(R.id.history_detail_back_layout);
        mWithdrawRequestLayout = findViewById(R.id.history_detail_withdraw_request_layout);

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
        switch (v.getId()){
            case R.id.history_detail_back_layout:
                getActivity().onBackPressed();
                break;
            case R.id.history_detail_withdraw_request_layout:

                break;
            default:
                break;
        }
    }
}
