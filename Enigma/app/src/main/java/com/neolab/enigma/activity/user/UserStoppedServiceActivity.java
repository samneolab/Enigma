package com.neolab.enigma.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.neolab.enigma.R;
import com.neolab.enigma.activity.BaseActivity;
import com.neolab.enigma.activity.LoginActivity;

/**
 * User stop using service
 *
 * @author Pika
 */
public class UserStoppedServiceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_stopped_service);
        initData();
    }

    /**
     * Initialize
     */
    private void initData() {
        TextView titleTextView = (TextView) findViewById(R.id.title_textView);
        titleTextView.setText(getString(R.string.user_stopped_service_title));
        findViewById(R.id.user_stopped_service_go_login_screen_layout).setOnClickListener(new View.OnClickListener() {
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
        startActivity(LoginActivity.class, R.anim.animation_fade_in_left_to_right, R.anim.animation_fade_out_left_to_right);
    }
}
