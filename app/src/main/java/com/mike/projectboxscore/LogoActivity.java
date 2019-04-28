package com.mike.projectboxscore;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class LogoActivity extends AppCompatActivity {

    private int mTotalDuration = 2000;
    private ImageView mImageLogo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.enter_page_animation);




        new Handler().postDelayed(() -> {

//            setLogoAnimation();

            new Handler().postDelayed(() -> {

//            startActivity(new Intent(this, MainActivity.class));
                finish();

            }, mTotalDuration / 3 * 2);

        }, mTotalDuration / 3);
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {}

    private void setLogoAnimation() {
//        int duration = mTotalDuration / 3 * 2;
//
//        RotateAnimation rotateAnimation = new RotateAnimation(0, 1800,
//                Animation.RELATIVE_TO_SELF, 0.5f,
//                Animation.RELATIVE_TO_SELF, 0.5f);
//        rotateAnimation.setDuration(duration);
//        rotateAnimation.setFillAfter(true);
//
//        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0,
//                0, getResources().getDimensionPixelSize(R.dimen.duration_logo_translate));
//        translateAnimation.setDuration(duration);
//        translateAnimation.setFillAfter(true);
//
//        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
//        alphaAnimation.setDuration(duration);
//        alphaAnimation.setFillAfter(true);
//
//        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0.5f, 1, 0.5f,
//                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        scaleAnimation.setDuration(duration);
//        scaleAnimation.setFillAfter(true);
//
//        AnimationSet animationSet = new AnimationSet(false);
//        animationSet.addAnimation(rotateAnimation);
//        animationSet.addAnimation(translateAnimation);
//        animationSet.addAnimation(alphaAnimation);
//        animationSet.addAnimation(scaleAnimation);
//        animationSet.setFillAfter(true);
//
//        findViewById(R.id.image_logo).startAnimation(animationSet);
    }
}
