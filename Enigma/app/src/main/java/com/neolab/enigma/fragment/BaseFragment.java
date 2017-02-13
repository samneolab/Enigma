package com.neolab.enigma.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neolab.enigma.R;

/**
 * Base Fragment
 *
 * @author LongHV.
 */

public abstract class BaseFragment extends Fragment {

    protected abstract void findView();
    protected abstract void init();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        findView();
        init();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * Add fragment to activity
     *
     * @param fragment Fragment
     * @param isBackStack Transaction will be remembered after it is committed,
     * and will reverse its operation when later popped off the stack
     */
    protected void addFrxagment(Fragment fragment, boolean isBackStack) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (isBackStack) {
            transaction.addToBackStack(fragment.getClass().getName());
        }
        transaction.add(R.id.main_root_frameLayout, fragment);
        transaction.commit();
    }

}
