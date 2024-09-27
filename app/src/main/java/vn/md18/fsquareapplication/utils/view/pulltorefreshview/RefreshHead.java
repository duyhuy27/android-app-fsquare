package vn.md18.fsquareapplication.utils.view.pulltorefreshview;


import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import java.util.Date;

import vn.md18.fsquareapplication.R;
import vn.md18.fsquareapplication.databinding.LayoutRefreshHeadViewBinding;


public class RefreshHead extends LinearLayout {

    public static final int STATE_NORMAL = 0;
    public static final int STATE_RELEASE_TO_REFRESH = 1;
    public static final int STATE_REFRESHING = 2;
    public static final int STATE_DONE = 3;
    public static final int STATE_FAIL = 4;

    private int refreshState = 0;
    /**
     * Minimum height to trigger refresh operation
     */
    private int refreshLimitHeight;

    private final LayoutRefreshHeadViewBinding mLayoutRefreshHeadViewBinding;

    private PullToRefreshListener pullToRefreshListener;
    /**
     * Last refresh time
     */
    private Date lastRefreshDate;
    private boolean showLastRefreshTime;
    private ValueAnimator animator;

    public RefreshHead(Context context) {
        this(context, null);
    }

    public RefreshHead(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshHead(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        refreshLimitHeight = getScreenHeight() / 8;

        mLayoutRefreshHeadViewBinding = LayoutRefreshHeadViewBinding.inflate(LayoutInflater.from(getContext()), this, false);

        addView(mLayoutRefreshHeadViewBinding.getRoot(), new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        setGravity(Gravity.BOTTOM);


    }

    /**
     * Set the height of the trigger refresh
     *
     * @param height
     */
    public void setRefreshLimitHeight(int height) {
        refreshLimitHeight = height;
    }

    public void setRefreshArrowResource(int resId) {
        mLayoutRefreshHeadViewBinding.imageArrow.setImageResource(resId);
    }

    public void setRefreshingResource(int resId) {
        mLayoutRefreshHeadViewBinding.imageRefreshing.setImageResource(resId);
    }

    public void displayLastRefreshTime(boolean display) {
        showLastRefreshTime = display;
    }

    public void onMove(int move) {
        int newVisibleHeight = getVisibleHeight() + move;
        if (newVisibleHeight >= refreshLimitHeight && refreshState != STATE_RELEASE_TO_REFRESH) {
            if (mLayoutRefreshHeadViewBinding.imageArrow.getVisibility() != VISIBLE)
                mLayoutRefreshHeadViewBinding.imageArrow.setVisibility(VISIBLE);
            refreshState = STATE_RELEASE_TO_REFRESH;
            mLayoutRefreshHeadViewBinding.textTip.setText(R.string.pull_refresh_view_end);
            rotationAnimator(180f);
        }
        if (newVisibleHeight < refreshLimitHeight && refreshState != STATE_NORMAL) {
            if (mLayoutRefreshHeadViewBinding.imageArrow.getVisibility() != VISIBLE)
                mLayoutRefreshHeadViewBinding.imageArrow.setVisibility(VISIBLE);
            refreshState = STATE_NORMAL;
            mLayoutRefreshHeadViewBinding.textTip.setText(R.string.pull_refresh_view_start);
            if (lastRefreshDate == null) {
                mLayoutRefreshHeadViewBinding.textLastRefreshTime.setVisibility(GONE);
            } else {
                if (showLastRefreshTime) {
                    mLayoutRefreshHeadViewBinding.textLastRefreshTime.setVisibility(VISIBLE);
                    mLayoutRefreshHeadViewBinding.textLastRefreshTime.setText(friendlyTime(lastRefreshDate));
                }
            }
            rotationAnimator(0);
        }
        setVisibleHeight(getVisibleHeight() + move);

    }

    /**
     * After the touch event is over, check whether it needs to be refreshed
     */
    public void checkRefresh() {
        if (getVisibleHeight() <= 0) return;
        if (refreshState == STATE_NORMAL) {
            smoothScrollTo(0);
            refreshState = STATE_DONE;
        } else if (refreshState == STATE_RELEASE_TO_REFRESH) {
            setState(STATE_REFRESHING);
        }
    }

    public void setRefreshing() {
        refreshState = STATE_REFRESHING;
        mLayoutRefreshHeadViewBinding.imageArrow.setVisibility(GONE);
        mLayoutRefreshHeadViewBinding.textLastRefreshTime.setVisibility(GONE);
        mLayoutRefreshHeadViewBinding.imageRefreshing.setVisibility(VISIBLE);
        startRefreshingAnimation();
        mLayoutRefreshHeadViewBinding.textTip.setText(R.string.pull_refresh_view_refreshing);
        smoothScrollTo(getScreenHeight() / 9);
        if (pullToRefreshListener != null) {
            pullToRefreshListener.onRefresh();
        }
    }

    public void startRefreshingAnimation() {
        animator = ValueAnimator.ofFloat(mLayoutRefreshHeadViewBinding.imageRefreshing.getRotation(), mLayoutRefreshHeadViewBinding.imageRefreshing.getRotation() + 359);
        animator.setDuration(1000).start();
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLayoutRefreshHeadViewBinding.imageRefreshing.setRotation((Float) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    public void setState(int state) {
        if (refreshState == state) return;
        switch (state) {
            case STATE_REFRESHING://Switch to refresh state
                refreshState = state;
                mLayoutRefreshHeadViewBinding.imageArrow.setVisibility(GONE);
                mLayoutRefreshHeadViewBinding.textLastRefreshTime.setVisibility(GONE);
                mLayoutRefreshHeadViewBinding.imageRefreshing.setVisibility(VISIBLE);
                startRefreshingAnimation();
                mLayoutRefreshHeadViewBinding.textTip.setText(R.string.pull_refresh_view_refreshing);
                smoothScrollTo(getScreenHeight() / 9);

                if (pullToRefreshListener != null) {
                    pullToRefreshListener.onRefresh();
                }
                break;
            case STATE_DONE://Switch to the state where the refresh is complete or the load is successful
                if (refreshState == STATE_REFRESHING) {
                    refreshState = state;
                    animator.end();
                    mLayoutRefreshHeadViewBinding.imageRefreshing.setVisibility(GONE);
                    mLayoutRefreshHeadViewBinding.textTip.setText(R.string.pull_refresh_view_refresh_success);
                    lastRefreshDate = new Date();
                    smoothScrollTo(0);
                }
                break;
            case STATE_FAIL://Switch to refresh failed or load failed state
                if (refreshState == STATE_REFRESHING) {
                    refreshState = state;
                    mLayoutRefreshHeadViewBinding.imageRefreshing.setVisibility(GONE);
                    mLayoutRefreshHeadViewBinding.imageArrow.setVisibility(VISIBLE);
                    mLayoutRefreshHeadViewBinding.textTip.setText(R.string.pull_refresh_view_refresh_fail);
                    animator.end();
                    smoothScrollTo(0);
                }
                break;
        }
    }

    public int getRefreshState() {
        return refreshState;
    }

    public void setRefreshComplete() {
        setState(STATE_DONE);
    }

    public void setRefreshFail() {
        setState(STATE_FAIL);
    }

    public int getVisibleHeight() {
        LayoutParams lp = (LayoutParams) mLayoutRefreshHeadViewBinding.getRoot().getLayoutParams();
        return lp.height;
    }

    public void setVisibleHeight(int height) {
        if (height < 0) height = 0;
        LayoutParams lp = (LayoutParams) mLayoutRefreshHeadViewBinding.getRoot().getLayoutParams();
        lp.height = height;
        mLayoutRefreshHeadViewBinding.getRoot().setLayoutParams(lp);
    }

    public void setPullToRefreshListener(PullToRefreshListener pullToRefreshListener) {
        this.pullToRefreshListener = pullToRefreshListener;
    }

    private void rotationAnimator(float rotation) {
        ValueAnimator animator = ValueAnimator.ofFloat(mLayoutRefreshHeadViewBinding.imageArrow.getRotation(), rotation);
        animator.setDuration(200).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLayoutRefreshHeadViewBinding.imageArrow.setRotation((Float) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    private void smoothScrollTo(int destHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(getVisibleHeight(), destHeight);
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setVisibleHeight((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }


    /**
     * Get screen height
     *
     * @return
     */
    private int getScreenHeight() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }


    public static String friendlyTime(Date time) {
        //Get the number of seconds from the current time
        int ct = (int) ((System.currentTimeMillis() - time.getTime()) / 1000);

        if (ct == 0) {
            return "just recently";
        }

        if (ct > 0 && ct < 60) {
            return ct + "seconds ago";
        }

        if (ct >= 60 && ct < 3600) {
            return Math.max(ct / 60, 1) + "minutes ago";
        }
        if (ct >= 3600 && ct < 86400)
            return ct / 3600 + "hours ago";
        if (ct >= 86400 && ct < 2592000) { //86400 * 30
            int day = ct / 86400;
            return day + "days ago";
        }
        if (ct >= 2592000 && ct < 31104000) { //86400 * 30
            return ct / 2592000 + "months ago";
        }
        return ct / 31104000 + "years ago";
    }

}

