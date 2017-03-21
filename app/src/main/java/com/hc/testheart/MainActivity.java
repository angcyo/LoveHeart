package com.hc.testheart;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hc.testheart.shimmer.Shimmer;
import com.hc.testheart.shimmer.ShimmerTextView;

public class MainActivity extends Activity {
    HeartView2 heartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        heartView = (HeartView2) findViewById(R.id.surfaceView);
//
        enableLayoutFullScreen();

        RevealTextView revealTextView = (RevealTextView) findViewById(R.id.reveal_text_view);
        revealTextView.setAnimationDuration(2000);
        revealTextView.setLoop(true);
        revealTextView.setAnimatedText("死胖子");

        new Shimmer().start(((ShimmerTextView) findViewById(R.id.shimmer_text_view)));
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //heartView.reDraw();
        }
        return super.onTouchEvent(event);
    }

    public void reDraw(View v) {

        heartView.reDraw();

    }

    protected void enableLayoutFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }
}
