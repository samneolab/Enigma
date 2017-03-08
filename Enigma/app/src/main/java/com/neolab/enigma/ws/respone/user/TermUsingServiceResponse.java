package com.neolab.enigma.ws.respone.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Terms and condition using service response
 *
 * @author Pika.
 */
public class TermUsingServiceResponse {

    @SerializedName("status_code")
    public int code;
    @Expose
    public String message;
    @Expose
    public TermUsingService data;

    public class TermUsingService {

        @SerializedName("page_content")
        public String pageContent;
    }

}
