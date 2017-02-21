package com.neolab.enigma.fragment.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.dto.HeaderDto;
import com.neolab.enigma.fragment.BaseFragment;
import com.neolab.enigma.util.EniUtil;
import com.neolab.enigma.ws.ApiCode;
import com.neolab.enigma.ws.ApiRequest;
import com.neolab.enigma.ws.core.ApiCallback;
import com.neolab.enigma.ws.core.ApiError;
import com.neolab.enigma.ws.respone.user.UserInformationResponse;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Update user information fragment
 *
 * @author Pika
 */
public class UpdateUserInformationFragment extends BaseFragment implements View.OnClickListener{

    private TextView mNameTextView;
    private EditText mEmailAddressEditText;

    private View mBackTopLayout;
    private View mUpdateInformationLayout;
    private View mStopServiceLayout;
    private Button mUpdateInformationButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_update_user_information);
        findView();
        initData();
        initEvent();
        return view;
    }

    @Override
    protected void findView() {
        mNameTextView = findViewById(R.id.update_user_information_name_textView);
        mEmailAddressEditText = findViewById(R.id.update_user_information_email_editText);
        mBackTopLayout = findViewById(R.id.update_user_information_back_layout);
        mUpdateInformationLayout = findViewById(R.id.update_user_information_update_information_layout);
        mUpdateInformationButton = findViewById(R.id.update_user_information_update_information_button);
        mStopServiceLayout = findViewById(R.id.update_user_information_stop_service_layout);
    }

    @Override
    protected void initData() {
        mUpdateInformationLayout.setEnabled(false);
        mUpdateInformationButton.setEnabled(false);
        doUserInformationApi();
    }

    /**
     * Call api to get user information
     */
    private void doUserInformationApi() {
        eniShowNowLoading(getActivity());
        ApiRequest.getUserInformation(new ApiCallback<UserInformationResponse>() {
            @Override
            public void failure(RetrofitError retrofitError, ApiError apiError) {
                eniCancelNowLoading();
            }

            @Override
            public void success(UserInformationResponse userInformationResponse, Response response) {
                eniCancelNowLoading();
                if (userInformationResponse.statusCode != ApiCode.SUCCESS
                        || userInformationResponse.data == null) {
                    return;
                }
                mUpdateInformationLayout.setEnabled(true);
                mUpdateInformationButton.setEnabled(true);
                mNameTextView.setText(userInformationResponse.data.name);
                mEmailAddressEditText.setText(userInformationResponse.data.email);
            }
        });
    }

    @Override
    protected void initEvent() {
        mBackTopLayout.setOnClickListener(this);
        mUpdateInformationLayout.setOnClickListener(this);
        mStopServiceLayout.setOnClickListener(this);

        mEmailAddressEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (EniUtil.isValidateEmail(mEmailAddressEditText.getText().toString())){
                    mUpdateInformationLayout.setEnabled(true);
                    mUpdateInformationButton.setEnabled(true);
                } else {
                    mUpdateInformationLayout.setEnabled(false);
                    mUpdateInformationButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected HeaderDto getHeaderTypeDto() {
        HeaderDto headerDto = new HeaderDto();
        headerDto.title = getString(R.string.update_user_information_title);
        headerDto.type = EniConstant.ToolbarType.ONLY_TITLE;
        return headerDto;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_user_information_back_layout:
                getActivity().onBackPressed();
                break;
            case R.id.update_user_information_update_information_layout:
                break;
            case R.id.update_user_information_stop_service_layout:
                break;
            default:
                break;
        }
    }
}
