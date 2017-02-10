package com.neolab.enigma.activity.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.neolab.enigma.R;
import com.neolab.enigma.dto.menu.MenuDto;

import java.util.List;

/**
 * @author LongHV.
 */

public class DrawerAdapter extends BaseAdapter {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private final List<MenuDto> mMenuDtoList;

    public DrawerAdapter(Context context, List<MenuDto> menuDtoList) {
        this.mContext = context;
        this.mMenuDtoList = menuDtoList;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mMenuDtoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMenuDtoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.menu_item, null);
            viewHolder = new ViewHolder();
            viewHolder.iconImageView = (ImageView) convertView.findViewById(R.id.drawer_item_icon_imageView);
            viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.drawer_item_title_textView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MenuDto menuDto = (MenuDto) getItem(position);
        viewHolder.iconImageView.setImageDrawable(ContextCompat.getDrawable(mContext, menuDto.icon));
        viewHolder.titleTextView.setText(menuDto.title);

        return convertView;
    }

    static class ViewHolder {
        ImageView iconImageView;
        TextView titleTextView;
    }
}
