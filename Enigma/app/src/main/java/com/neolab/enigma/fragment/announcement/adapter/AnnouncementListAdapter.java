package com.neolab.enigma.fragment.announcement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.neolab.enigma.R;
import com.neolab.enigma.dto.ws.announcement.AnnouncementDto;

import java.util.List;

/**
 * @author Pika
 */
public class AnnouncementListAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<AnnouncementDto> mAnnouncementDtoList;

    public AnnouncementListAdapter(Context context, List<AnnouncementDto> announcementDtoList){
        mContext = context;
        mAnnouncementDtoList = announcementDtoList;
        mLayoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mAnnouncementDtoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAnnouncementDtoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.announcement_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.announcement_list_item_title_textView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        AnnouncementDto announcementDto = (AnnouncementDto)getItem(position);
        viewHolder.titleTextView.setText(announcementDto.title);
        return convertView;
    }

    /**
     * Add announcement list to the end of this list
     *
     * @param announcementDtoList List<AnnouncementDto>
     */
    public void addAnnouncementList(List<AnnouncementDto> announcementDtoList){
        mAnnouncementDtoList.addAll(announcementDtoList);
    }

    /**
     * ViewHolder class
     */
    private static class ViewHolder {
        TextView titleTextView;
    }
}
