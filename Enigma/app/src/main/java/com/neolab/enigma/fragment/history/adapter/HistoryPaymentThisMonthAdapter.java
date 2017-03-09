package com.neolab.enigma.fragment.history.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.dto.ws.history.SalaryRequestDto;
import com.neolab.enigma.util.EniFormatUtil;

import java.util.List;

/**
 * @author LongHV.
 */
public class HistoryPaymentThisMonthAdapter extends BaseAdapter {

    /**
     * LayoutInflater
     */
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<SalaryRequestDto> mSalaryRequestDtoList;
    private OnItemListViewListener mOnItemListViewListener;

    /**
     * Constructor
     *
     * @param context Context
     * @param salaryRequestDtoList salary request list
     */
    public HistoryPaymentThisMonthAdapter(Context context, List<SalaryRequestDto> salaryRequestDtoList, OnItemListViewListener onItemListViewListener) {
        mContext = context;
        mSalaryRequestDtoList = salaryRequestDtoList;
        mOnItemListViewListener = onItemListViewListener;
        if (mContext != null) {
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
    }

    @Override
    public int getCount() {
        return mSalaryRequestDtoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mSalaryRequestDtoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.history_payment_this_month_item, null);
            viewHolder = new ViewHolder();
            viewHolder.statusPaymentTextView = (TextView) convertView.findViewById(R.id.history_payment_this_month_status_request_textView);
            viewHolder.requestInformTextView = (TextView) convertView.findViewById(R.id.history_payment_this_month_salary_request_textView);
            viewHolder.dateRequestTextView = (TextView) convertView.findViewById(R.id.history_payment_this_month_date_request_textView);
            viewHolder.iconDetailImageView = (ImageView) convertView.findViewById(R.id.history_payment_this_month_icon_detail_imageView);
            viewHolder.itemFrameLayout = (FrameLayout) convertView.findViewById(R.id.history_payment_this_month_item_layout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final SalaryRequestDto salaryRequestDto = (SalaryRequestDto) getItem(position);
        // Check request payment is applying
        if (salaryRequestDto.status == EniConstant.HISTORY_SALARY_REQUEST_APPLYING) {
            viewHolder.itemFrameLayout.setEnabled(true);
            viewHolder.statusPaymentTextView.setBackgroundResource(R.drawable.button_history_payment_applying_status);
            viewHolder.requestInformTextView.setTextColor(ContextCompat.getColor(mContext, R.color.text_primary));
            viewHolder.iconDetailImageView.setVisibility(View.VISIBLE);
            viewHolder.dateRequestTextView.setText(mContext.getString(R.string.history_date_request)
                    + EniConstant.LARGE_SPACE + salaryRequestDto.createDate);
        } else {
            viewHolder.itemFrameLayout.setEnabled(false);
            viewHolder.statusPaymentTextView.setBackgroundResource(R.drawable.button_history_payment_done_status);
            viewHolder.requestInformTextView.setTextColor(ContextCompat.getColor(mContext, R.color.history_item_status_done));
            viewHolder.iconDetailImageView.setVisibility(View.GONE);
            viewHolder.dateRequestTextView.setText(mContext.getString(R.string.history_date_request)
                    + EniConstant.LARGE_SPACE + salaryRequestDto.appliedDate);
        }

        viewHolder.statusPaymentTextView.setText(salaryRequestDto.statusRequestDto.name);
        StringBuilder requestInform;
        requestInform = new StringBuilder();
//        requestInform.append(mContext.getResources().getString(R.string.top_yen_unit));
//        requestInform.append(EniFormatUtil.convertMoneyFormat(salaryRequestDto.amountOfSalary));
//        requestInform.append(EniConstant.SPACE);
//        requestInform.append(EniConstant.PARENTHESIS_START);
//        requestInform.append(mContext.getString(R.string.history_salary_deduction));
//        requestInform.append(EniConstant.SPACE);
        requestInform.append(mContext.getResources().getString(R.string.top_yen_unit));
        requestInform.append(EniConstant.SPACE);
        requestInform.append(EniFormatUtil.convertMoneyFormat(salaryRequestDto.total));
//        requestInform.append(EniConstant.PARENTHESIS_END);
        viewHolder.requestInformTextView.setText(requestInform.toString());
        viewHolder.dateRequestTextView.setText(mContext.getString(R.string.history_date_request)
                + EniConstant.LARGE_SPACE + salaryRequestDto.appliedDate);
        viewHolder.itemFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemListViewListener.onItemListener(salaryRequestDto);
            }
        });
        return convertView;
    }

    /**
     * View holder class
     */
    private static class ViewHolder {
        TextView statusPaymentTextView;
        TextView requestInformTextView;
        TextView dateRequestTextView;
        ImageView iconDetailImageView;
        FrameLayout itemFrameLayout;
    }

    /**
     * Item listView listener
     */
    public interface OnItemListViewListener {
        void onItemListener(SalaryRequestDto salaryRequestDto);
    }
}
