package com.example.mwajeeh.animations;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * This behaviour is not necessary, its just there to prevent viewpager from scrolling during vertical scroll
 * of AppbarLayout
 */
public class DisableViewPagerTouchOnScrollBehaviour extends AppBarLayout.Behavior {
    private ViewPager pager;
    private int mTouchSlop = -1;
    private int totalYConsumed;

    public DisableViewPagerTouchOnScrollBehaviour() {
    }

    public DisableViewPagerTouchOnScrollBehaviour(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes) {
        boolean scroll = super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes);
        if (scroll && directTargetChild instanceof ViewPager) {
            pager = (ViewPager) directTargetChild;
            if (mTouchSlop < 0) {
                mTouchSlop = ViewConfiguration.get(parent.getContext()).getScaledTouchSlop();
            }
        }
        return scroll;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        if (pager != null && consumed[1] > 0) {
            totalYConsumed += consumed[1];
            if (totalYConsumed > mTouchSlop) {
                pager.requestDisallowInterceptTouchEvent(true);
            }
        }
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout abl, View target) {
        super.onStopNestedScroll(coordinatorLayout, abl, target);
        if (pager != null) {
            pager.requestDisallowInterceptTouchEvent(false);
            pager = null;
            totalYConsumed = 0;
        }
    }
}
