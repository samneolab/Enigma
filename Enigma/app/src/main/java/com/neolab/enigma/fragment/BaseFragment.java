package com.neolab.enigma.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.neolab.enigma.R;
import com.neolab.enigma.dto.ToolbarTypeDto;

/**
 * Base Fragment
 *
 * @author LongHV.
 */
public abstract class BaseFragment extends Fragment {

    protected abstract void initData();
    protected abstract void findView();
    protected abstract void initEvent();
    protected abstract ToolbarTypeDto getToolbarTypeDto();

    /** Control access */
    private final Object mutex = new Object();
    /** Loading progress */
    private ProgressDialog mProgressDialog;
    /** show loading flag */
    private boolean isShowLoading;
    /** Count request cancel loading */
    protected int requestCancelLoadingCount = 0;
    /** Count request show loading  */
    private int mRequestShowLoadingCount = 0;

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

    protected OnBaseFragmentListener onBaseFragmentListener;

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onBaseFragmentListener = (OnBaseFragmentListener)activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        onBaseFragmentListener.onToolbarListener(getToolbarTypeDto());
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
    protected void addFragment(Fragment fragment, boolean isBackStack) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_pop_enter, R.anim.fragment_pop_exit);
        if (isBackStack) {
            transaction.addToBackStack(fragment.getClass().getName());
        }
        transaction.replace(R.id.main_root_frameLayout, fragment);
        transaction.commit();
    }

    /**
     * Show dialog loading when call api
     *
     * @param context Context
     */
    protected void eniShowNowLoading(Context context) {
        mRequestShowLoadingCount++;
        if (!isShowLoading) {
            isShowLoading = true;
            synchronized (mutex) {
                mProgressDialog = new ProgressDialog(context);
                mProgressDialog.setCancelable(false);
                mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                mProgressDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                mProgressDialog.show();
                mProgressDialog.setContentView(R.layout.loading);
            }
        }
    }

    /**
     * Cancel display dialog
     */
    public boolean eniCancelNowLoading() {
        requestCancelLoadingCount++;
        if (mRequestShowLoadingCount == requestCancelLoadingCount) {
            synchronized (mutex) {
                resetShowLoading();
                if (mProgressDialog == null) {
                    return true;
                }
                mProgressDialog.dismiss();
                return true;
            }
        }
        return false;
    }

    /**
     * Reset status loading dialog
     */
    private void resetShowLoading(){
        isShowLoading = false;
        requestCancelLoadingCount = 0;
        mRequestShowLoadingCount = 0;
    }

    public interface OnBaseFragmentListener {
        void onToolbarListener(ToolbarTypeDto toolbarTypeDto);
    }

}
