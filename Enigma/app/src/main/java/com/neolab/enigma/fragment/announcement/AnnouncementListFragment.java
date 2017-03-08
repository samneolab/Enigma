package com.neolab.enigma.fragment.announcement;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.dto.HeaderDto;
import com.neolab.enigma.dto.ws.announcement.AnnouncementDto;
import com.neolab.enigma.fragment.BaseFragment;
import com.neolab.enigma.fragment.announcement.adapter.AnnouncementListAdapter;
import com.neolab.enigma.util.EniFormatUtil;
import com.neolab.enigma.ws.ApiCode;
import com.neolab.enigma.ws.ApiParameter;
import com.neolab.enigma.ws.ApiRequest;
import com.neolab.enigma.ws.core.ApiCallback;
import com.neolab.enigma.ws.core.ApiError;
import com.neolab.enigma.ws.respone.announcement.AnnouncementDetailResponse;
import com.neolab.enigma.ws.respone.announcement.AnnouncementResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author Pika
 */
public class AnnouncementListFragment extends BaseFragment {

    private ListView mAnnouncementListView;
    private View mStickyFooterView;
    /** Announcement parameters */
    private Map<String, String> mAnnouncementParamMap = new HashMap<>();
    /** Current page */
    private int mCurrentPage = 1;
    private List<AnnouncementDto> mAnnouncementDtoList = new ArrayList<>();
    private AnnouncementListAdapter mAnnouncementListAdapter;

    /** Check load more status */
    private boolean isLoadMore;
    /** Check exist the data for the next load more */
    private String mNextPageUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_announcement_list);
        findView();
        initData();
        initEvent();
        return view;
    }

    @Override
    protected void findView() {
        mAnnouncementListView = findViewById(R.id.announcement_list_listView);
        mStickyFooterView = findViewById(R.id.announcement_list_sticky_footer);
        final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View headerView = inflater.inflate(R.layout.announcement_list_header, null, false);
        View footerView = inflater.inflate(R.layout.announcement_list_footer, null, false);
        mAnnouncementListView.addHeaderView(headerView);
        mAnnouncementListView.addFooterView(footerView);
    }

    @Override
    protected void initData() {
        mStickyFooterView.setVisibility(View.GONE);
        mAnnouncementListAdapter = new AnnouncementListAdapter(getActivity(), mAnnouncementDtoList);
        mAnnouncementListView.setAdapter(mAnnouncementListAdapter);
        mAnnouncementParamMap.put(ApiParameter.PAGE, String.valueOf(mCurrentPage));
        mAnnouncementParamMap.put(ApiParameter.PER_PAGE, String.valueOf(EniConstant.NUMBER_ANNOUNCEMENT_PER_PAGE));
        mAnnouncementParamMap.put(ApiParameter.PUBLISH_TYPE, ApiParameter.OPEN);
        getAnnouncementList();
    }

    /**
     * Call api to get announcement list
     */
    private void getAnnouncementList() {
        eniShowNowLoading(getActivity());
        ApiRequest.getAnnouncementList(mAnnouncementParamMap, new ApiCallback<AnnouncementResponse>() {
            @Override
            public void failure(RetrofitError retrofitError, ApiError apiError) {
                isLoadMore = false;
                eniCancelNowLoading();
                Toast.makeText(getActivity(), apiError.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void success(AnnouncementResponse announcementResponse, Response response) {
                isLoadMore = false;
                eniCancelNowLoading();
                if (announcementResponse.statusCode != ApiCode.SUCCESS
                        || announcementResponse.data == null
                        || announcementResponse.data.announcementDtoList == null) {
                    return;
                }
                mNextPageUrl = announcementResponse.data.nextPageUrl;
                mAnnouncementDtoList.addAll(announcementResponse.data.announcementDtoList);
                mAnnouncementListAdapter.addAnnouncementList(announcementResponse.data.announcementDtoList);
                mAnnouncementListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initEvent() {
        mAnnouncementListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // Check last item visible
                if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                    mStickyFooterView.setVisibility(View.VISIBLE);
                } else {
                    mStickyFooterView.setVisibility(View.GONE);
                }
                if ((firstVisibleItem + visibleItemCount >= totalItemCount)
                        && !isLoadMore && mNextPageUrl != null) {
                    isLoadMore = true;
                    mCurrentPage++;
                    // Update value mCurrentPage
                    mAnnouncementParamMap.put(ApiParameter.PAGE, String.valueOf(mCurrentPage));
                    getAnnouncementList();
                }
            }
        });

        mAnnouncementListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getAnnouncementDetail(mAnnouncementDtoList.get(position - 1));
            }
        });
    }

    @Override
    protected HeaderDto getHeaderTypeDto() {
        HeaderDto headerDto = new HeaderDto();
        headerDto.type = EniConstant.ToolbarType.DISPLAY_BACK_LOGO_DRAWER;
        return headerDto;
    }

    /**
     * Get detail announcement
     *
     * @param announcementDto AnnouncementDto
     */
    private void getAnnouncementDetail(AnnouncementDto announcementDto) {
        eniShowNowLoading(getActivity());
        ApiRequest.getAnnouncementDetail(announcementDto.id, new ApiCallback<AnnouncementDetailResponse>() {
            @Override
            public void failure(RetrofitError retrofitError, ApiError apiError) {
                eniCancelNowLoading();
                Toast.makeText(getActivity(), apiError.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void success(AnnouncementDetailResponse announcementDetailResponse, Response response) {
                eniCancelNowLoading();
                if (announcementDetailResponse.statusCode == ApiCode.SUCCESS) {
                    if (announcementDetailResponse.data != null) {
                        showAnnouncementDetailDialog(announcementDetailResponse.data);
                    }
                }
            }
        });
    }

    /**
     * Show announcement detail dialog
     */
    public void showAnnouncementDetailDialog(AnnouncementDto announcementDto) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.top_announcement_detail_dialog, null);
        builder.setView(view);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        dialog.show();

        TextView titleTextView = (TextView) view.findViewById(R.id.top_announcement_detail_title_textView);
        TextView startTimeTextView = (TextView) view.findViewById(R.id.top_announcement_detail_start_time_textView);
        TextView contentTextView = (TextView) view.findViewById(R.id.top_announcement_detail_content_textView);
        FrameLayout closeTextView = (FrameLayout) view.findViewById(R.id.top_announcement_detail_close_layout);
        if (announcementDto != null) {
            titleTextView.setText(announcementDto.title);
            startTimeTextView.setText(EniFormatUtil.getDateAnnouncementWithFormat(announcementDto.startTime));
            contentTextView.setText(announcementDto.content);
        }
        closeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}
