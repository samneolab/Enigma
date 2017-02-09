package com.neolab.enigma;

import android.app.Application;

import com.neolab.enigma.ws.core.ApiClient;
import com.neolab.enigma.ws.core.ApiConfig;

/**
 * @author LongHV.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        createService();
    }

    private void createService() {
        ApiConfig apiConfig = ApiConfig.builder()
                .context(getApplicationContext())
                .baseUrl(getApplicationContext().getString(R.string.base_url))
                .build();
        ApiClient.getInstance().init(apiConfig);
    }
}
