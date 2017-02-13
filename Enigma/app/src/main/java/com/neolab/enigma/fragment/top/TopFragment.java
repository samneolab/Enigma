package com.neolab.enigma.fragment.top;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neolab.enigma.BuildConfig;
import com.neolab.enigma.R;
import com.neolab.enigma.dto.MoneyPrepaymentDto;
import com.neolab.enigma.dto.ws.announcement.AnnouncementDto;
import com.neolab.enigma.fragment.BaseFragment;
import com.neolab.enigma.util.EniLogUtil;
import com.neolab.enigma.ws.ApiCode;
import com.neolab.enigma.ws.ApiRequest;
import com.neolab.enigma.ws.core.ApiCallback;
import com.neolab.enigma.ws.core.ApiError;
import com.neolab.enigma.ws.respone.MoneyPrepaymentResponse;
import com.neolab.enigma.ws.respone.announcement.AnnouncementResponse;
import com.neolab.enigma.ws.respone.announcement.EmergencyAnnouncementResponse;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * This class display notification and money allow prepayment
 *
 * @author LongHV
 */
public class TopFragment extends BaseFragment {

    private View mUrgentNotificationFrameLayout;
    private TextView mUrgentNotificationTextView;
    private LinearLayout mNotificationItem1LinearLayout;
    private LinearLayout mNotificationItem2LinearLayout;
    private LinearLayout mNotificationItem3LinearLayout;
    private TextView mNotificationItem1TexView;
    private TextView mNotificationItem2TexView;
    private TextView mNotificationItem3TexView;

    private TextView mAmountPrepaymentAvaiableTextView;
    private TextView mMoneyPendingTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_top);
        findView();
        initData();
        initEvent();
        return view;
    }

    @Override
    protected void findView() {
        mUrgentNotificationFrameLayout = findViewById(R.id.top_urgent_notification_frameLayout);
        mUrgentNotificationTextView = findViewById(R.id.top_urgent_notification_textView);

        mNotificationItem1LinearLayout = findViewById(R.id.top_notification_item1_linearLayout);
        mNotificationItem2LinearLayout = findViewById(R.id.top_notification_item2_linearLayout);
        mNotificationItem3LinearLayout = findViewById(R.id.top_notification_item3_linearLayout);
        mNotificationItem1TexView = findViewById(R.id.top_notification_item1_textView);
        mNotificationItem2TexView = findViewById(R.id.top_notification_item2_textView);
        mNotificationItem3TexView = findViewById(R.id.top_notification_item3_textView);

        mAmountPrepaymentAvaiableTextView = findViewById(R.id.top_amount_prepayment_available_textView);
        mMoneyPendingTextView = findViewById(R.id.top_money_pending_textView);
    }

    @Override
    protected void initData() {
        getEmergenceAnnouncement();
        getAnnouncementList();
        getMoneyAvailableForPrepayment();
    }

    /**
     * Get the money allow prepayment and the money is pending payment
     */
    private void getMoneyAvailableForPrepayment() {
        ApiRequest.getMoneyAvailableForPrepayment(new ApiCallback<MoneyPrepaymentResponse>() {
            @Override
            public void failure(RetrofitError retrofitError, ApiError apiError) {

            }

            @Override
            public void success(MoneyPrepaymentResponse moneyPrepaymentResponse, Response response) {
                if (moneyPrepaymentResponse.statusCode == ApiCode.SUCCESS){
                    MoneyPrepaymentDto moneyPrepaymentDto = moneyPrepaymentResponse.data;
                    if (moneyPrepaymentDto != null) {
                        mAmountPrepaymentAvaiableTextView.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(moneyPrepaymentDto.remainPayment)));
                        mMoneyPendingTextView.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(moneyPrepaymentDto.amountOfSalary)));
                    }
                }
            }
        });
    }

    /**
     * Get all announcements
     */
    private void getAnnouncementList() {
        ApiRequest.getAnnouncementList(new ApiCallback<AnnouncementResponse>() {
            @Override
            public void failure(RetrofitError retrofitError, ApiError apiError) {
                if (BuildConfig.DEBUG) {
                    EniLogUtil.d(getClass(), "[failure]getAnnouncementList " + apiError.getError());
                }
            }

            @Override
            public void success(AnnouncementResponse announcementResponse, Response response) {
                if (BuildConfig.DEBUG) {
                    EniLogUtil.d(getClass(), "[success]getAnnouncementList" + announcementResponse.statusCode);
                }
                if (announcementResponse.statusCode == ApiCode.SUCCESS) {
                    if (announcementResponse.data.announcementDtoList != null) {
                        displayAnnouncement(announcementResponse.data.announcementDtoList);
                    }
                }
            }
        });
    }

    /**
     * Process to display announcement list
     *
     * @param announcementDtoList List<AnnouncementDto>
     */
    private void displayAnnouncement(List<AnnouncementDto> announcementDtoList) {
        switch (announcementDtoList.size()) {
            case 3:
                mNotificationItem3LinearLayout.setVisibility(View.VISIBLE);
                mNotificationItem3TexView.setText(announcementDtoList.get(2).title);
            case 2:
                mNotificationItem2LinearLayout.setVisibility(View.VISIBLE);
                mNotificationItem2TexView.setText(announcementDtoList.get(1).title);
            case 1:
                mNotificationItem1LinearLayout.setVisibility(View.VISIBLE);
                mNotificationItem1TexView.setText(announcementDtoList.get(0).title);
                break;
            default:
                break;
        }
    }

    @Override
    protected void initEvent() {

    }

    /**
     * Call request to get emergency announcement
     */
    private void getEmergenceAnnouncement() {
        ApiRequest.getEmergencyAnnouncement(new ApiCallback<EmergencyAnnouncementResponse>() {
            @Override
            public void failure(RetrofitError retrofitError, ApiError apiError) {
                mUrgentNotificationFrameLayout.setVisibility(View.GONE);
                if (BuildConfig.DEBUG) {
                    EniLogUtil.d(getClass(), "[getEmergencyAnnouncement] " + apiError.getError());
                }
            }

            @Override
            public void success(EmergencyAnnouncementResponse emergencyAnnouncementResponse, Response response) {
                if (emergencyAnnouncementResponse.statusCode == ApiCode.SUCCESS) {
                    AnnouncementDto announcementDto = emergencyAnnouncementResponse.mAnnouncementDto;
                    if (announcementDto != null) {
                        mUrgentNotificationFrameLayout.setVisibility(View.VISIBLE);
                        mUrgentNotificationTextView.setText(announcementDto.title);
                    } else {
                        mUrgentNotificationFrameLayout.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

}
