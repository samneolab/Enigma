package com.neolab.enigma.service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.neolab.enigma.R;
import com.urbanairship.AirshipConfigOptions;
import com.urbanairship.Autopilot;
import com.urbanairship.UAirship;

import static android.util.Log.DEBUG;
import static android.util.Log.ERROR;

/**
 * Config api key, it called in manifest file,
 * Don't remove it.
 *
 * @author Pika.
 */
public class EnigmaAutopilot extends Autopilot {

    @Nullable
    @Override
    public AirshipConfigOptions createAirshipConfigOptions(@NonNull Context context) {
        AirshipConfigOptions options = new AirshipConfigOptions.Builder()
                .setProductionAppKey(context.getString(R.string.productionAppKey))
                .setProductionAppSecret(context.getString(R.string.productionAppSecret))
                .setInProduction(true)
                .setGcmSender(context.getString(R.string.gcmSender))
                .setDevelopmentLogLevel(DEBUG)
                .setProductionLogLevel(ERROR)
                .build();
        return options;
    }

    @Override
    public void onAirshipReady(UAirship airship) {
        airship.getPushManager().setUserNotificationsEnabled(false);
    }
}
