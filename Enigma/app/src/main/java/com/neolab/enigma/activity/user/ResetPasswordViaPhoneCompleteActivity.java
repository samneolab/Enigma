package com.neolab.enigma.activity.user;

import android.os.Bundle;
import android.view.View;

import com.neolab.enigma.R;
import com.neolab.enigma.activity.BaseActivity;
import com.neolab.enigma.activity.LoginActivity;

/**
 * Complete reset password via phone  activity
 *
 * @author Pika
 */
public class ResetPasswordViaPhoneCompleteActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reset_password_via_phone_complete);
        initEvent();
    }

    private void initEvent() {
        (findViewById(R.id.toolbar_back_textView)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        (findViewById(R.id.user_reset_password_via_phone_complete_go_login_screen_layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(LoginActivity.class, R.anim.animation_fade_in_left_to_right, R.anim.animation_fade_out_left_to_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(ResetPasswordViaPhoneActivity.class, R.anim.animation_fade_in_left_to_right, R.anim.animation_fade_out_left_to_right);
    }
}
