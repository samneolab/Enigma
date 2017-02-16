package com.neolab.enigma.fragment.history;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.neolab.enigma.BuildConfig;
import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.dto.HeaderDto;
import com.neolab.enigma.dto.ws.History.SalaryRequestDto;
import com.neolab.enigma.fragment.BaseFragment;
import com.neolab.enigma.fragment.history.adapter.HistoryPaymentThisMonthAdapter.OnItemListViewListener;
import com.neolab.enigma.fragment.history.adapter.HistoryPaymentThisMonthAdapter;
import com.neolab.enigma.util.EniLogUtil;
import com.neolab.enigma.util.EniUtil;
import com.neolab.enigma.ws.ApiCode;
import com.neolab.enigma.ws.ApiRequest;
import com.neolab.enigma.ws.core.ApiCallback;
import com.neolab.enigma.ws.core.ApiError;
import com.neolab.enigma.ws.respone.HistoryThisMonthResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Payment this month history
 *
 * @author LongHV
 */
public class HistoryPaymentThisMonthFragment extends BaseFragment implements View.OnClickListener {

    private View mReloadScreenLayout;
    private View mViewMonthlyHistoryLayout;
    private TextView mTotalPaymentOfMonthTextView;
    private ListView mHistoryPaymentListView;
    private List<SalaryRequestDto> mSalaryRequestDtoList;

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
        View footerView = inflater.inflate(R.layout.history_payment_this_month_footer, null, false);
        mTotalPaymentOfMonthTextView = (TextView) headerView.findViewById(R.id.history_payment_this_month_total_payment_textView);
        mReloadScreenLayout = footerView.findViewById(R.id.history_payment_this_month_reload_screen_layout);
        mViewMonthlyHistoryLayout = footerView.findViewById(R.id.history_payment_this_month_view_monthly_history_layout);
        mHistoryPaymentListView = findViewById(R.id.history_payment_this_month_listView);
        mHistoryPaymentListView.addHeaderView(headerView);
        mHistoryPaymentListView.addFooterView(footerView);

    }

    @Override
    protected void initData() {
        doHistoryPaymentApi();
    }

    /**
     * Call api request history payment of month
     */
    private void doHistoryPaymentApi() {
        eniShowNowLoading(getActivity());
        ApiRequest.getHistoryPaymentOfMonth(new ApiCallback<HistoryThisMonthResponse>() {
            @Override
            public void failure(RetrofitError retrofitError, ApiError apiError) {
                eniCancelNowLoading();
            }

            @Override
            public void success(HistoryThisMonthResponse historyThisMonthResponse, Response response) {
                eniCancelNowLoading();
                if (historyThisMonthResponse.statusCode == ApiCode.SUCCESS){
                    if (historyThisMonthResponse.data.salaryRequestDtoList == null){
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
    }

    @Override
    protected HeaderDto getHeaderTypeDto() {
        HeaderDto headerDto = new HeaderDto();
        headerDto.type = EniConstant.ToolbarType.DISPLAY_BACK_TITLE_DRAWER;
        headerDto.title = getResources().getString(R.string.history_payment_this_month_title);
        return headerDto;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.history_payment_this_month_reload_screen_layout:
                doHistoryPaymentApi();
                break;
            case R.id.history_payment_this_month_view_monthly_history_layout:
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
            bundle.putParcelable(DetailHistoryPaymentFragment.KEY_SALARY_REQUEST, salaryRequestDto);
            DetailHistoryPaymentFragment detailHistoryPaymentFragment = new DetailHistoryPaymentFragment();
            detailHistoryPaymentFragment.setArguments(bundle);
            replaceFragment(detailHistoryPaymentFragment, true);
        }
    };
}
