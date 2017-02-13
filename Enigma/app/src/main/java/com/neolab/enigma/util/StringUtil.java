package com.neolab.enigma.util;

/**
 * Utility for String
 *
 * @author LongHV.
 */
public class StringUtil {

    /**
     * Check data blank
     *
     * @param message message
     * @return true if data is blank, otherwise false
     */
    public static boolean isBlank(String message) {
        if (message == null || message.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Check data not blank
     *
     * @param message message
     * @return true if data is blank, otherwise false
     */
    public static boolean isNotBlank(String message) {
        if (message != null && !message.isEmpty()) {
            return true;
        }
        return false;
    }
}
