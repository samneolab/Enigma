package com.neolab.enigma.fragment.user;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.activity.LoginActivity;
import com.neolab.enigma.activity.MainActivity;
import com.neolab.enigma.dto.HeaderDto;
import com.neolab.enigma.fragment.BaseFragment;
import com.neolab.enigma.util.EniEncryptionUtil;

/**
 * Complete stopping using service screen
 *
 * @author Pika
 */
public class CompleteStopServiceFragment extends BaseFragment {

    private View mLoginScreenView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_user_complete_stop_service);
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
        mLoginScreenView = findViewById(R.id.user_complete_stop_service_go_login_screen_layout);
    }

    @Override
    protected void initEvent() {
        mLoginScreenView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.logout();
            }
        });
    }

    @Override
    protected HeaderDto getHeaderTypeDto() {
        HeaderDto headerDto = new HeaderDto();
        headerDto.title = getString(R.string.user_stop_service_title);
        headerDto.type = EniConstant.ToolbarType.ONLY_TITLE;
        return headerDto;
    }

}
