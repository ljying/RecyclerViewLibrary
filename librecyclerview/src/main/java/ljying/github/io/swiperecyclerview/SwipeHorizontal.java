package ljying.github.io.swiperecyclerview;

import android.view.View;
import android.widget.OverScroller;

/**
 * Description: 水平滑动状态类
 * @author Li Jianying
 * @version 2.0
 * @since 2016/7/22
 */
abstract class SwipeHorizontal {

    private int direction;
    private View menuView;
    protected Checker mChecker;

    public SwipeHorizontal(int direction, View menuView) {
        this.direction = direction;
        this.menuView = menuView;
        mChecker = new Checker();
    }

    public abstract boolean isMenuOpen(final int scrollX);

    public abstract boolean isMenuOpenNotEqual(final int scrollX);

    public abstract void autoOpenMenu(OverScroller scroller, int scrollX, int duration);

    public abstract void autoCloseMenu(OverScroller scroller, int scrollX, int duration);

    public abstract Checker checkXY(int x, int y);

    public abstract boolean isClickOnContentView(int contentViewWidth, float x);

    public int getDirection() {
        return direction;
    }

    public View getMenuView() {
        return menuView;
    }

    public int getMenuWidth() {
        return menuView.getWidth();
    }


    /**
     * 位置检测者
     */
    public static final class Checker {
        public int x;
        public int y;
        public boolean shouldResetSwipe;
    }

}
