package com.neolab.enigma;

import android.app.Application;
import android.content.Intent;

import com.neolab.enigma.activity.MainActivity;
import com.neolab.enigma.preference.EncryptionPreference;
import com.neolab.enigma.ws.core.ApiClient;
import com.neolab.enigma.ws.core.ApiConfig;
import com.urbanairship.UAirship;

/**
 * @author LongHV.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        createService();
    }

    /**
     * The method is used to create service
     */
    private void createService() {
        ApiConfig apiConfig = ApiConfig.builder()
                .context(getApplicationContext())
                .baseUrl(getApplicationContext().getString(R.string.base_url))
                .build();
        ApiClient.getInstance().init(apiConfig);
    }
}
