package com.example.mwajeeh.animations;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.View;

public class CollapsingIndicatorBehaviour extends ViewOffsetBehavior<IconPageIndicator> {

    private WindowInsetsCompat lastInsets;

    public CollapsingIndicatorBehaviour() {
    }

    public CollapsingIndicatorBehaviour(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, IconPageIndicator child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, IconPageIndicator child, View dependency) {
        //keep child centered inside dependency respecting android:fitsSystemWindows="true"
        int systemWindowInsetTop = 0;
        if (lastInsets != null) {
            systemWindowInsetTop = lastInsets.getSystemWindowInsetTop();
        }
        int bottom = dependency.getBottom();
        float center = (bottom - systemWindowInsetTop) / 2F;
        float halfChild = child.getHeight() / 2F;
        setTopAndBottomOffset((int) (center + systemWindowInsetTop - halfChild));
        if (dependency instanceof AppBarLayout) {
            float totalScrollRange = ((AppBarLayout) dependency).getTotalScrollRange();
            child.collapse(-dependency.getTop(), totalScrollRange);
        }
        return true;
    }

    @NonNull
    @Override
    public WindowInsetsCompat onApplyWindowInsets(CoordinatorLayout coordinatorLayout, IconPageIndicator child, WindowInsetsCompat insets) {
        lastInsets = insets;
        return super.onApplyWindowInsets(coordinatorLayout, child, insets);
    }
}
