package com.neolab.enigma.fragment.top;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neolab.enigma.R;
import com.neolab.enigma.fragment.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top, container, false);
    }

    @Override
    protected void findView() {

    }

    @Override
    protected void init() {

    }

}
