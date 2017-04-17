package com.neolab.enigma.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.neolab.enigma.R;

/**
 * Custom EditText with highlight when user focus
 *
 * @author Pika.
 */
public class EniEditText extends EditText implements View.OnFocusChangeListener {

    /**
     * Constructor
     *
     * @param context Context
     */
    public EniEditText(Context context) {
        super(context);
        setOnFocusChangeListener(this);
    }

    public EniEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnFocusChangeListener(this);
    }

    public EniEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnFocusChangeListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            v.setBackgroundResource(R.drawable.edittext_payment_payment_focus);
        } else {
            v.setBackgroundResource(R.drawable.edittext_payment_payment_normal);
        }
    }
}
