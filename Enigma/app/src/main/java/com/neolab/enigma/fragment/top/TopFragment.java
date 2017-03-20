package com.neolab.enigma.fragment.top;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.neolab.enigma.BuildConfig;
import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.dto.HeaderDto;
import com.neolab.enigma.dto.ws.announcement.AnnouncementDto;
import com.neolab.enigma.dto.ws.payment.MoneyPrepaymentDto;
import com.neolab.enigma.fragment.BaseFragment;
import com.neolab.enigma.fragment.announcement.AnnouncementListFragment;
import com.neolab.enigma.fragment.history.HistoryPaymentEveryMonthFragment;
import com.neolab.enigma.fragment.history.HistoryPaymentThisMonthFragment;
import com.neolab.enigma.fragment.payment.PaymentFragment;
import com.neolab.enigma.util.EniFormatUtil;
import com.neolab.enigma.util.EniLogUtil;
import com.neolab.enigma.ws.ApiCode;
import com.neolab.enigma.ws.ApiParameter;
import com.neolab.enigma.ws.ApiRequest;
import com.neolab.enigma.ws.core.ApiCallback;
import com.neolab.enigma.ws.core.ApiError;
import com.neolab.enigma.ws.respone.ErrorResponse;
import com.neolab.enigma.ws.respone.announcement.AnnouncementDetailResponse;
import com.neolab.enigma.ws.respone.announcement.AnnouncementResponse;
import com.neolab.enigma.ws.respone.announcement.EmergencyAnnouncementResponse;
import com.neolab.enigma.ws.respone.payment.MoneyPrepaymentResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * This class display notification and money allow prepayment
 *
 * @author LongHV
 */
public class TopFragment extends BaseFragment implements View.OnClickListener {

    private static final int PAGE = 1;
    private static final int PER_PAGE = 3;

    private View mUrgentNotificationFrameLayout;
    private TextView mUrgentNotificationTextView;

    private FrameLayout mNotificationItem1Layout;
    private FrameLayout mNotificationItem2Layout;
    private FrameLayout mNotificationItem3Layout;
    private TextView mNotificationItem1TexView;
    private TextView mNotificationItem2TexView;
    private TextView mNotificationItem3TexView;

    private TextView mAmountPrepaymentAvailableTextView;
    private TextView mMoneyPendingTextView;
    private TextView mNotificationTextView;

    private FrameLayout mApplyPrepaymentLayout;
    private View mApplyPrepaymentButton;
    private FrameLayout mViewHistoryThisMonthLayout;
    private FrameLayout mViewHistoryEveryMonthLayout;
    private FrameLayout mReloadScreenLayout;
    private View mMessageNoNotificationTextView;

    /**
     * Emergency announcement
     */
    private AnnouncementDto mEmergencyAnnouncementDto;
    /**
     * Announcement list
     */
    private List<AnnouncementDto> mAnnouncementDtoList;
    /** Announcement parameters */
    private Map<String, String> mAnnouncementParamMap;

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

        mNotificationItem1Layout = findViewById(R.id.top_notification_item1_layout);
        mNotificationItem2Layout = findViewById(R.id.top_notification_item2_layout);
        mNotificationItem3Layout = findViewById(R.id.top_notification_item3_layout);
        mNotificationItem1TexView = findViewById(R.id.top_notification_item1_textView);
        mNotificationItem2TexView = findViewById(R.id.top_notification_item2_textView);
        mNotificationItem3TexView = findViewById(R.id.top_notification_item3_textView);
        mNotificationTextView = findViewById(R.id.top_last_notification_list_textView);
        mApplyPrepaymentLayout = findViewById(R.id.top_apply_for_prepayment_layout);
        mApplyPrepaymentButton = findViewById(R.id.top_apply_for_prepayment_textView);
        mViewHistoryThisMonthLayout = findViewById(R.id.top_view_history_this_month_layout);
        mViewHistoryEveryMonthLayout = findViewById(R.id.top_view_history_every_month_layout);
        mReloadScreenLayout = findViewById(R.id.top_reload_screen_layout);
        mMessageNoNotificationTextView = findViewById(R.id.top_message_no_notification_textView);

        mAmountPrepaymentAvailableTextView = findViewById(R.id.top_amount_prepayment_available_textView);
        mMoneyPendingTextView = findViewById(R.id.top_money_pending_textView);
    }

    @Override
    protected void initData() {
        mAnnouncementParamMap = new HashMap<>();
        mAnnouncementParamMap.put(ApiParameter.PAGE, String.valueOf(PAGE));
        mAnnouncementParamMap.put(ApiParameter.PER_PAGE, String.valueOf(PER_PAGE));
        mAnnouncementParamMap.put(ApiParameter.PUBLISH_TYPE, ApiParameter.OPEN);
        mApplyPrepaymentButton.setEnabled(false);
        mApplyPrepaymentLayout.setEnabled(false);
        mUrgentNotificationFrameLayout.setVisibility(View.GONE);
        mNotificationItem1Layout.setVisibility(View.INVISIBLE);
        mNotificationItem2Layout.setVisibility(View.INVISIBLE);
        mNotificationItem3Layout.setVisibility(View.INVISIBLE);
        mAnnouncementDtoList = new ArrayList<>();
        getEmergenceAnnouncement();
        getAnnouncementList();
        getMoneyAvailableForPrepayment();
    }

    @Override
    protected void initEvent() {
        mUrgentNotificationFrameLayout.setOnClickListener(this);
        mNotificationItem1Layout.setOnClickListener(this);
        mNotificationItem2Layout.setOnClickListener(this);
        mNotificationItem3Layout.setOnClickListener(this);
        mNotificationTextView.setOnClickListener(this);
        mApplyPrepaymentLayout.setOnClickListener(this);
        mViewHistoryThisMonthLayout.setOnClickListener(this);
        mViewHistoryEveryMonthLayout.setOnClickListener(this);
        mReloadScreenLayout.setOnClickListener(this);
    }

    @Override
    protected HeaderDto getHeaderTypeDto() {
        HeaderDto headerDto = new HeaderDto();
        headerDto.type = EniConstant.ToolbarType.HOME;
        return headerDto;
    }

    /**
     * Process to display announcement list
     *
     * @param announcementDtoList List<AnnouncementDto>
     */
    private void displayAnnouncement(List<AnnouncementDto> announcementDtoList) {
        switch (announcementDtoList.size()) {
            case 3:
                mNotificationItem3Layout.setVisibility(View.VISIBLE);
                mNotificationItem3TexView.setText(announcementDtoList.get(2).title);
            case 2:
                mNotificationItem2Layout.setVisibility(View.VISIBLE);
                mNotificationItem2TexView.setText(announcementDtoList.get(1).title);
            case 1:
                mNotificationItem1Layout.setVisibility(View.VISIBLE);
                mNotificationItem1TexView.setText(announcementDtoList.get(0).title);
                break;
            default:
                mMessageNoNotificationTextView.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_urgent_notification_frameLayout:
                showAnnouncementDetailDialog(mEmergencyAnnouncementDto);
                break;
            case R.id.top_last_notification_list_textView:
                AnnouncementListFragment announcementListFragment = new AnnouncementListFragment();
                replaceFragment(announcementListFragment, true);
                break;
            case R.id.top_notification_item1_layout:
                getAnnouncementDetail(mAnnouncementDtoList.get(0));
                break;
            case R.id.top_notification_item2_layout:
                getAnnouncementDetail(mAnnouncementDtoList.get(1));
                break;
            case R.id.top_notification_item3_layout:
                getAnnouncementDetail(mAnnouncementDtoList.get(2));
                break;
            case R.id.top_apply_for_prepayment_layout:
                PaymentFragment paymentFragment = new PaymentFragment();
                replaceFragment(paymentFragment, true);
                break;
            case R.id.top_view_history_this_month_layout:
                HistoryPaymentThisMonthFragment fragment = new HistoryPaymentThisMonthFragment();
                replaceFragment(fragment, true);
                break;
            case R.id.top_view_history_every_month_layout:
                HistoryPaymentEveryMonthFragment historyPaymentEveryMonthFragment = new HistoryPaymentEveryMonthFragment();
                replaceFragment(historyPaymentEveryMonthFragment, true);
                break;
            case R.id.top_reload_screen_layout:
                initData();
                break;
            default:
                break;
        }
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
                ErrorResponse body = (ErrorResponse) retrofitError.getBodyAs(ErrorResponse.class);
                if (body == null) {
                    return;
                }
                // User stopped service
                if (body.code == ApiCode.USER_STOPPED_SERVICE) {
                    goStopServiceScreen();
                } else {
                    Toast.makeText(getActivity(), apiError.getError().getMessage(), Toast.LENGTH_SHORT).show();
                }
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
     * Call request to get emergency announcement
     */
    private void getEmergenceAnnouncement() {
        eniShowNowLoading(getActivity());
        ApiRequest.getEmergencyAnnouncement(new ApiCallback<EmergencyAnnouncementResponse>() {
            @Override
            public void failure(RetrofitError retrofitError, ApiError apiError) {
                eniCancelNowLoading();
                mUrgentNotificationFrameLayout.setVisibility(View.GONE);
                if (getActivity() != null) {
                    if (getActivity().isFinishing()) {
                        return;
                    }
                }
                ErrorResponse body = (ErrorResponse) retrofitError.getBodyAs(ErrorResponse.class);
                if (body == null) {
                    return;
                }
                // User stopped service
                if (body.code == ApiCode.USER_STOPPED_SERVICE) {
                    goStopServiceScreen();
                } else {
                    Toast.makeText(getActivity(), apiError.getError().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void success(EmergencyAnnouncementResponse emergencyAnnouncementResponse, Response response) {
                eniCancelNowLoading();
                if (emergencyAnnouncementResponse.statusCode == ApiCode.SUCCESS) {
                    mEmergencyAnnouncementDto = emergencyAnnouncementResponse.mAnnouncementDto;
                    if (mEmergencyAnnouncementDto != null) {
                        mUrgentNotificationFrameLayout.setVisibility(View.VISIBLE);
                        mUrgentNotificationTextView.setText(mEmergencyAnnouncementDto.title);
                    } else {
                        mUrgentNotificationFrameLayout.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    /**
     * Get the money allow prepayment and the money is pending payment
     */
    private void getMoneyAvailableForPrepayment() {
        eniShowNowLoading(getActivity());
        ApiRequest.getMoneyAvailableForPrepayment(new ApiCallback<MoneyPrepaymentResponse>() {
            @Override
            public void failure(RetrofitError retrofitError, ApiError apiError) {
                eniCancelNowLoading();
                if (getActivity() != null) {
                    if (getActivity().isFinishing()) {
                        return;
                    }
                }
                ErrorResponse body = (ErrorResponse) retrofitError.getBodyAs(ErrorResponse.class);
                if (body == null) {
                    return;
                }
                // User stopped service
                if (body.code == ApiCode.USER_STOPPED_SERVICE) {
                    goStopServiceScreen();
                } else {
                    Toast.makeText(getActivity(), apiError.getError().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void success(MoneyPrepaymentResponse moneyPrepaymentResponse, Response response) {
                eniCancelNowLoading();
                if (moneyPrepaymentResponse.statusCode == ApiCode.SUCCESS) {
                    MoneyPrepaymentDto moneyPrepaymentDto = moneyPrepaymentResponse.data;
                    if (moneyPrepaymentDto != null) {
                        mAmountPrepaymentAvailableTextView.setText(
                                EniFormatUtil.convertMoneyFormat(moneyPrepaymentDto.remainPayment));
                        if (moneyPrepaymentDto.remainPayment > 0) {
                            mApplyPrepaymentButton.setEnabled(true);
                            mApplyPrepaymentLayout.setEnabled(true);
                        } else {
                            mApplyPrepaymentButton.setEnabled(false);
                            mApplyPrepaymentLayout.setEnabled(false);
                        }
                        if (moneyPrepaymentDto.amountOfSalary > 0) {
                            mMoneyPendingTextView.setText(
                                    EniFormatUtil.convertMoneyFormat(moneyPrepaymentDto.amountOfSalary));
                        } else {
                            mMoneyPendingTextView.setText(EniConstant.EMPTY);
                        }
                    }
                }
            }
        });
    }

    /**
     * Get all announcements
     */
    private void getAnnouncementList() {
        eniShowNowLoading(getActivity());
        ApiRequest.getAnnouncementList(mAnnouncementParamMap, new ApiCallback<AnnouncementResponse>() {
            @Override
            public void failure(RetrofitError retrofitError, ApiError apiError) {
                eniCancelNowLoading();
                if (getActivity() != null) {
                    if (getActivity().isFinishing()) {
                        return;
                    }
                }
                ErrorResponse body = (ErrorResponse) retrofitError.getBodyAs(ErrorResponse.class);
                if (body == null) {
                    return;
                }
                // User stopped service
                if (body.code == ApiCode.USER_STOPPED_SERVICE) {
                    goStopServiceScreen();
                } else {
                    Toast.makeText(getActivity(), apiError.getError().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void success(AnnouncementResponse announcementResponse, Response response) {
                eniCancelNowLoading();
                if (BuildConfig.DEBUG) {
                    EniLogUtil.d(getClass(), "[success]getAnnouncementList" + announcementResponse.statusCode);
                }
                if (announcementResponse.statusCode == ApiCode.SUCCESS) {
                    if (announcementResponse.data.announcementDtoList != null) {
                        mAnnouncementDtoList = announcementResponse.data.announcementDtoList;
                        displayAnnouncement(mAnnouncementDtoList);
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
