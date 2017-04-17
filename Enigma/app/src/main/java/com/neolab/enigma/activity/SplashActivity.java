package com.neolab.enigma.activity;

import android.content.Intent;
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
        if (!isTaskRoot()) {
            final Intent intent = getIntent();
            if (intent != null) {
                final String intentAction = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null && intentAction.equals(Intent.ACTION_MAIN)) {
                    finish();
                    return;
                }
            }
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.animation_fade_in_right_to_left, R.anim.animation_fade_out_right_to_left);
            }
        }, TIME_DELAY);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

}
