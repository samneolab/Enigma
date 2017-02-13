package com.neolab.enigma.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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

    protected abstract void initData();
    protected abstract void findView();
    protected abstract void initEvent();

    /**
     * The view global
     * @must non-abstract subclass of onCreateView in return view;
     * @warn can not be created in subclasses
     */
    protected View view = null;

    /** LayoutInflater */
    protected LayoutInflater inflater = null;

    /** Add the layout of this Fragment view */
    @Nullable
    protected ViewGroup container = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        return view;
    }

    public void setContentView(int layoutResID) {
        setContentView(inflater.inflate(layoutResID, container, false));
    }

    public void setContentView(View v) {
        setContentView(v, null);
    }

    public void setContentView(View v, ViewGroup.LayoutParams params) {
        view = v;
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V findViewById(int id) {
        return (V) view.findViewById(id);
    }

    /** Find and get the control via id and setOnClickListener
     * @param id id
     * @param l OnClickListener
     * @return View
     */
    public <V extends View> V findViewById(int id, View.OnClickListener l) {
        V v = findViewById(id);
        v.setOnClickListener(l);
        return v;
    }

    /**
     * Add fragment to activity
     *
     * @param fragment    Fragment
     * @param isBackStack Transaction will be remembered after it is committed,
     *                    and will reverse its operation when later popped off the stack
     */
    protected void addFraggment(Fragment fragment, boolean isBackStack) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (isBackStack) {
            transaction.addToBackStack(fragment.getClass().getName());
        }
        transaction.replace(R.id.main_root_frameLayout, fragment);
        transaction.commit();
    }

}
