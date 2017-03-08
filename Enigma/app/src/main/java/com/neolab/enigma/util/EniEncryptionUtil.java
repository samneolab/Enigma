package com.neolab.enigma.util;

import android.content.Context;

import com.neolab.enigma.EniConstant;
import com.neolab.enigma.preference.EncryptionPreference;

/**
 * Utility for encryption
 *
 * @author Pika.
 */
public class EniEncryptionUtil {

    /**
     * Reset data when logout
     *
     * @param context Context
     */
    public static void resetDataForLogout(final Context context){
        final EncryptionPreference pref = new EncryptionPreference(context.getApplicationContext());
        pref.token = EniConstant.TOKEN_DEFAULT;
        pref.userId = EniConstant.USER_ID_DEFAULT;
        pref.isUserLogin = false;
        pref.loginStatusCode = EniConstant.USER_STATUS_DEFAULT;
        pref.write();
    }

    /**
     * Return login state
     *
     * <p>
     * Check userId and token to guarantee that login are valid.
     * </p>
     *
     * @param context Context
     * @return true if logged in, otherwise return false
     */
    public static boolean isLogin(final Context context) {
        return new EncryptionPreference(context.getApplicationContext()).isLogin();
    }

}
