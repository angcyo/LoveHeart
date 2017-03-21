package com.hc.testheart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

/**
 * https://github.com/ANPez/RevealTextView
 */
public final class RevealTextView extends AppCompatTextView implements Runnable, ValueAnimator.AnimatorUpdateListener {
    private int animationDuration = 300;
    private String text;
    private int red;
    private int green;
    private int blue;
    private double[] alphas;

    /**
     * 无限循环
     */
    private boolean isLoop = false;

    public RevealTextView(Context context) {
        super(context);
        init(null);
    }

    public RevealTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context.getTheme().obtainStyledAttributes(attrs, R.styleable.RevealTextView, 0, 0));
    }

    public RevealTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context.getTheme().obtainStyledAttributes(attrs, R.styleable.RevealTextView, 0, 0));
    }

    protected void init(TypedArray attrs) {
        try {
            animationDuration = attrs.getInteger(R.styleable.RevealTextView_rtv_duration, animationDuration);
            text = attrs.getString(R.styleable.RevealTextView_android_text);
        } finally {
            attrs.recycle();
        }

        setAnimatedText(text);
    }

    @Override
    public void run() {
        final int color = getCurrentTextColor();

        red = Color.red(color);
        green = Color.green(color);
        blue = Color.blue(color);

        ValueAnimator animator = ValueAnimator.ofFloat(0f, 2f);
        animator.setDuration(animationDuration);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(this);
        if (isLoop) {
            animator.setRepeatMode(ValueAnimator.REVERSE);
            animator.setRepeatCount(ValueAnimator.INFINITE);
        }
        animator.start();
    }

    protected int clamp(double value) {
        return (int) (255f * Math.min(Math.max(value, 0f), 1f));
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        final float value = (float) valueAnimator.getAnimatedValue();
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        for (int i = 0; i < text.length(); i++) {
            builder.setSpan(new ForegroundColorSpan(Color.argb(clamp(value + alphas[i]), red, green, blue)), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        setText(builder);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }

    public void setLoop(boolean loop) {
        isLoop = loop;
    }

    /**************
     *** Public ***
     **************/

    /**
     * Replay the animation.
     */
    public void replayAnimation() {
        if (null != text) {
            post(this);
        }
    }

    public void setAnimationDuration(int animationDuration) {
        this.animationDuration = animationDuration;
    }

    /**
     * Change the text and replay the animation.
     *
     * @param text Text to be shown.
     */
    public void setAnimatedText(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        this.text = text;
        alphas = new double[text.length()];
        for (int i = 0; i < text.length(); i++) {
            alphas[i] = Math.random() - 1.0f;
        }

        setText(text);

        replayAnimation();
    }
}