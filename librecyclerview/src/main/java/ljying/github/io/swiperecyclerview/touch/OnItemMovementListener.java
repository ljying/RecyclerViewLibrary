package ljying.github.io.swiperecyclerview.touch;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Description: 条目移动时监听
 *
 * @author Li Jianying
 * @version 2.0
 * @since 2016/8/1
 */
public interface OnItemMovementListener {

    int INVALID = 0;

    /**
     * 向左拖拽或者滑动
     */
    int LEFT = ItemTouchHelper.LEFT;

    /**
     * 向上拖拽或者滑动
     */
    int UP = ItemTouchHelper.UP;

    /**
     * 向右拖拽或者滑动
     */
    int RIGHT = ItemTouchHelper.RIGHT;

    /**
     * 向下拖拽或者滑动
     */
    int DOWN = ItemTouchHelper.DOWN;

    /**
     * ViewHolder是否可以拖拽
     *
     * @param recyclerView     {@link RecyclerView}.
     * @param targetViewHolder 判断的 ViewHolder.
     * @return use {@link #LEFT}, {@link #UP}, {@link #RIGHT}, {@link #DOWN}.
     */
    int onDragFlags(RecyclerView recyclerView, RecyclerView.ViewHolder targetViewHolder);

    /**
     * ViewHolder是否可以滑动
     *
     * @param recyclerView     {@link RecyclerView}.
     * @param targetViewHolder 判断的 ViewHolder.
     * @return use {@link #LEFT}, {@link #UP}, {@link #RIGHT}, {@link #DOWN}.
     */
    int onSwipeFlags(RecyclerView recyclerView, RecyclerView.ViewHolder targetViewHolder);

}
