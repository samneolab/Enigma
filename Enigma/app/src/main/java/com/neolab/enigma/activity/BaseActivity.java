package com.neolab.enigma.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.neolab.enigma.R;

/**
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

    @Override
    public void onBackPressed() {
        eniCancelNowLoading();
        super.onBackPressed();
    }

    protected void startActivity(Class<?> cls){
        if (cls != null) {
            startActivity(new Intent(BaseActivity.this, cls));
            overridePendingTransition(R.anim.animation_fade_in, R.anim.animation_fade_out);
        }
    }
}
