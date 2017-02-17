package com.neolab.enigma.fragment.history;


import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.dto.HeaderDto;
import com.neolab.enigma.dto.ws.history.MonthPaymentDto;
import com.neolab.enigma.fragment.BaseFragment;
import com.neolab.enigma.fragment.history.adapter.MonthPaymentListAdapter;
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
public class HistoryPaymentEveryMonthFragment extends BaseFragment {

    private ListView mMonthPaymentListView;
    private View mStickyFooterView;
    private View mFooterView;
    private View mHeaderView;

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
                List<MonthPaymentDto> monthPaymentDtoList = new ArrayList<MonthPaymentDto>();
                for (int i = 1; i <= 20; i++) {
                    MonthPaymentDto monthPaymentDto = new MonthPaymentDto();
                    monthPaymentDto.year = 2010 + i;
                    monthPaymentDto.month = i;
                    monthPaymentDtoList.add(monthPaymentDto);
                }
                MonthPaymentListAdapter adapter = new MonthPaymentListAdapter(getActivity(), monthPaymentDtoList);
                mMonthPaymentListView.setAdapter(adapter);
            }
        });
    }

    @Override
    protected void findView() {
        mMonthPaymentListView = findViewById(R.id.history_payment_every_month_listView);
        mStickyFooterView = findViewById(R.id.history_payment_every_month_sticky_footer_view);
        final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mHeaderView = inflater.inflate(R.layout.history_payment_every_month_header, null, false);
        mFooterView = inflater.inflate(R.layout.history_payment_every_month_footer, null, false);
        mMonthPaymentListView.addHeaderView(mHeaderView);
        mMonthPaymentListView.addFooterView(mFooterView);
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected HeaderDto getHeaderTypeDto() {
        HeaderDto headerDto = new HeaderDto();
        headerDto.type = EniConstant.ToolbarType.DISPLAY_BACK_TITLE_DRAWER;
        headerDto.title = getResources().getString(R.string.history_payment_every_month_title);
        return headerDto;
    }

    private int getHeightOfScreen(){
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    private int getHeightOfListView(ListView listView) {
        ListAdapter mAdapter = listView.getAdapter();
        int totalHeight = 0;

        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, listView);
            mView.measure(
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),

                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

            totalHeight += mView.getMeasuredHeight();
            Log.w("HEIGHT" + i, String.valueOf(totalHeight));
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
        return totalHeight;
    }
}
