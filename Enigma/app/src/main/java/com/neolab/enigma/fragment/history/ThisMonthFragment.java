package com.neolab.enigma.fragment.history;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neolab.enigma.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThisMonthFragment extends Fragment {


    public ThisMonthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_this_month, container, false);
    }

}
