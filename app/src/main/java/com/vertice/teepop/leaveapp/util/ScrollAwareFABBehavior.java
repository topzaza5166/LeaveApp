package com.vertice.teepop.leaveapp.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

/**
 * Created by VerDev06 on 12/29/2017.
 */

public class ScrollAwareFABBehavior extends FloatingActionButton.Behavior {

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private boolean mIsAnimatingOut = false;

    public ScrollAwareFABBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull final FloatingActionButton child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);

        if (dyConsumed > 0 && !this.mIsAnimatingOut && child.getVisibility() == View.VISIBLE) {
            // User scrolled down and the FAB is currently visible -> hide the FAB
            animateOut(child);
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            // User scrolled up and the FAB is currently not visible -> show the FAB
            animateIn(child);
        }
    }

    // Same animation that FloatingActionButton.Behavior uses to hide the FAB when the AppBarLayout exits
    private void animateOut(final FloatingActionButton button) {
        ViewCompat.animate(button).scaleX(0.0F).scaleY(0.0F).alpha(0.0F).setInterpolator(INTERPOLATOR).withLayer()
                .setListener(new ViewPropertyAnimatorListener() {
                    public void onAnimationStart(View view) {
                        ScrollAwareFABBehavior.this.mIsAnimatingOut = true;
                    }

                    public void onAnimationCancel(View view) {
                        ScrollAwareFABBehavior.this.mIsAnimatingOut = false;
                    }

                    public void onAnimationEnd(View view) {
                        ScrollAwareFABBehavior.this.mIsAnimatingOut = false;
                        view.setVisibility(View.INVISIBLE);
                    }
                }).start();

    }

    // Same animation that FloatingActionButton.Behavior uses to show the FAB when the AppBarLayout enters
    private void animateIn(FloatingActionButton button) {
        button.setVisibility(View.VISIBLE);
        ViewCompat.animate(button).scaleX(1.0F).scaleY(1.0F).alpha(1.0F)
                .setInterpolator(INTERPOLATOR).withLayer().setListener(null)
                .start();

    }
}

//    // Same animation that FloatingActionButton.Behavior uses to hide the FAB when the AppBarLayout exits
//    private void animateOut(final FloatingActionButton button) {
//        if (Build.VERSION.SDK_INT >= 14) {
//            ViewCompat.animate(button).scaleX(0.0F).scaleY(0.0F).alpha(0.0F).setInterpolator(INTERPOLATOR).withLayer()
//                    .setListener(new ViewPropertyAnimatorListener() {
//                        public void onAnimationStart(View view) {
//                            ScrollAwareFABBehavior.this.mIsAnimatingOut = true;
//                        }
//
//                        public void onAnimationCancel(View view) {
//                            ScrollAwareFABBehavior.this.mIsAnimatingOut = false;
//                        }
//
//                        public void onAnimationEnd(View view) {
//                            ScrollAwareFABBehavior.this.mIsAnimatingOut = false;
//                            view.setVisibility(View.INVISIBLE);
//                        }
//                    }).start();
//        } else {
//            Animation anim = AnimationUtils.loadAnimation(button.getContext(), android.R.anim.fade_out);
//            anim.setInterpolator(INTERPOLATOR);
//            anim.setDuration(200L);
//            anim.setAnimationListener(new Animation.AnimationListener() {
//                public void onAnimationStart(Animation animation) {
//                    ScrollAwareFABBehavior.this.mIsAnimatingOut = true;
//                }
//
//                public void onAnimationEnd(Animation animation) {
//                    ScrollAwareFABBehavior.this.mIsAnimatingOut = false;
//                    button.setVisibility(View.INVISIBLE);
//                }
//
//                @Override
//                public void onAnimationRepeat(final Animation animation) {
//                }
//            });
//            button.startAnimation(anim);
//        }
//    }
//
//    // Same animation that FloatingActionButton.Behavior uses to show the FAB when the AppBarLayout enters
//    private void animateIn(FloatingActionButton button) {
//        button.setVisibility(View.VISIBLE);
//        if (Build.VERSION.SDK_INT >= 14) {
//            ViewCompat.animate(button).scaleX(1.0F).scaleY(1.0F).alpha(1.0F)
//                    .setInterpolator(INTERPOLATOR).withLayer().setListener(null)
//                    .start();
//        } else {
//            Animation anim = AnimationUtils.loadAnimation(button.getContext(), android.R.anim.fade_in);
//            anim.setDuration(200L);
//            anim.setInterpolator(INTERPOLATOR);
//            button.startAnimation(anim);
//        }
//    }


