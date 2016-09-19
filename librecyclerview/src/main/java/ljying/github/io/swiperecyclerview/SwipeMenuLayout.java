package ljying.github.io.swiperecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import android.widget.TextView;

/**
 * Description: 滑动菜单布局控件
 * @author Li Jianying
 * @version 2.0
 * @since 2016/7/27
 */
public class SwipeMenuLayout extends FrameLayout implements SwipeSwitch {

    public static final int DEFAULT_SCROLLER_DURATION = 200;

    private int mLeftViewId = 0;
    private int mContentViewId = 0;
    private int mRightViewId = 0;

    private float mOpenPercent = 0.5f;
    private int mScrollerDuration = DEFAULT_SCROLLER_DURATION;

    private int mScaledTouchSlop;
    private int mLastX;
    private int mLastY;
    private int mDownX;
    private int mDownY;
    private View mContentView;
    private SwipeLeftHorizontal mSwipeLeftHorizontal;
    private SwipeRightHorizontal mSwipeRightHorizontal;
    private SwipeHorizontal mSwipeCurrentHorizontal;
    private boolean shouldResetSwipe;
    private boolean mDragging;
    private boolean swipeEnable = true;
    private OverScroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mScaledMinimumFlingVelocity;
    private int mScaledMaximumFlingVelocity;


    public SwipeMenuLayout(Context context) {
        this(context, null);
    }

    public SwipeMenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeMenuLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwipeMenuLayout);
        mLeftViewId = typedArray.getResourceId(R.styleable.SwipeMenuLayout_leftViewId, mLeftViewId);
        mContentViewId = typedArray.getResourceId(R.styleable.SwipeMenuLayout_contentViewId, mContentViewId);
        mRightViewId = typedArray.getResourceId(R.styleable.SwipeMenuLayout_rightViewId, mRightViewId);
        typedArray.recycle();

        ViewConfiguration mViewConfig = ViewConfiguration.get(getContext());
        mScaledTouchSlop = mViewConfig.getScaledTouchSlop();
        mScroller = new OverScroller(getContext());
        mScaledMinimumFlingVelocity = mViewConfig.getScaledMinimumFlingVelocity();
        mScaledMaximumFlingVelocity = mViewConfig.getScaledMaximumFlingVelocity();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (mLeftViewId != 0 && mSwipeLeftHorizontal == null) {
            View view = findViewById(mLeftViewId);
            mSwipeLeftHorizontal = new SwipeLeftHorizontal(view);
        }
        if (mRightViewId != 0 && mSwipeRightHorizontal == null) {
            View view = findViewById(mRightViewId);
            mSwipeRightHorizontal = new SwipeRightHorizontal(view);
        }
        if (mContentViewId != 0 && mContentView == null) {
            mContentView = findViewById(mContentViewId);
        } else {
            TextView errorView = new TextView(getContext());
            errorView.setClickable(true);
            errorView.setGravity(Gravity.CENTER);
            errorView.setTextSize(16);
            errorView.setText("You may not have set the ContentView.");
            mContentView = errorView;
            addView(mContentView);
        }
    }

    /**
     * Set whether open swipe. Default is true.
     *
     * @param swipeEnable true open, otherwise false.
     */
    public void setSwipeEnable(boolean swipeEnable) {
        this.swipeEnable = swipeEnable;
    }

    /**
     * Open the swipe function of the Item?
     *
     * @return open is true, otherwise is false.
     */
    public boolean isSwipeEnable() {
        return swipeEnable;
    }

    /**
     * Set a percentage.
     *
     * @param openPercent such as 0.5F.
     */
    public void setOpenPercent(float openPercent) {
        this.mOpenPercent = openPercent;
    }

    /**
     * The duration of the set.
     *
     * @param scrollerDuration such as 500.
     */
    public void setScrollerDuration(int scrollerDuration) {
        this.mScrollerDuration = scrollerDuration;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercepted = super.onInterceptTouchEvent(ev);
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownX = mLastX = (int) ev.getX();
                mDownY = (int) ev.getY();
                isIntercepted = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int disX = (int) (ev.getX() - mDownX);
                int disY = (int) (ev.getY() - mDownY);
                isIntercepted = Math.abs(disX) > mScaledTouchSlop && Math.abs(disX) > Math.abs(disY);
                break;
            case MotionEvent.ACTION_UP:
                isIntercepted = false;
                if (isMenuOpen() && mSwipeCurrentHorizontal.isClickOnContentView(getWidth(), ev.getX())) {
                    smoothCloseMenu();
                    isIntercepted = true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                isIntercepted = false;
                if (!mScroller.isFinished())
                    mScroller.abortAnimation();
                break;
        }
        return isIntercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mVelocityTracker == null) mVelocityTracker = VelocityTracker.obtain();
        mVelocityTracker.addMovement(ev);
        int dx;
        int dy;
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = (int) ev.getX();
                mLastY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isSwipeEnable()) break;
                int disX = (int) (mLastX - ev.getX());
                int disY = (int) (mLastY - ev.getY());
                if (!mDragging && Math.abs(disX) > mScaledTouchSlop && Math.abs(disX) > Math.abs(disY)) {
                    mDragging = true;
                }
                if (mDragging) {
                    if (mSwipeCurrentHorizontal == null || shouldResetSwipe) {
                        if (disX < 0) {
                            if (mSwipeLeftHorizontal != null)
                                mSwipeCurrentHorizontal = mSwipeLeftHorizontal;
                            else
                                mSwipeCurrentHorizontal = mSwipeRightHorizontal;
                        } else {
                            if (mSwipeRightHorizontal != null)
                                mSwipeCurrentHorizontal = mSwipeRightHorizontal;
                            else
                                mSwipeCurrentHorizontal = mSwipeLeftHorizontal;
                        }
                    }
                    scrollBy(disX, 0);
                    mLastX = (int) ev.getX();
                    mLastY = (int) ev.getY();
                    shouldResetSwipe = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                dx = (int) (mDownX - ev.getX());
                dy = (int) (mDownY - ev.getY());
                mDragging = false;
                mVelocityTracker.computeCurrentVelocity(1000, mScaledMaximumFlingVelocity);
                int velocityX = (int) mVelocityTracker.getXVelocity();
                int velocity = Math.abs(velocityX);
                if (velocity > mScaledMinimumFlingVelocity) {
                    if (mSwipeCurrentHorizontal != null) {
                        int duration = getSwipeDuration(ev, velocity);
                        if (mSwipeCurrentHorizontal instanceof SwipeRightHorizontal) {
                            if (velocityX < 0) {
                                smoothOpenMenu(duration);
                            } else {
                                smoothCloseMenu(duration);
                            }
                        } else {
                            if (velocityX > 0) {
                                smoothOpenMenu(duration);
                            } else {
                                smoothCloseMenu(duration);
                            }
                        }
                        ViewCompat.postInvalidateOnAnimation(this);
                    }
                } else {
                    judgeOpenClose(dx, dy);
                }
                mVelocityTracker.clear();
                mVelocityTracker.recycle();
                mVelocityTracker = null;
                if (Math.abs(mDownX - ev.getX()) > mScaledTouchSlop || Math.abs(mDownY - ev.getY()) > mScaledTouchSlop || isMenuOpen()) {
                    ev.setAction(MotionEvent.ACTION_CANCEL);
                    super.onTouchEvent(ev);
                    return true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                mDragging = false;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                } else {
                    dx = (int) (mDownX - ev.getX());
                    dy = (int) (mDownY - ev.getY());
                    judgeOpenClose(dx, dy);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * compute finish duration.
     *
     * @param ev       up event.
     * @param velocity velocity x.
     * @return finish duration.
     */
    private int getSwipeDuration(MotionEvent ev, int velocity) {
        int sx = getScrollX();
        int dx = (int) (ev.getX() - sx);
        final int width = mSwipeCurrentHorizontal.getMenuWidth();
        final int halfWidth = width / 2;
        final float distanceRatio = Math.min(1f, 1.0f * Math.abs(dx) / width);
        final float distance = halfWidth + halfWidth * distanceInfluenceForSnapDuration(distanceRatio);
        int duration;
        if (velocity > 0) {
            duration = 4 * Math.round(1000 * Math.abs(distance / velocity));
        } else {
            final float pageDelta = (float) Math.abs(dx) / width;
            duration = (int) ((pageDelta + 1) * 100);
        }
        duration = Math.min(duration, mScrollerDuration);
        return duration;
    }

    float distanceInfluenceForSnapDuration(float f) {
        f -= 0.5f; // center the values about 0.
        f *= 0.3f * Math.PI / 2.0f;
        return (float) Math.sin(f);
    }

    private void judgeOpenClose(int dx, int dy) {
        if (mSwipeCurrentHorizontal != null) {
            if (Math.abs(getScrollX()) >= (mSwipeCurrentHorizontal.getMenuView().getWidth() * mOpenPercent)) { // auto open
                if (Math.abs(dx) > mScaledTouchSlop || Math.abs(dy) > mScaledTouchSlop) { // swipe up
                    if (isMenuOpenNotEqual()) smoothCloseMenu();
                    else smoothOpenMenu();
                } else { // normal up
                    if (isMenuOpen()) smoothCloseMenu();
                    else smoothOpenMenu();
                }
            } else { // auto close
                smoothCloseMenu();
            }
        }
    }

    @Override
    public void scrollTo(int x, int y) {
        if (mSwipeCurrentHorizontal == null) {
            super.scrollTo(x, y);
        } else {
            SwipeHorizontal.Checker checker = mSwipeCurrentHorizontal.checkXY(x, y);
            shouldResetSwipe = checker.shouldResetSwipe;
            if (checker.x != getScrollX()) {
                super.scrollTo(checker.x, checker.y);
            }
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset() && mSwipeCurrentHorizontal != null) {
            if (mSwipeCurrentHorizontal instanceof SwipeRightHorizontal) {
                scrollTo(Math.abs(mScroller.getCurrX()), 0);
                invalidate();
            } else {
                scrollTo(-Math.abs(mScroller.getCurrX()), 0);
                invalidate();
            }
        }
    }

    @Override
    public boolean isMenuOpen() {
        return isLeftMenuOpen() || isRightMenuOpen();
    }

    @Override
    public boolean isLeftMenuOpen() {
        return mSwipeLeftHorizontal != null && mSwipeLeftHorizontal.isMenuOpen(getScrollX());
    }

    @Override
    public boolean isRightMenuOpen() {
        return mSwipeRightHorizontal != null && mSwipeRightHorizontal.isMenuOpen(getScrollX());
    }

    @Override
    public boolean isMenuOpenNotEqual() {
        return isLeftMenuOpenNotEqual() || isRightMenuOpenNotEqual();
    }

    @Override
    public boolean isLeftMenuOpenNotEqual() {
        return mSwipeLeftHorizontal != null && mSwipeLeftHorizontal.isMenuOpenNotEqual(getScrollX());
    }

    @Override
    public boolean isRightMenuOpenNotEqual() {
        return mSwipeRightHorizontal != null && mSwipeRightHorizontal.isMenuOpenNotEqual(getScrollX());
    }

    @Override
    public void smoothOpenLeftMenu() {
        smoothOpenLeftMenu(mScrollerDuration);
    }

    @Override
    public void smoothOpenLeftMenu(int duration) {
        if (mSwipeLeftHorizontal != null) {
            mSwipeCurrentHorizontal = mSwipeLeftHorizontal;
            smoothOpenMenu(duration);
        }
    }

    @Override
    public void smoothOpenRightMenu() {
        smoothOpenRightMenu(mScrollerDuration);
    }

    @Override
    public void smoothOpenRightMenu(int duration) {
        if (mSwipeRightHorizontal != null) {
            mSwipeCurrentHorizontal = mSwipeRightHorizontal;
            smoothOpenMenu(duration);
        }
    }

    @Override
    public void smoothOpenMenu() {
        smoothOpenMenu(mScrollerDuration);
    }

    private void smoothOpenMenu(int duration) {
        if (mSwipeCurrentHorizontal != null) {
            mSwipeCurrentHorizontal.autoOpenMenu(mScroller, getScrollX(), duration);
            invalidate();
        }
    }

    @Override
    public void smoothCloseLeftMenu() {
        if (mSwipeLeftHorizontal != null) {
            mSwipeCurrentHorizontal = mSwipeLeftHorizontal;
            smoothCloseMenu();
        }
    }

    @Override
    public void smoothCloseRightMenu() {
        if (mSwipeRightHorizontal != null) {
            mSwipeCurrentHorizontal = mSwipeRightHorizontal;
            smoothCloseMenu();
        }
    }

    @Override
    public void smoothCloseMenu(int duration) {
        if (mSwipeCurrentHorizontal != null) {
            mSwipeCurrentHorizontal.autoCloseMenu(mScroller, getScrollX(), duration);
            invalidate();
        }
    }

    @Override
    public void smoothCloseMenu() {
        smoothCloseMenu(mScrollerDuration);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int parentViewWidth = ViewCompat.getMeasuredWidthAndState(this);

        if (mContentView != null) {
            int contentViewWidth = ViewCompat.getMeasuredWidthAndState(mContentView);
            int contentViewHeight = ViewCompat.getMeasuredHeightAndState(mContentView);
            LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
            int start = getPaddingLeft();
            int top = getPaddingTop() + lp.topMargin;
            mContentView.layout(start, top, start + contentViewWidth, top + contentViewHeight);
        }

        if (mSwipeLeftHorizontal != null) {
            View leftMenu = mSwipeLeftHorizontal.getMenuView();
            int menuViewWidth = ViewCompat.getMeasuredWidthAndState(leftMenu);
            int menuViewHeight = ViewCompat.getMeasuredHeightAndState(leftMenu);
            LayoutParams lp = (LayoutParams) leftMenu.getLayoutParams();
            int top = getPaddingTop() + lp.topMargin;
            mSwipeLeftHorizontal.getMenuView().layout(-menuViewWidth, top, 0, top + menuViewHeight);
        }

        if (mSwipeRightHorizontal != null) {
            View rightMenu = mSwipeRightHorizontal.getMenuView();
            int menuViewWidth = ViewCompat.getMeasuredWidthAndState(rightMenu);
            int menuViewHeight = ViewCompat.getMeasuredHeightAndState(rightMenu);
            LayoutParams lp = (LayoutParams) rightMenu.getLayoutParams();
            int top = getPaddingTop() + lp.topMargin;
            mSwipeRightHorizontal.getMenuView().layout(parentViewWidth, top, parentViewWidth + menuViewWidth, top + menuViewHeight);
        }
    }

}
