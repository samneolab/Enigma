package com.neolab.enigma.fragment.announcement;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.dto.HeaderDto;
import com.neolab.enigma.fragment.BaseFragment;
import com.neolab.enigma.util.EniLogUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnnouncementListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_announcement_list, container, false);
        return view;
    }
//
//    @Override
//    protected void initData() {
//
//    }
//
//    @Override
//    protected void findView() {
//
//    }
//
//    @Override
//    protected void initEvent() {
//
//    }
//
//    @Override
//    protected HeaderDto getHeaderTypeDto() {
//        HeaderDto headerDto = new HeaderDto();
//        headerDto.type = EniConstant.ToolbarType.DISPLAY_BACK_LOGO_DRAWER;
//        return headerDto;
//    }

}
