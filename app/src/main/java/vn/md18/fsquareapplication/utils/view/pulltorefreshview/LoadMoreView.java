package vn.md18.fsquareapplication.utils.view.pulltorefreshview;


import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import vn.md18.fsquareapplication.databinding.LayoutLoadMoreViewBinding;


public class LoadMoreView extends LinearLayout {

    private ValueAnimator animator;
    private final LayoutLoadMoreViewBinding mLayoutLoadMoreViewBinding;

    public LoadMoreView(Context context) {
        this(context, null);
    }

    public LoadMoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mLayoutLoadMoreViewBinding = LayoutLoadMoreViewBinding.inflate(LayoutInflater.from(getContext()), this, false);

        mLayoutLoadMoreViewBinding.textTip.setVisibility(GONE);
        addView(mLayoutLoadMoreViewBinding.getRoot());
    }

    public void setLoadMoreResource(int resId) {
        mLayoutLoadMoreViewBinding.imageLoadMore.setImageResource(resId);
    }

    public void startAnimation() {
        if (animator != null && animator.isStarted()) {
            return;
        }
        animator = ValueAnimator.ofFloat(mLayoutLoadMoreViewBinding.imageLoadMore.getRotation()
                , mLayoutLoadMoreViewBinding.imageLoadMore.getRotation() + 359);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLayoutLoadMoreViewBinding.imageLoadMore.setRotation((Float) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    public void stopAnimation() {
        if (animator != null) {
            animator.removeAllUpdateListeners();
            animator.cancel();
            animator.end();
            animator = null;
        }
        mLayoutLoadMoreViewBinding.imageLoadMore.clearAnimation();
    }

    public void loadMoreComplete(PullToRefreshRecyclerView refreshRecyclerView, boolean isAwaysShow) {
        if (!isAwaysShow) {
            setVisibility(GONE);
            stopAnimation();
        }
        refreshRecyclerView.scrollBy(0, -getHeight());
    }

    public void loadMoreEnd(PullToRefreshRecyclerView refreshRecyclerView) {
        setVisibility(GONE);
        stopAnimation();
        refreshRecyclerView.scrollBy(0, -getHeight());
    }

    public void loadMoreFail(final PullToRefreshRecyclerView refreshRecyclerView) {
        mLayoutLoadMoreViewBinding.textTip.setVisibility(VISIBLE);
        mLayoutLoadMoreViewBinding.imageLoadMore.setVisibility(INVISIBLE);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                setVisibility(GONE);
                stopAnimation();
                refreshRecyclerView.scrollBy(0, -getHeight());
                mLayoutLoadMoreViewBinding.textTip.setVisibility(INVISIBLE);
                mLayoutLoadMoreViewBinding.imageLoadMore.setVisibility(VISIBLE);
            }
        }, 800);
    }
}

