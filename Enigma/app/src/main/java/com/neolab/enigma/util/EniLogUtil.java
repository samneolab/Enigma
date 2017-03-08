
package com.neolab.enigma.util;

import android.util.Log;

import com.neolab.enigma.BuildConfig;

/**
 * Utility for log output.
 *
 * @author Pika
 */
public final class EniLogUtil {

    /** タグ */
    private static final String TAG = "Demo.neolab";

    /** For message format [ */
    private static final String BRACKET_START = "[";

    /** For message format ] */
    private static final String BRACKET_END = "] ";

    /** Name package */
    private static final String MY_PKG_NAME = "neolab.Demo";

    /**
     * Output a debug message.
     * 
     * @param cls class
     * @param msg message
     */
    public static void d(Class<?> cls, String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, BRACKET_START + getMyClassName(cls) + BRACKET_END + msg);
        }
    }

    /**
     * Output a debug message.
     *
     * <p>
     * Break a line with the specified number of characters
     * </p>
     *
     * @param cls class
     * @param msg message
     * @param splitLen number of characters per line
     */
    public static void d(Class<?> cls, String msg, int splitLen) {
        if (!BuildConfig.DEBUG) {
            return;
        }
        for (int i = 0; i < (msg.length() - 1) / splitLen + 1; i++) {
            EniLogUtil.d(cls, msg.substring(i * splitLen,
                    Math.min((i + 1) * splitLen, msg.length())));
        }
    }

    /**
     * Output debug messages.
     * 
     * @param cls class
     * @param msg message
     * @param tr exception
     */
    public static void d(Class<?> cls, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, BRACKET_START + getMyClassName(cls) + BRACKET_END + msg, tr);
        }
    }

    /**
     * Output error message.
     * 
     * @param cls class
     * @param msg message
     */
    public static void e(Class<?> cls, String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, BRACKET_START + getMyClassName(cls) + BRACKET_END + msg);
        }
    }

    /**
     * Output error message.
     * 
     * @param cls class
     * @param msg message
     * @param tr exception
     */
    public static void e(Class<?> cls, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, BRACKET_START + getMyClassName(cls) + BRACKET_END + msg, tr);
        }
    }

    /**
     * Output information message.
     * 
     * @param cls class
     * @param msg message
     */
    public static void i(Class<?> cls, String msg) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, BRACKET_START + getMyClassName(cls) + BRACKET_END + msg);
        }
    }

    /**
     * Output information message.
     *
     * @param cls class
     * @param msg message
     * @param tr exception
     */
    public static void i(Class<?> cls, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, BRACKET_START + getMyClassName(cls) + BRACKET_END + msg, tr);
        }
    }

    /**
     * Output detail message.
     * 
     * @param cls class
     * @param msg message
     */
    public static void v(Class<?> cls, String msg) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, BRACKET_START + getMyClassName(cls) + BRACKET_END + msg);
        }
    }

    /**
     * Output detail message.
     * 
     * @param cls class
     * @param msg message
     * @param tr exception
     */
    public static void v(Class<?> cls, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, BRACKET_START + getMyClassName(cls) + BRACKET_END + msg, tr);
        }
    }

    /**
     * Output detail message..
     * 
     * @param cls class
     * @param msg message
     */
    public static void w(Class<?> cls, String msg) {
        if (BuildConfig.DEBUG) {
            Log.w(TAG, BRACKET_START + getMyClassName(cls) + BRACKET_END + msg);
        }
    }

    /**
     * Output warning message.
     * 
     * @param cls class
     * @param tr message
     */
    public static void w(Class<?> cls, Throwable tr) {
        if (BuildConfig.DEBUG) {
            Log.w(TAG, tr);
        }
    }

    /**
     * Output warning message.
     * 
     * @param cls class
     * @param msg message
     * @param tr exception
     */
    public static void w(Class<?> cls, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            Log.w(TAG, BRACKET_START + getMyClassName(cls) + BRACKET_END + msg, tr);
        }
    }

    /**
     * Output unexpected message
     * 
     * @param cls class
     * @param msg message
     */
    public static void wtf(Class<?> cls, String msg) {
        if (BuildConfig.DEBUG) {
            Log.wtf(TAG, BRACKET_START + getMyClassName(cls) + BRACKET_END + msg);
        }
    }

    /**
     * Output unexpected message
     * 
     * @param cls class
     * @param msg message
     * @param tr exception
     */
    public static void wtf(Class<?> cls, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            Log.wtf(TAG, BRACKET_START + getMyClassName(cls) + BRACKET_END + msg, tr);
        }
    }


    /**
     * Constructor.
     */
    private EniLogUtil() {
    }

    /**
     * Get class name.
     * 
     * @param cls class
     * @return class name excluding package name
     */
    private static String getMyClassName(Class<?> cls)
    {
        return cls.getName().replace(MY_PKG_NAME, "");
    }

}
