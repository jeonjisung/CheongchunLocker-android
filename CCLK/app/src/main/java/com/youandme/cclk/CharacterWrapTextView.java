package com.youandme.cclk;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class CharacterWrapTextView extends TextView {
    public CharacterWrapTextView(Context context) {
        super(context);
    }

    public CharacterWrapTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CharacterWrapTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override public void setText(CharSequence text, TextView.BufferType type) {
        super.setText(text.toString().replace(" ", "\u00A0"), type);
    }
}