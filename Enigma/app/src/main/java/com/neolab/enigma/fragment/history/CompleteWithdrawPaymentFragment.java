package com.neolab.enigma.fragment.history;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.dto.HeaderDto;
import com.neolab.enigma.fragment.BaseFragment;
import com.neolab.enigma.fragment.top.TopFragment;

/**
 * @author LongHV
 */
public class CompleteWithdrawPaymentFragment extends BaseFragment{

    private View mTopLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_history_complete_withdraw_payment);
        findView();
        initData();
        initEvent();
        return view;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void findView() {
        mTopLayout = findViewById(R.id.complete_withdraw_payment_top_layout);
    }

    @Override
    protected void initEvent() {
        mTopLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new TopFragment(), false);
            }
        });
    }

    @Override
    protected HeaderDto getHeaderTypeDto() {
        HeaderDto headerDto = new HeaderDto();
        headerDto.type = EniConstant.ToolbarType.ONLY_TITLE;
        headerDto.title = getResources().getString(R.string.complete_withdraw_payment_title);
        return headerDto;
    }
}
