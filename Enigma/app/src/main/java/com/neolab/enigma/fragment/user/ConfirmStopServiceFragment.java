package com.neolab.enigma.fragment.user;


import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.dto.HeaderDto;
import com.neolab.enigma.fragment.BaseFragment;
import com.neolab.enigma.util.EniDialogUtil;
import com.neolab.enigma.util.EniLogUtil;
import com.neolab.enigma.util.EniUtil;

/**
 * Confirm stop service fragment
 *
 * @author Pika
 */
public class ConfirmStopServiceFragment extends BaseFragment implements View.OnClickListener, DialogInterface.OnClickListener{

    private EditText mPasswordEditText;
    private View mBackLayout;
    private View mStopUsingServiewlayout;
    private Button mStopUsingServiewButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_user_confirm_stop_service);
        findView();
        initData();
        initEvent();
        return view;
    }

    @Override
    protected void findView() {
        mPasswordEditText = findViewById(R.id.user_confirm_stop_service_password_editText);
        mBackLayout = findViewById(R.id.user_confirm_stop_service_back_frameLayout);
        mStopUsingServiewlayout = findViewById(R.id.user_confirm_stop_service_stop_using_frameLayout);
        mStopUsingServiewButton = findViewById(R.id.user_confirm_stop_service_stop_using_button);

    }

    @Override
    protected void initData() {
        mStopUsingServiewlayout.setEnabled(false);
    }

    @Override
    protected void initEvent() {
        mBackLayout.setOnClickListener(this);
        mStopUsingServiewlayout.setOnClickListener(this);
        mPasswordEditText.addTextChangedListener(userInformationTextWatcher);
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
            case R.id.user_confirm_stop_service_back_frameLayout:
                onBackPressed();
                break;
            case R.id.user_confirm_stop_service_stop_using_frameLayout:
                EniDialogUtil.showAlertDialog(getFragmentManager(), this, "abccssdd", getClass().getName());
                break;
        }
    }



    /**
     * Enable stop using service button when the text is changed
     */
    private TextWatcher userInformationTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String password = mPasswordEditText.getText().toString().trim();
            if (EniUtil.isValidPassword(password)) {
                mStopUsingServiewlayout.setEnabled(true);
                mStopUsingServiewButton.setEnabled(true);
            } else {
                mStopUsingServiewlayout.setEnabled(false);
                mStopUsingServiewButton.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                break;
        }
    }
}
