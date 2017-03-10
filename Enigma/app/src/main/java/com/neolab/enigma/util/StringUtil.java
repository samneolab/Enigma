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
        return (message == null || message.isEmpty());
    }

    /**
     * Check data not blank
     *
     * @param message message
     * @return true if data is blank, otherwise false
     */
    public static boolean isNotBlank(String message) {
        return (message != null && !message.isEmpty());
    }
}
