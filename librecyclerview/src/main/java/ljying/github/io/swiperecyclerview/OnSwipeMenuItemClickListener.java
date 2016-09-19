package ljying.github.io.swiperecyclerview;

/**
 * Description: 侧滑菜单条目点击时间监听
 *
 * @author Li Jianying
 * @version 2.0
 * @since 2016/7/26
 */
public interface OnSwipeMenuItemClickListener {

    /**
     * @param closeable       是否能够关闭.
     * @param adapterPosition 菜单所在条目的位置.
     * @param menuPosition    菜单条目在菜单中的位置.
     * @param direction       滑动的方向 {@link SwipeMenuRecyclerView#LEFT_DIRECTION}, {@link SwipeMenuRecyclerView#RIGHT_DIRECTION}.
     */
    void onMenuItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction);

}