package ljying.github.io.swiperecyclerview.touch;

import android.support.v7.widget.helper.CompatItemTouchHelper;

/**
 * Description:  默认条目touch处理助手类
 *
 * @author Li Jianying
 * @version 2.0
 * @since 2016/4/19
 */
public class DefaultItemTouchHelper extends CompatItemTouchHelper {

    private DefaultItemTouchHelperCallback mDefaultItemTouchHelperCallback;

    /**
     * 默认构造方法
     */
    public DefaultItemTouchHelper() {
        this(new DefaultItemTouchHelperCallback());
    }

    /**
     * @param callback ItemTouchHelper行为回掉.
     */
    private DefaultItemTouchHelper(DefaultItemTouchHelperCallback callback) {
        super(callback);
        mDefaultItemTouchHelperCallback = (DefaultItemTouchHelperCallback) getCallback();
    }

    /**
     * 设置 OnItemMoveListener.
     *
     * @param onItemMoveListener {@link OnItemMoveListener}.
     */
    public void setOnItemMoveListener(OnItemMoveListener onItemMoveListener) {
        mDefaultItemTouchHelperCallback.setOnItemMoveListener(onItemMoveListener);
    }

    /**
     * 设置 OnItemMoveListener.
     *
     * @return {@link OnItemMoveListener}.
     */
    public OnItemMoveListener getOnItemMoveListener() {
        return mDefaultItemTouchHelperCallback.getOnItemMoveListener();
    }

    /**
     * 设置 OnItemMovementListener.
     *
     * @param onItemMovementListener {@link OnItemMovementListener}.
     */
    public void setOnItemMovementListener(OnItemMovementListener onItemMovementListener) {
        mDefaultItemTouchHelperCallback.setOnItemMovementListener(onItemMovementListener);
    }

    /**
     * 获取 OnItemMovementListener.
     *
     * @return {@link OnItemMovementListener}.
     */
    public OnItemMovementListener getOnItemMovementListener() {
        return mDefaultItemTouchHelperCallback.getOnItemMovementListener();
    }

    /**
     * 设置是否可以长按拖拽.
     *
     * @param canDrag true可以
     */
    public void setLongPressDragEnabled(boolean canDrag) {
        mDefaultItemTouchHelperCallback.setLongPressDragEnabled(canDrag);
    }

    /**
     * 获取是否可以长按拖拽
     *
     * @return 是否可以
     */
    public boolean isLongPressDragEnabled() {
        return mDefaultItemTouchHelperCallback.isLongPressDragEnabled();
    }


    /**
     * 设置是否可以侧滑
     *
     * @param canSwipe true可以
     */
    public void setItemViewSwipeEnabled(boolean canSwipe) {
        mDefaultItemTouchHelperCallback.setItemViewSwipeEnabled(canSwipe);
    }

    /**
     * 获取是否可以侧滑
     *
     * @return 是否可以
     */
    public boolean isItemViewSwipeEnabled() {
        return this.mDefaultItemTouchHelperCallback.isItemViewSwipeEnabled();
    }

}
