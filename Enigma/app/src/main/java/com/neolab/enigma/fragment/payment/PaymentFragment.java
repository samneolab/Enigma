package com.neolab.enigma.fragment.payment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.dto.ToolbarTypeDto;
import com.neolab.enigma.fragment.BaseFragment;

/**
 * The class process prepayment for user
 *
 * @author LongHV
 */
public class PaymentFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_prepayment);
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
        EditText editText = findViewById(R.id.payment_money_payment_editText);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    v.setBackgroundResource(R.drawable.payment_edittext_focus);
                } else {
                    v.setBackgroundResource(R.drawable.payment_edittext_normal);
                }
            }
        });
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected ToolbarTypeDto getToolbarTypeDto() {
        ToolbarTypeDto toolbarTypeDto = new ToolbarTypeDto();
        toolbarTypeDto.type = EniConstant.ToolbarType.DETAIL_NOT_DISPLAY_DRAWER;
        toolbarTypeDto.title = getResources().getString(R.string.payment_title);
        return toolbarTypeDto;
    }

}
