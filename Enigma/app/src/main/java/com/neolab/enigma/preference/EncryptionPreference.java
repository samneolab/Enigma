package com.neolab.enigma.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.neolab.enigma.EniConstant;
import com.neolab.enigma.util.StringUtil;

/**
 * Login Information Preferences
 *
 * @author LongHV.
 */
public class EncryptionPreference extends BasePreference {

    /** Preference name */
    private static final String PREFERENCE_NAME = "encryption_preference";
    /** Token key */
    private static final String KEY_TOKEN = "token";
    /** UserId key */
    private static final String KEY_USER_ID = "userId";
    /** Check user login flag key */
    private static final String KEY_IS_USER_LOGIN = "isUserLogin";
    /** Login state key */
    private static final String KEY_LOGIN_STATUS = "loginStatusCode";

    /** Access token */
    public String token;
    /** UserId */
    public String userId;
    /** Check user login flag */
    public boolean isUserLogin;
    /** Login status */
    public int loginStatusCode;

    /**
     * Constructor
     *
     * @param context Context
     */
    public EncryptionPreference(Context context) {
        super();
        this.context = context;
        init();
    }

    @Override
    protected String getPreferenceName() {
        return PREFERENCE_NAME;
    }

    @Override
    protected void setData(SharedPreferences.Editor editor) {
        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_USER_ID, userId);
        editor.putInt(KEY_LOGIN_STATUS, loginStatusCode);
        editor.putBoolean(KEY_IS_USER_LOGIN, isUserLogin);
    }

    @Override
    protected void getData(SharedPreferences preferences) {
        token = preferences.getString(KEY_TOKEN, EniConstant.TOKEN_DEFAULT);
        userId = preferences.getString(KEY_USER_ID, EniConstant.USER_ID_DEFAULT);
        loginStatusCode = preferences.getInt(KEY_LOGIN_STATUS, EniConstant.USER_STATUS_DEFAULT);
        isUserLogin = preferences.getBoolean(KEY_IS_USER_LOGIN, false);
    }

    /**
     * Check login status
     *
     * @return true if user login, otherwise false
     */
    public boolean isLogin() {
        return (StringUtil.isNotBlank(userId)
                && StringUtil.isNotBlank(token)
                && loginStatusCode == EniConstant.UserStatus.MEMBER);
    }
}
