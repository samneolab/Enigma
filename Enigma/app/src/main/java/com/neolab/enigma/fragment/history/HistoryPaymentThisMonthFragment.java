package com.neolab.enigma.fragment.history;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import com.neolab.enigma.BuildConfig;
import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.dto.HeaderDto;
import com.neolab.enigma.dto.ws.history.MonthPaymentDto;
import com.neolab.enigma.dto.ws.history.SalaryRequestDto;
import com.neolab.enigma.fragment.BaseFragment;
import com.neolab.enigma.fragment.history.adapter.HistoryPaymentThisMonthAdapter.OnItemListViewListener;
import com.neolab.enigma.fragment.history.adapter.HistoryPaymentThisMonthAdapter;
import com.neolab.enigma.ui.EniListView;
import com.neolab.enigma.util.EniLogUtil;
import com.neolab.enigma.util.EniUtil;
import com.neolab.enigma.ws.ApiCode;
import com.neolab.enigma.ws.ApiParameter;
import com.neolab.enigma.ws.ApiRequest;
import com.neolab.enigma.ws.core.ApiCallback;
import com.neolab.enigma.ws.core.ApiError;
import com.neolab.enigma.ws.respone.history.HistoryThisMonthResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * History payment of this month
 *
 * @author LongHV
 */
public class HistoryPaymentThisMonthFragment extends BaseFragment implements View.OnClickListener {

    public static final String KEY_SALARY_REQUEST_DTO = "SalaryRequestDto";

    private View mReloadScreenLayout;
    private View mViewMonthlyHistoryLayout;
    private TextView mTotalPaymentOfMonthTextView;
    private EniListView mHistoryPaymentListView;
    private List<SalaryRequestDto> mSalaryRequestDtoList;

    private View mStickyFooterView;
    private View mFooterView;

    /** Date request payment */
    private Map<String, Integer> mDateRequestParamMap;
    private int mYearRequestPayment;
    private int mMonthRequestPayment;

    /**
     * Check last item visible
     */
    private boolean isLastItemVisible = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_history_payment_this_month);
        findView();
        initData();
        initEvent();
        return view;
    }

    @Override
    protected void findView() {
        final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerView = inflater.inflate(R.layout.history_payment_this_month_header, null, false);
        mFooterView = inflater.inflate(R.layout.history_payment_this_month_footer, null, false);
        mTotalPaymentOfMonthTextView = (TextView) headerView.findViewById(R.id.history_payment_this_month_total_payment_textView);
        mReloadScreenLayout = findViewById(R.id.history_payment_this_month_reload_screen_layout);
        mViewMonthlyHistoryLayout = findViewById(R.id.history_payment_this_month_view_monthly_history_layout);
        mHistoryPaymentListView = findViewById(R.id.history_payment_this_month_listView);
        mStickyFooterView = findViewById(R.id.history_payment_this_month_sticky_footer_view);
        mHistoryPaymentListView.addHeaderView(headerView);
        mHistoryPaymentListView.addFooterView(mFooterView);
    }

    @Override
    protected void initData() {
        mDateRequestParamMap = new HashMap<>();
        Bundle bundle = getArguments();
        // Check get history payment of recently month or any month from list month
        if (bundle != null) {
            MonthPaymentDto monthPaymentDto = bundle.getParcelable(KEY_SALARY_REQUEST_DTO);
            if (monthPaymentDto != null) {
                mYearRequestPayment = monthPaymentDto.year;
                mMonthRequestPayment = monthPaymentDto.month;
                // Add date to get payment
                mDateRequestParamMap.put(ApiParameter.YEAR_REQUEST_PAYMENT, mYearRequestPayment);
                mDateRequestParamMap.put(ApiParameter.MONTH_REQUEST_PAYMENT, mMonthRequestPayment);
            }
            mViewMonthlyHistoryLayout.setVisibility(View.GONE);
            mReloadScreenLayout.setVisibility(View.GONE);
            mFooterView.findViewById(R.id.history_payment_this_month_view_monthly_history_layout).setVisibility(View.GONE);
            mFooterView.findViewById(R.id.history_payment_this_month_reload_screen_layout).setVisibility(View.GONE);
        } else {
            // The case get history payment of recently month
            mViewMonthlyHistoryLayout.setVisibility(View.VISIBLE);
            mReloadScreenLayout.setVisibility(View.VISIBLE);
        }
        doHistoryPaymentApi();
    }

    /**
     * Call api request history payment of month
     */
    private void doHistoryPaymentApi() {
        eniShowNowLoading(getActivity());
        ApiRequest.getHistoryRecentPaymentOfMonth(mDateRequestParamMap, new ApiCallback<HistoryThisMonthResponse>() {
            @Override
            public void failure(RetrofitError retrofitError, ApiError apiError) {
                eniCancelNowLoading();
            }

            @Override
            public void success(HistoryThisMonthResponse historyThisMonthResponse, Response response) {
                eniCancelNowLoading();
                if (historyThisMonthResponse.statusCode == ApiCode.SUCCESS) {
                    if (historyThisMonthResponse.data.salaryRequestDtoList == null) {
                        return;
                    }
                    mSalaryRequestDtoList = new ArrayList<>();
                    mSalaryRequestDtoList = historyThisMonthResponse.data.salaryRequestDtoList;
                    HistoryPaymentThisMonthAdapter adapter = new HistoryPaymentThisMonthAdapter(getActivity(), mSalaryRequestDtoList, onItemListViewListener);
                    mHistoryPaymentListView.setAdapter(adapter);
                    mTotalPaymentOfMonthTextView.setText(EniUtil.convertMoneyFormat(historyThisMonthResponse.data.totalPayment));
                }
            }
        });
    }

    @Override
    protected void initEvent() {
        mReloadScreenLayout.setOnClickListener(this);
        mViewMonthlyHistoryLayout.setOnClickListener(this);
        mHistoryPaymentListView.setOnDetectScrollListener(new EniListView.OnDetectScrollListener() {
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

        mHistoryPaymentListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                setStickyFooterVisibility(mStickyFooterView, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        });
    }

    /**
     * Process hide/display sticky footer
     *
     * @param footerView       sticky footer
     * @param firstVisibleItem first visible item
     * @param visibleItemCount visible Item Count
     * @param totalItemCount   total Item Count
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
        if (mDateRequestParamMap.size() == 0){
            headerDto.title = getResources().getString(R.string.history_payment_this_month_title);
        } else {
            headerDto.title = mYearRequestPayment + getActivity().getString(R.string.history_year)
                    + mMonthRequestPayment + getActivity().getString(R.string.history_month);
        }
        headerDto.type = EniConstant.ToolbarType.DISPLAY_BACK_TITLE_DRAWER;
        return headerDto;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.history_payment_this_month_reload_screen_layout:
                doHistoryPaymentApi();
                break;
            case R.id.history_payment_this_month_view_monthly_history_layout:
                HistoryPaymentEveryMonthFragment historyPaymentEveryMonthFragment = new HistoryPaymentEveryMonthFragment();
                replaceFragment(historyPaymentEveryMonthFragment, true);
                break;
            default:
                break;
        }
    }

    private OnItemListViewListener onItemListViewListener = new OnItemListViewListener() {
        @Override
        public void onItemListener(SalaryRequestDto salaryRequestDto) {
            if (BuildConfig.DEBUG) {
                EniLogUtil.d(getClass(), "[onItemListener] id:" + salaryRequestDto.id);
            }
            Bundle bundle = new Bundle();
            bundle.putInt(DetailHistoryPaymentFragment.KEY_REQUEST_PAYMENT_ID, salaryRequestDto.id);
            DetailHistoryPaymentFragment detailHistoryPaymentFragment = new DetailHistoryPaymentFragment();
            detailHistoryPaymentFragment.setArguments(bundle);
            replaceFragment(detailHistoryPaymentFragment, true);
        }
    };
}
