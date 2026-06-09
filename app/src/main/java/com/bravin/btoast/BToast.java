package com.bravin.btoast;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

public class BToast {

    public static final int DURATION_SHORT = 0;
    public static final int DURATION_LONG = 1;
    public static final int ANIMATION_GRAVITY_TOP = 0;
    public static final int ANIMATION_GRAVITY_BOTTOM = 1;
    public static final int ANIMATION_GRAVITY_LEFT = 2;
    public static final int LAYOUT_GRAVITY_BOTTOM = 0;
    public static final int LAYOUT_GRAVITY_RIGHT = 1;

    private static final int COLOR_SUCCESS = 0xFF00AA74;
    private static final int COLOR_ERROR = 0xFFFF4444;
    private static final int COLOR_INFO = 0xFF33B5E5;
    private static final int COLOR_NORMAL = 0xFF666666;

    private Context context;
    private CharSequence text;
    private int duration = Toast.LENGTH_SHORT;

    private BToast(Context context) {
        this.context = context;
    }

    public static BToast success(Context context) {
        return new BToast(context);
    }

    public static BToast error(Context context) {
        return new BToast(context);
    }

    public static BToast info(Context context) {
        return new BToast(context);
    }

    public static BToast normal(Context context) {
        return new BToast(context);
    }

    public BToast text(CharSequence text) {
        this.text = text;
        return this;
    }

    public BToast text(int resId) {
        this.text = context.getString(resId);
        return this;
    }

    public BToast duration(int duration) {
        this.duration = duration;
        return this;
    }

    public BToast animate(boolean animate) {
        return this;
    }

    public BToast animationGravity(int gravity) {
        return this;
    }

    public BToast target(View target) {
        return this;
    }

    public BToast layoutGravity(int gravity) {
        return this;
    }

    public BToast tag(int tag) {
        return this;
    }

    public void show() {
        Toast toast = Toast.makeText(context, text, duration == DURATION_LONG ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static class Config {
        private static Config instance = new Config();

        public static Config getInstance() {
            return instance;
        }

        public Config setAnimationDuration(int ms) { return this; }
        public Config setAnimationGravity(int gravity) { return this; }
        public Config setDuration(int duration) { return this; }
        public Config setInfoColor(int color) { return this; }
        public Config setSuccessColor(int color) { return this; }
        public Config setErrorColor(int color) { return this; }
        public Config setTextColor(int color) { return this; }
        public Config setTextSize(int sp) { return this; }
        public Config apply(Context context) { return this; }
    }
}
