package com.neolab.enigma.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.neolab.enigma.BuildConfig;
import com.neolab.enigma.R;
import com.neolab.enigma.activity.LoginActivity;
import com.neolab.enigma.activity.user.UserStoppedServiceActivity;
import com.neolab.enigma.dto.HeaderDto;
import com.neolab.enigma.util.EniEncryptionUtil;
import com.neolab.enigma.util.EniLogUtil;

/**
 * Base Fragment
 *
 * @author LongHV.
 */
public abstract class BaseFragment extends Fragment{

    protected abstract void initData();

    protected abstract void findView();

    protected abstract void initEvent();

    protected abstract HeaderDto getHeaderTypeDto();

    public static class EniSemaphore {
        public boolean lock;
    }

    /** Semaphore */
    private final EniSemaphore semaphoreSingleDialog = new EniSemaphore();

    /** Control access */
    private final Object mutex = new Object();

    /** Loading progress */
    private ProgressDialog mProgressDialog;
    /** Count request show loading */
    private int mLoadingDialogCounter = 0;

    /**
     * The view global
     *
     * @must non-abstract subclass of onCreateView in return view;
     * @warn can not be created in subclasses
     */
    protected View view = null;

    /**
     * LayoutInflater
     */
    private LayoutInflater inflater = null;

    /**
     * Add the layout of this Fragment view
     */
    @Nullable
    private ViewGroup container = null;

    private OnBaseFragmentListener onBaseFragmentListener;

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onBaseFragmentListener = (OnBaseFragmentListener) activity;
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
        if (BuildConfig.DEBUG) {
            EniLogUtil.d(getClass(), "onResume");
        }
        onBaseFragmentListener.onHeaderListener(getHeaderTypeDto());
    }

    public void setContentView(int layoutResID) {
        setContentView(inflater.inflate(layoutResID, container, false));
    }

    private void setContentView(View v) {
        setContentView(v, null);
    }

    private void setContentView(View v, ViewGroup.LayoutParams params) {
        view = v;
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V findViewById(int id) {
        return (V) view.findViewById(id);
    }

    /**
     * Find and get the control via id and setOnClickListener
     *
     * @param id id
     * @param l  OnClickListener
     * @return View
     */
    public <V extends View> V findViewById(int id, View.OnClickListener l) {
        V v = findViewById(id);
        v.setOnClickListener(l);
        return v;
    }

    /**
     * Replace fragment to activity
     *
     * @param fragment    Fragment
     * @param isBackStack Transaction will be remembered after it is committed,
     *                    and will reverse its operation when later popped off the stack
     */
    protected void replaceFragment(Fragment fragment, boolean isBackStack) {
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
     * Navigate to stop service screen
     */
    protected void goStopServiceScreen(String messageError) {
        EniEncryptionUtil.resetDataForLogout(getActivity());
        Intent intent = new Intent(getActivity(), UserStoppedServiceActivity.class);
        intent.putExtra(UserStoppedServiceActivity.EXTRA_MESSAGE_ERROR, messageError);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.animation_fade_in_right_to_left, R.anim.animation_fade_out_right_to_left);
        getActivity().finish();
    }

    /**
     * Show dialog loading when call api
     *
     * @param context Context
     */
    protected void eniShowNowLoading(Context context) {
        synchronized (mutex) {
            if (mLoadingDialogCounter == 0) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    mProgressDialog = new ProgressDialog(context);
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    mProgressDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    mProgressDialog.show();
                    mProgressDialog.setContentView(R.layout.loading);
                }
            }
            mLoadingDialogCounter++;
        }
    }

    /**
     * Cancel display dialog
     */
    public boolean eniCancelNowLoading() {
        synchronized (mutex) {
            if (mProgressDialog == null) {
                return true;
            }
            mLoadingDialogCounter--;
            if (mLoadingDialogCounter <= 0) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    mProgressDialog.dismiss();
                }
                mLoadingDialogCounter = 0;
                return true;
            }
        }
        return false;
    }

    /**
     * popping the fragment back stack or finishing the activity.
     */
    protected void onBackPressed() {
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }

    @Override
    public void onDestroy() {
        if (getActivity() != null
                && !getActivity().isFinishing()
                && mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        super.onDestroy();
    }

    /**
     * Display dialog (OK / Cancel button)
     *
     * @param message         message
     * @param okListener      OnClickListener
     * @param dismissListener OnDismissListener
     * @return AlertDialog.Builder
     */
    protected AlertDialog.Builder eniShowDialog(
            final Context context,
            final String message,
            final DialogInterface.OnClickListener okListener,
            final DialogInterface.OnDismissListener dismissListener) {

        synchronized (semaphoreSingleDialog) {
            // Semaphore object specified
            if (semaphoreSingleDialog.lock) {
                // processing
                EniLogUtil.d(getClass(), "[eniShowDialog] lock");
                return null;
            }
            semaphoreSingleDialog.lock = true;
        }

        if (getActivity().isFinishing()) {
            // Do not process when activity is invalid
            EniLogUtil.d(getClass(), "[eniShowDialog] activityAvailableFlag=FALSE");
            synchronized (semaphoreSingleDialog) {
                semaphoreSingleDialog.lock = false;
            }
            return null;
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        synchronized (semaphoreSingleDialog) {
                            semaphoreSingleDialog.lock = false;
                        }
                        if (okListener != null) {
                            okListener.onClick(dialog, which);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int id) {
                        synchronized (semaphoreSingleDialog) {
                            semaphoreSingleDialog.lock = false;
                        }
                    }
                });
        builder.show().setOnDismissListener(dismissListener);
        return builder;
    }

    /**
     * Wake up toolbar
     */
    public interface OnBaseFragmentListener {
        void onHeaderListener(HeaderDto headerDto);
    }

}
