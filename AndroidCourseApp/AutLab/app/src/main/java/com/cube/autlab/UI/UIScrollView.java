package com.cube.autlab.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;


public class UIScrollView extends ScrollView {

    public UIScrollView(Context context) {
        this(context, null);
    }

    public UIScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UIScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (((SelectionTextView) getChildAt(0)).isAllowSelectText() == true) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

}
