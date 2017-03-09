package com.neolab.enigma.util;

import java.util.Locale;

/**
 * Utility for language.
 *
 * @author Pika.
 */
public class EniLanguageUtil {

    private static final String JAPAN_LANGUAGE = "日本語";
    private static final String PREFIX_ENGLISH = "en";
    private static final String PREFIX_JAPAN = "ja";

    /**
     * Get prefix language for api
     *
     * @return prefix language
     */
    public static String getPrefixDeviceLanguage() {
        if (Locale.getDefault().getDisplayLanguage().equals(JAPAN_LANGUAGE)){
            return PREFIX_JAPAN;
        }
        return PREFIX_ENGLISH;
    }

    /**
     * Check device language
     *
     * @return true if language is Japan, otherwise false
     */
    public static boolean isJapanLanguage(){
        if (Locale.getDefault().getDisplayLanguage().equals(JAPAN_LANGUAGE)){
            return true;
        }
        return false;
    }
}
