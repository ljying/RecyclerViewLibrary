package ljying.github.io.swiperecyclerview;

/**
 * Description: 条目菜单关闭效果接口
 *
 * @author Li Jianying
 * @version 2.0
 * @since 2016/7/26
 */
public interface Closeable {

    /**
     * 平稳关闭条目左侧菜单
     */
    void smoothCloseLeftMenu();

    /**
     * 平稳关闭条目右侧菜单
     */
    void smoothCloseRightMenu();

    /**
     * 平稳关闭条目菜单
     */
    void smoothCloseMenu();

    /**
     * 在时间段内平稳关闭条目菜单
     *
     * @param duration 关闭时间
     */
    void smoothCloseMenu(int duration);
}
