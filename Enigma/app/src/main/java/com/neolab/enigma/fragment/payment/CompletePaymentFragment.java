package com.neolab.enigma.fragment.payment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.dto.HeaderDto;
import com.neolab.enigma.dto.ws.payment.PaymentRequestDto;
import com.neolab.enigma.fragment.BaseFragment;
import com.neolab.enigma.fragment.top.TopFragment;
import com.neolab.enigma.util.EniUtil;

/**
 * @author LongHV
 */
public class CompletePaymentFragment extends BaseFragment {

    public static final String KEY_PAYMENT_REQUEST = "paymentRequestDto";

    private TextView mAmountSalaryPayment;
    private TextView mTitleTextView;
    private View mTopLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_complete_payment);
        findView();
        initData();
        initEvent();
        return view;
    }

    @Override
    protected void findView() {
        mAmountSalaryPayment = findViewById(R.id.complete_payment_amount_salary_textView);
        mTitleTextView = findViewById(R.id.complete_payment_title_textView);
        mTopLayout = findViewById(R.id.complete_payment_top_layout);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            PaymentRequestDto paymentRequestDto = bundle.getParcelable(KEY_PAYMENT_REQUEST);
            mAmountSalaryPayment.setText(EniUtil.convertMoneyFormat(paymentRequestDto.total));
            mTitleTextView.setText(paymentRequestDto.completeMessage);
        }
    }

    @Override
    protected void initEvent() {
        mTopLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new TopFragment(), false);
            }
        });
    }

    @Override
    protected HeaderDto getHeaderTypeDto() {
        HeaderDto headerDto = new HeaderDto();
        headerDto.type = EniConstant.ToolbarType.ONLY_TITLE;
        headerDto.title = getResources().getString(R.string.payment_title);
        return headerDto;
    }

}
