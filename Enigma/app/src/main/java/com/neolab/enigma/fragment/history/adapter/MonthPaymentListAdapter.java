package com.neolab.enigma.fragment.history.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.dto.ws.history.MonthPaymentDto;
import com.neolab.enigma.util.EniLanguageUtil;
import com.neolab.enigma.util.EniUtil;

import java.util.List;

/**
 * @author LongHV.
 */

public class MonthPaymentListAdapter extends BaseAdapter {

    private Context mContext;
    private List<MonthPaymentDto> mMonthPaymentDtoList;
    private LayoutInflater mLayoutInflater;

    public MonthPaymentListAdapter(final Context context, final List<MonthPaymentDto> monthPaymentDtoList) {
        this.mContext = context;
        mMonthPaymentDtoList = monthPaymentDtoList;
        if (mContext != null) {
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
    }

    @Override
    public int getCount() {
        return mMonthPaymentDtoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMonthPaymentDtoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.history_payment_every_month_item, null);
            viewHolder = new ViewHolder();
            viewHolder.dateApplyTextView = (TextView) convertView.findViewById(R.id.history_payment_every_month_date_apply_textView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MonthPaymentDto monthPaymentDto = (MonthPaymentDto) getItem(position);
        StringBuilder builder = new StringBuilder();
        if (EniLanguageUtil.isJapanLanguage()) {
            builder.append(monthPaymentDto.year);
            builder.append(mContext.getString(R.string.history_year));
            builder.append(monthPaymentDto.month);
            builder.append(mContext.getString(R.string.history_month));
        } else {
            builder.append(monthPaymentDto.month);
            builder.append(EniConstant.SLASH);
            builder.append(monthPaymentDto.year);
        }
        viewHolder.dateApplyTextView.setText(builder.toString());
        return convertView;
    }

    /**
     * ViewHolder
     */
    static class ViewHolder {
        TextView dateApplyTextView;
    }
}
