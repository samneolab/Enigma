package com.neolab.enigma.activity;

import android.os.Bundle;
import android.os.Handler;

import com.neolab.enigma.R;
import com.neolab.enigma.util.EniEncryptionUtil;

/**
 * Splash screen
 */
public class SplashActivity extends BaseActivity {

    /** Millisecond time delay */
    private static final int TIME_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (EniEncryptionUtil.isLogin(SplashActivity.this)) {
                    startActivity(MainActivity.class);
                } else {
                    startActivity(LoginActivity.class);
                }
                finish();
            }
        }, TIME_DELAY);
    }
}
