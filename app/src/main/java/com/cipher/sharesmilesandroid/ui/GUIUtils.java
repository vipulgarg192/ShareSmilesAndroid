package com.cipher.sharesmilesandroid.ui;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;


import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.ColorRes;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import com.cipher.sharesmilesandroid.R;


public class GUIUtils {

    public static void animateRevealHide(final Context ctx, final View view, @ColorRes int color,
                                         final int finalRadius, OnRevealAnimationListener listener) {
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;
        int initialRadius = view.getWidth();

        Animator anim =
                ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, finalRadius);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setBackgroundColor(ctx.getResources().getColor(color));
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
                listener.onRevealHide();
            }
        });
        anim.setDuration(ctx.getResources().getInteger(Integer.parseInt("1000")));
        anim.start();
    }

    public static void animateRevealShow(final Context ctx, final View view, final int startRadius,
                                         @ColorRes int color, int x, int y, OnRevealAnimationListener listener) {
        float finalRadius = (float) Math.hypot(view.getWidth(), view.getHeight());

        Animator anim =
                ViewAnimationUtils.createCircularReveal(view, x, y, startRadius, finalRadius);
        anim.setDuration(ctx.getResources().getInteger(Integer.parseInt("1000")));
        anim.setStartDelay(100);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setBackgroundColor(ctx.getResources().getColor(color));
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.VISIBLE);
                listener.onRevealShow();
            }
        });
        anim.start();
    }
    public static void startEnterTransitionSlideUp(Context ctx, View... views) {
        Animation slideAnimationUp = AnimationUtils.loadAnimation(ctx, R.anim.abc_slide_in_bottom);
        slideAnimationUp.setDuration(300);
        slideAnimationUp.setInterpolator(new LinearOutSlowInInterpolator());
        slideAnimationUp.setAnimationListener(getShowAnimationListener(null, views));
        startAnimations(slideAnimationUp, views);
    }

    public static void startEnterTransitionSlideDown(Context ctx, View... views) {
        Animation slideAnimationDown = AnimationUtils.loadAnimation(ctx, R.anim.abc_slide_in_top);
        slideAnimationDown.setDuration(300);
        slideAnimationDown.setInterpolator(new LinearOutSlowInInterpolator());
        slideAnimationDown.setAnimationListener(getShowAnimationListener(null, views));
        startAnimations(slideAnimationDown, views);
    }







    private static Animation.AnimationListener getGoneAnimationListener(OnReturnAnimationFinished listener, View... views) {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                for(View v : views) {
                    v.setVisibility(View.INVISIBLE);
                }
                if(listener != null) {
                    listener.onAnimationFinished();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
    }

    private static Animation.AnimationListener getShowAnimationListener(OnReturnAnimationFinished listener, View... views) {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                for(View v : views) {
                    v.setVisibility(View.VISIBLE);
                }
                if(listener != null) {
                    listener.onAnimationFinished();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
    }

    private static void startAnimations(Animation animation, View... views) {
        for(View v : views) {
            v.startAnimation(animation);
        }
    }

    public interface OnReturnAnimationFinished {
        void onAnimationFinished();
    }
}
