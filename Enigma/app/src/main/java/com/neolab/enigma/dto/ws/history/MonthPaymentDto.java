package com.neolab.enigma.dto.ws.history;

import com.google.gson.annotations.Expose;

/**
 * Month request payment list
 *
 * @author LongHV.
 */
public class MonthPaymentDto {

    @Expose
    public int year;
    @Expose
    public int month;

}
