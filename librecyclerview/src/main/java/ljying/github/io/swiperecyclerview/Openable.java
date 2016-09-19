package ljying.github.io.swiperecyclerview;

/**
 * Description:条目菜单打开相关效果接口
 * @author Li Jianying
 * @version 2.0
 * @since 2016/7/26
 */
public interface Openable {

    /**
     * 条目菜单是否打开？
     *
     * @return true, otherwise false.
     */
    boolean isMenuOpen();

    /**
     * 条目左侧菜单是否打开？
     *
     * @return true, otherwise false.
     */
    boolean isLeftMenuOpen();

    /**
     * 条目菜单是否打开？
     *
     * @return true, otherwise false.
     */
    boolean isLeftMenuOpenNotEqual();

    /**
     * 条目菜单是否打开？
     *
     * @return true, otherwise false.
     */
    boolean isMenuOpenNotEqual();

    /**
     * 条目右侧菜单是否打开？
     *
     * @return true, otherwise false.
     */
    boolean isRightMenuOpen();

    /**
     * 条目右侧菜单是否打开？
     *
     * @return true, otherwise false.
     */
    boolean isRightMenuOpenNotEqual();

    /**
     * 打开菜单
     */
    void smoothOpenMenu();

    /**
     * 打开条目左侧菜单
     */
    void smoothOpenLeftMenu();

    /**
     * 打开条目左侧菜单
     *
     * @param duration duration time.
     */
    void smoothOpenLeftMenu(int duration);

    /**
     * 打开条目右侧菜单
     */
    void smoothOpenRightMenu();

    /**
     * 打开条目右侧菜单
     *
     * @param duration duration time.
     */
    void smoothOpenRightMenu(int duration);

}
