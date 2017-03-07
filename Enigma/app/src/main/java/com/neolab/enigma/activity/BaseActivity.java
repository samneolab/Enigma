package com.neolab.enigma.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.neolab.enigma.R;

/**
 * Base Activity
 *
 * @author LongHV.
 */

public class BaseActivity extends AppCompatActivity {

    /**
     * Control access
     */
    private final Object mutex = new Object();

    private ProgressDialog mProgressDialog;

    protected void eniShowLoading() {
        synchronized (mutex) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mProgressDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.loading);
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
            mProgressDialog.dismiss();
            return false;
        }
    }

    /**
     * Start new activity
     *
     * @param cls Class
     */
    protected void startActivity(Class<?> cls){
        if (cls != null) {
            startActivity(new Intent(BaseActivity.this, cls));
            overridePendingTransition(R.anim.animation_fade_in_right_to_left, R.anim.animation_fade_out_right_to_left);
        }
    }

    /**
     * Start new activity
     *
     * @param cls Class
     */
    protected void startActivity(Class<?> cls, int enterAnim, int exitAnim){
        if (cls != null) {
            startActivity(new Intent(BaseActivity.this, cls));
            overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Add fragment to activity
     *
     * @param fragment Fragment
     * @param isBackStack Transaction will be remembered after it is committed,
     * and will reverse its operation when later popped off the stack
     */
    protected void addFragment(Fragment fragment, boolean isBackStack) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (isBackStack) {
            transaction.addToBackStack(fragment.getClass().getName());
        }
        transaction.replace(R.id.main_root_frameLayout, fragment);
        transaction.commit();
    }


    @Override
    public void onBackPressed() {
        eniCancelNowLoading();
        super.onBackPressed();
    }
}
