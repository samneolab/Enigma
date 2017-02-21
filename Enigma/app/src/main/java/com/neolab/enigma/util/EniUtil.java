package com.neolab.enigma.util;

import android.annotation.SuppressLint;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author LongHV.
 */

public class EniUtil {

    private static final String ENGLISH_LANGUAGE = "English";
    private static final String YEAR = "年";
    private static final String MONTH = "月";
    private static final String DAY = "日";
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    /**
     * Convert date from format yyyy-MM-dd HH:mm:ss to yyyy-MM-dd or yyyy年MM月dd日
     *
     * @param dateTime datetime with format yyyy-MM-dd HH:mm:ss
     * @return date yyyy-MM-dd or yyyy年MM月dd日
     */
    public static String getDateAnnouncementWithFormat(String dateTime) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = originalFormat.parse(dateTime);
            if (Locale.getDefault().getDisplayLanguage().equals(ENGLISH_LANGUAGE)) {
                return targetFormat.format(date);
            } else {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                StringBuilder sb = new StringBuilder();
                sb.append(calendar.get(Calendar.YEAR));
                sb.append(YEAR);
                sb.append(calendar.get(Calendar.MONTH));
                sb.append(MONTH);
                sb.append(calendar.get(Calendar.DAY_OF_MONTH));
                sb.append(DAY);
                return sb.toString();
            }
        } catch (ParseException ex) {
        }
        return "";
    }

    /**
     * Convert date from format yyyy-MM-dd HH:mm:ss to yyyy/MM/dd
     *
     * @param dateTime datetime with format yyyy-MM-dd HH:mm:ss
     * @return date with format yyyy/MM/dd
     */
    public static String getDateRequestPaymentWithFormat(String dateTime) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date;
        try {
            date = originalFormat.parse(dateTime);
            return targetFormat.format(date);
        } catch (ParseException ex) {
            return "";
        }
    }

    /**
     * The method is used to convert money to format xxx,yyy
     *
     * @param money the money
     * @return number format
     */
    public static String convertMoneyFormat(int money) {
        return String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(money));
    }

    /**
     * The method is used to check validate email address
     *
     * @param emailStr email
     * @return true if email address is validate, otherwise false
     */
    public static boolean isValidateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

}
