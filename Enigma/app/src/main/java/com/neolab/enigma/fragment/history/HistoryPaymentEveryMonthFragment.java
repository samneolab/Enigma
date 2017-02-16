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
public class HistoryPaymentEveryMonthFragment extends Fragment {


    public HistoryPaymentEveryMonthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_payment_every_month, container, false);
    }

}
