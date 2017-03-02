package com.neolab.enigma.fragment.user;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.dto.HeaderDto;
import com.neolab.enigma.fragment.BaseFragment;

/**
 * Stop using service fragment
 *
 * @author Pika
 */
public class StopServiceFragment extends BaseFragment implements View.OnClickListener{

    private View mBackButton;
    private View mNextLayout;
    private Button mNextButton;
    private CheckBox mAgreeStopServiceCheckBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_user_stop_service);
        findView();
        initData();
        initEvent();
        return view;
    }

    @Override
    protected void findView() {
        mAgreeStopServiceCheckBox = findViewById(R.id.user_stop_service_agree_checkBox);
        mBackButton = findViewById(R.id.user_stop_service_back_frameLayout);
        mNextLayout = findViewById(R.id.user_stop_service_next_frameLayout);
        mNextButton = findViewById(R.id.user_stop_service_next_button);
    }

    @Override
    protected void initData() {
        mNextLayout.setEnabled(false);
    }

    @Override
    protected void initEvent() {
        mBackButton.setOnClickListener(this);
        mNextLayout.setOnClickListener(this);
        mNextLayout.setOnClickListener(this);

        mAgreeStopServiceCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mNextLayout.setEnabled(true);
                    mNextButton.setEnabled(true);
                } else {
                    mNextLayout.setEnabled(false);
                    mNextButton.setEnabled(false);
                }
            }
        });
    }

    @Override
    protected HeaderDto getHeaderTypeDto() {
        HeaderDto headerDto = new HeaderDto();
        headerDto.title = getString(R.string.user_stop_service_title);
        headerDto.type = EniConstant.ToolbarType.DISPLAY_BACK_TITLE;
        return headerDto;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_stop_service_back_frameLayout:
                onBackPressed();
                break;
            case R.id.user_stop_service_next_frameLayout:
                ConfirmStopServiceFragment fragment = new ConfirmStopServiceFragment();
                replaceFragment(fragment, true);
                break;
            default:
                break;
        }
    }
}
