package com.neolab.enigma.fragment.history;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.dto.HeaderDto;
import com.neolab.enigma.dto.ws.history.MonthPaymentDto;
import com.neolab.enigma.dto.ws.history.SalaryRequestDto;
import com.neolab.enigma.fragment.BaseFragment;
import com.neolab.enigma.fragment.history.adapter.MonthPaymentListAdapter;
import com.neolab.enigma.ui.EniListView;
import com.neolab.enigma.ws.ApiCode;
import com.neolab.enigma.ws.ApiRequest;
import com.neolab.enigma.ws.core.ApiCallback;
import com.neolab.enigma.ws.core.ApiError;
import com.neolab.enigma.ws.respone.history.MonthPaymentResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author LongHV
 */
public class HistoryPaymentEveryMonthFragment extends BaseFragment implements View.OnClickListener {

    private EniListView mMonthPaymentListView;
    private View mStickyFooterView;
    private View mFooterView;
    private View mHeaderView;
    private View mViewHistoryRecentPaymentLayout;

    /** Check last item visible */
    private boolean isLastItemVisible = false;

    private List<MonthPaymentDto> mMonthPaymentDtoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_history_payment_every_month);
        findView();
        initData();
        initEvent();
        return view;
    }

    @Override
    protected void initData() {
        mMonthPaymentDtoList = new ArrayList<>();
        eniShowNowLoading(getActivity());
        ApiRequest.getMonthRequestPaymentList(new ApiCallback<MonthPaymentResponse>() {
            @Override
            public void failure(RetrofitError retrofitError, ApiError apiError) {
                eniCancelNowLoading();
            }

            @Override
            public void success(MonthPaymentResponse monthPaymentResponse, Response response) {
                eniCancelNowLoading();
                if (monthPaymentResponse.statusCode != ApiCode.SUCCESS) {
                    return;
                }
                if (monthPaymentResponse.data == null) {
                    return;
                }
                mMonthPaymentDtoList = monthPaymentResponse.data;
                MonthPaymentListAdapter adapter = new MonthPaymentListAdapter(getActivity(), mMonthPaymentDtoList);
                mMonthPaymentListView.setAdapter(adapter);
            }
        });
    }

    @Override
    protected void findView() {
        mMonthPaymentListView = findViewById(R.id.history_payment_every_month_listView);
        mViewHistoryRecentPaymentLayout = findViewById(R.id.history_payment_this_month_view_history_recent_payment_layout);
        mStickyFooterView = findViewById(R.id.history_payment_every_month_sticky_footer_view);
        final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mHeaderView = inflater.inflate(R.layout.history_payment_every_month_header, null, false);
        mFooterView = inflater.inflate(R.layout.history_payment_every_month_footer, null, false);
        mMonthPaymentListView.addHeaderView(mHeaderView);
        mMonthPaymentListView.addFooterView(mFooterView);
    }

    @Override
    protected void initEvent() {
        mViewHistoryRecentPaymentLayout.setOnClickListener(this);

        mMonthPaymentListView.setOnDetectScrollListener(new EniListView.OnDetectScrollListener() {
            @Override
            public void onUpScrolling() {
                if (mStickyFooterView.getVisibility() == View.GONE) {
                    mStickyFooterView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onDownScrolling() {
                if (!isLastItemVisible) {
                    if (mStickyFooterView.getVisibility() == View.VISIBLE) {
                        mStickyFooterView.setVisibility(View.GONE);
                    }
                }
            }
        });

        mMonthPaymentListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                setStickyFooterVisibility(mStickyFooterView, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        });

        mMonthPaymentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HistoryPaymentThisMonthFragment fragment = new HistoryPaymentThisMonthFragment();
                MonthPaymentDto monthPaymentDto = mMonthPaymentDtoList.get(position -1);
                Bundle bundle = new Bundle();
                bundle.putParcelable(HistoryPaymentThisMonthFragment.KEY_SALARY_REQUEST_DTO, monthPaymentDto);
                fragment.setArguments(bundle);
                replaceFragment(fragment, true);
            }
        });
    }

    /**
     * Process hide/display sticky footer
     *
     * @param footerView sticky footer
     * @param firstVisibleItem first visible item
     * @param visibleItemCount visible Item Count
     * @param totalItemCount total Item Count
     */
    private void setStickyFooterVisibility(final View footerView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem + visibleItemCount >= totalItemCount) {
            isLastItemVisible = true;
            if (footerView.getVisibility() != View.VISIBLE) {
                footerView.setVisibility(View.VISIBLE);
            }
        } else {
            isLastItemVisible = false;
        }
    }

    @Override
    protected HeaderDto getHeaderTypeDto() {
        HeaderDto headerDto = new HeaderDto();
        headerDto.type = EniConstant.ToolbarType.DISPLAY_BACK_TITLE_DRAWER;
        headerDto.title = getResources().getString(R.string.history_payment_every_month_title);
        return headerDto;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.history_payment_this_month_view_history_recent_payment_layout:
                HistoryPaymentThisMonthFragment fragment = new HistoryPaymentThisMonthFragment();
                replaceFragment(fragment, true);
                break;
            default:
                break;
        }
    }
}
