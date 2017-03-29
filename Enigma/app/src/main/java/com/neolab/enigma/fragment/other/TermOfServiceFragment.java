package com.neolab.enigma.fragment.other;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.dto.HeaderDto;
import com.neolab.enigma.fragment.BaseFragment;
import com.neolab.enigma.fragment.top.TopFragment;
import com.neolab.enigma.ws.ApiRequest;
import com.neolab.enigma.ws.core.ApiCallback;
import com.neolab.enigma.ws.core.ApiError;
import com.neolab.enigma.ws.respone.user.TermUsingServiceResponse;

import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.text.Html.fromHtml;

/**
 * Term and condition using service fragment
 *
 * @author Pika Long
 */
public class TermOfServiceFragment extends BaseFragment {

    private WebView mTermAndConditionWebView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_other_term_of_service);
        findView();
        initData();
        initEvent();
        return view;
    }

    @Override
    protected void initData() {
        getTermsAndConditionUsingService();
    }

    @Override
    protected void findView() {
        mTermAndConditionWebView = findViewById(R.id.other_term_service_term_using_webView);
    }

    @Override
    protected void initEvent() {
        (findViewById(R.id.other_term_service_back_layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TopFragment topFragment = new TopFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.setCustomAnimations(R.anim.animation_fade_in_left_to_right, R.anim.animation_fade_out_left_to_right, 0, 0);
                transaction.replace(R.id.main_root_frameLayout, topFragment);
                transaction.commit();
            }
        });
    }

    @Override
    protected HeaderDto getHeaderTypeDto() {
        HeaderDto headerDto = new HeaderDto();
        headerDto.type = EniConstant.ToolbarType.ONLY_DRAWER_MENU;
        return headerDto;
    }

    /**
     * Call api to get term and condition using service
     */
    private void getTermsAndConditionUsingService() {
        eniShowNowLoading(getActivity());
        ApiRequest.getTermAndCondition(new ApiCallback<TermUsingServiceResponse>() {
            @Override
            public void failure(RetrofitError retrofitError, ApiError apiError) {
                eniCancelNowLoading();
            }

            @Override
            public void success(TermUsingServiceResponse termUsingServiceResponse, Response response) {
                eniCancelNowLoading();
                if (termUsingServiceResponse == null) {
                    return;
                }
                String termAndCondition = String.valueOf(
                        fromHtml("<![CDATA[<body>"
                                + termUsingServiceResponse.data.pageContent
                                + "</body>]]>"));
                WebSettings webSettings = mTermAndConditionWebView.getSettings();
                webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
                if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    webSettings.setAllowFileAccessFromFileURLs(true);
                    webSettings.setAllowUniversalAccessFromFileURLs(true);
                }
                mTermAndConditionWebView.loadData(termAndCondition, "text/html; charset=UTF-8", null);
            }
        });
    }

}
