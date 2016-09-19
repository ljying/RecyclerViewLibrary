package ljying.github.io.swiperecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import ljying.github.io.swiperecyclerview.touch.DefaultItemTouchHelper;
import ljying.github.io.swiperecyclerview.touch.OnItemMoveListener;
import ljying.github.io.swiperecyclerview.touch.OnItemMovementListener;

/**
 * Description: 滑动菜单RecyclerView
 * @author Li Jianying
 * @version 2.0
 * @since 2016/7/27
 */
public class SwipeMenuRecyclerView extends RecyclerView {

    /**
     * 左侧方向
     */
    public static final int LEFT_DIRECTION = 1;
    /**
     * 右侧方向
     */
    public static final int RIGHT_DIRECTION = -1;

    /**
     * 不合法位置
     */
    private static final int INVALID_POSITION = -1;

    protected ViewConfiguration mViewConfig;
    protected SwipeMenuLayout mOldSwipedLayout;
    protected int mOldTouchedPosition = INVALID_POSITION;

    private int mDownX;
    private int mDownY;

    private SwipeMenuCreator mSwipeMenuCreator;
    private OnSwipeMenuItemClickListener mSwipeMenuItemClickListener;
    private DefaultItemTouchHelper mDefaultItemTouchHelper;

    public SwipeMenuRecyclerView(Context context) {
        this(context, null);
    }

    public SwipeMenuRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeMenuRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mViewConfig = ViewConfiguration.get(getContext());
    }

    private void initializeItemTouchHelper() {
        if (mDefaultItemTouchHelper == null) {
            mDefaultItemTouchHelper = new DefaultItemTouchHelper();
            mDefaultItemTouchHelper.attachToRecyclerView(this);
        }
    }

    public void setOnItemMoveListener(OnItemMoveListener onItemMoveListener) {
        initializeItemTouchHelper();
        mDefaultItemTouchHelper.setOnItemMoveListener(onItemMoveListener);
    }

    public void setOnItemMovementListener(OnItemMovementListener onItemMovementListener) {
        initializeItemTouchHelper();
        mDefaultItemTouchHelper.setOnItemMovementListener(onItemMovementListener);
    }


    public void setLongPressDragEnabled(boolean canDrag) {
        initializeItemTouchHelper();
        mDefaultItemTouchHelper.setLongPressDragEnabled(canDrag);
    }

    public boolean isLongPressDragEnabled() {
        initializeItemTouchHelper();
        return this.mDefaultItemTouchHelper.isLongPressDragEnabled();
    }


    public void setItemViewSwipeEnabled(boolean canSwipe) {
        initializeItemTouchHelper();
        mDefaultItemTouchHelper.setItemViewSwipeEnabled(canSwipe);
    }

    public boolean isItemViewSwipeEnabled() {
        initializeItemTouchHelper();
        return this.mDefaultItemTouchHelper.isItemViewSwipeEnabled();
    }

    public void startDrag(RecyclerView.ViewHolder viewHolder) {
        initializeItemTouchHelper();
        mDefaultItemTouchHelper.startDrag(viewHolder);
    }
    public void startSwipe(RecyclerView.ViewHolder viewHolder) {
        initializeItemTouchHelper();
        mDefaultItemTouchHelper.startSwipe(viewHolder);
    }

    public void setSwipeMenuCreator(SwipeMenuCreator swipeMenuCreator) {
        this.mSwipeMenuCreator = swipeMenuCreator;
    }

    public void setSwipeMenuItemClickListener(OnSwipeMenuItemClickListener swipeMenuItemClickListener) {
        this.mSwipeMenuItemClickListener = swipeMenuItemClickListener;
    }

    private SwipeMenuCreator mDefaultMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            if (mSwipeMenuCreator != null) {
                mSwipeMenuCreator.onCreateMenu(swipeLeftMenu, swipeRightMenu, viewType);
            }
        }
    };

    /**
     * 默认滑动菜单点击监听
     */
    private OnSwipeMenuItemClickListener mDefaultMenuItemClickListener = new OnSwipeMenuItemClickListener() {
        @Override
        public void onMenuItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            if (mSwipeMenuItemClickListener != null) {
                mSwipeMenuItemClickListener.onMenuItemClick(closeable, adapterPosition, menuPosition, direction);
            }
        }
    };

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter instanceof SwipeMenuAdapter) {
            SwipeMenuAdapter menuAdapter = (SwipeMenuAdapter) adapter;
            menuAdapter.setSwipeMenuCreator(mDefaultMenuCreator);
            menuAdapter.setSwipeMenuItemClickListener(mDefaultMenuItemClickListener);
        }
        super.setAdapter(adapter);
    }

    public void openLeftMenu(int position) {
        openMenu(position, LEFT_DIRECTION, SwipeMenuLayout.DEFAULT_SCROLLER_DURATION);
    }

    public void openLeftMenu(int position, int duration) {
        openMenu(position, LEFT_DIRECTION, duration);
    }

    public void openRightMenu(int position) {
        openMenu(position, RIGHT_DIRECTION, SwipeMenuLayout.DEFAULT_SCROLLER_DURATION);
    }

    public void openRightMenu(int position, int duration) {
        openMenu(position, RIGHT_DIRECTION, duration);
    }

    public void openMenu(int position, int direction, int duration) {
        if (mOldSwipedLayout != null) {
            if (mOldSwipedLayout.isMenuOpen()) {
                mOldSwipedLayout.smoothCloseMenu();
            }
        }
        ViewHolder vh = findViewHolderForAdapterPosition(position);
        if (vh != null) {
            View itemView = getSwipeMenuView(vh.itemView);
            if (itemView != null && itemView instanceof SwipeMenuLayout) {
                mOldSwipedLayout = (SwipeMenuLayout) itemView;
                if (direction == RIGHT_DIRECTION) {
                    mOldTouchedPosition = position;
                    mOldSwipedLayout.smoothOpenRightMenu(duration);
                } else if (direction == LEFT_DIRECTION) {
                    mOldTouchedPosition = position;
                    mOldSwipedLayout.smoothOpenLeftMenu(duration);
                }
            }
        }
    }

    private View getSwipeMenuView(View itemView) {
        if (itemView instanceof SwipeMenuLayout) return itemView;
        List<View> unvisited = new ArrayList<>();
        unvisited.add(itemView);
        while (!unvisited.isEmpty()) {
            View child = unvisited.remove(0);
            if (!(child instanceof ViewGroup)) { // view
                continue;
            }
            if (child instanceof SwipeMenuLayout) return child;
            ViewGroup group = (ViewGroup) child;
            final int childCount = group.getChildCount();
            for (int i = 0; i < childCount; i++) unvisited.add(group.getChildAt(i));
        }
        return itemView;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (e.getPointerCount() > 1) return true;
        boolean isIntercepted = super.onInterceptTouchEvent(e);
        int action = e.getAction();
        int x = (int) e.getX();
        int y = (int) e.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownX = x;
                mDownY = y;
                isIntercepted = false;

                int touchingPosition = getChildAdapterPosition(findChildViewUnder(x, y));
                if (touchingPosition != mOldTouchedPosition && mOldSwipedLayout != null && mOldSwipedLayout.isMenuOpen()) {
                    mOldSwipedLayout.smoothCloseMenu();
                    isIntercepted = true;
                }

                if (isIntercepted) {
                    mOldSwipedLayout = null;
                    mOldTouchedPosition = INVALID_POSITION;
                } else {
                    ViewHolder vh = findViewHolderForAdapterPosition(touchingPosition);
                    if (vh != null) {
                        View itemView = getSwipeMenuView(vh.itemView);
                        if (itemView != null && itemView instanceof SwipeMenuLayout) {
                            mOldSwipedLayout = (SwipeMenuLayout) itemView;
                            mOldTouchedPosition = touchingPosition;
                        }
                    }
                }
                break;
            // They are sensitive to retain sliding and inertia.
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isIntercepted = handleUnDown(x, y, isIntercepted);
                break;
        }
        return isIntercepted;
    }

    private boolean handleUnDown(int x, int y, boolean defaultValue) {
        int disX = mDownX - x;
        int disY = mDownY - y;
        // swipe
        if (Math.abs(disX) > mViewConfig.getScaledTouchSlop())
            defaultValue = false;
        // click
        if (Math.abs(disY) < mViewConfig.getScaledTouchSlop() && Math.abs(disX) < mViewConfig.getScaledTouchSlop())
            defaultValue = false;
        return defaultValue;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                if (mOldSwipedLayout != null && mOldSwipedLayout.isMenuOpen()) {
                    mOldSwipedLayout.smoothCloseMenu();
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.onTouchEvent(e);
    }
}
