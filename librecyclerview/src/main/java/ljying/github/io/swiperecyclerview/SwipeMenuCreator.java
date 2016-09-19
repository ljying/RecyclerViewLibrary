package ljying.github.io.swiperecyclerview;

/**
 * Description:  滑动菜单构建器
 * @author Li Jianying
 * @version 2.0
 * @since 2016/7/26
 */
public interface SwipeMenuCreator {

    /**
     * 为recyclerView item创建menu
     *
     * @param swipeLeftMenu  左侧menu
     * @param swipeRightMenu  右侧menu
     * @param viewType       recyclerView item的类型
     */
    void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType);

}
