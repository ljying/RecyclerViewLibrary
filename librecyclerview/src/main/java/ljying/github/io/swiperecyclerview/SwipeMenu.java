package ljying.github.io.swiperecyclerview;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 侧滑菜单
 *
 * @author Li Jianying
 * @version 2.0
 * @since 2016/7/22
 */
public class SwipeMenu {

    private SwipeMenuLayout mSwipeMenuLayout;

    private int mViewType;

    private List<SwipeMenuItem> mSwipeMenuItems;

    SwipeMenu(SwipeMenuLayout swipeMenuLayout, int viewType) {
        this.mSwipeMenuLayout = swipeMenuLayout;
        this.mViewType = viewType;
        this.mSwipeMenuItems = new ArrayList<>();
    }

    /**
     * 设置菜单打开后占据条目百分比
     *
     * @param openPercent  例如0.5F.
     */
    public void setOpenPercent(float openPercent) {
        openPercent = openPercent > 1 ? 1 : (openPercent < 0 ? 0 : openPercent);
        mSwipeMenuLayout.setOpenPercent(openPercent);
    }

    /**
     * 设置滑动的持续时间
     *
     * @param scrollerDuration 如 500.
     */
    public void setScrollerDuration(int scrollerDuration) {
        this.mSwipeMenuLayout.setScrollerDuration(scrollerDuration);
    }

    public void addMenuItem(SwipeMenuItem item) {
        mSwipeMenuItems.add(item);
    }

    public void removeMenuItem(SwipeMenuItem item) {
        mSwipeMenuItems.remove(item);
    }

    public void clearMenuItem() {
        if (mSwipeMenuItems != null)
            mSwipeMenuItems.clear();
    }

    public List<SwipeMenuItem> getMenuItems() {
        return mSwipeMenuItems;
    }

    public SwipeMenuItem getMenuItem(int index) {
        return mSwipeMenuItems.get(index);
    }

    public Context getContext() {
        return mSwipeMenuLayout.getContext();
    }

    public int getViewType() {
        return mViewType;
    }
}
