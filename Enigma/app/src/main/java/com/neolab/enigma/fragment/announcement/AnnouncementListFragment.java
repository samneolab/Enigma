package com.neolab.enigma.fragment.announcement;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neolab.enigma.EniConstant;
import com.neolab.enigma.R;
import com.neolab.enigma.dto.ToolbarTypeDto;
import com.neolab.enigma.fragment.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnnouncementListFragment extends BaseFragment {

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
    protected void initData() {

    }

    @Override
    protected void findView() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected ToolbarTypeDto getToolbarTypeDto() {
        ToolbarTypeDto toolbarTypeDto = new ToolbarTypeDto();
        toolbarTypeDto.type = EniConstant.ToolbarType.DETAIL_DISPLAY_DRAWER;
        return toolbarTypeDto;
    }

}
