package android.support.v7.widget.helper;

/**
 * Description: ItemTouchHelper是一个强大的工具，它处理好了关于在RecyclerView上添加拖动排序与滑动删除的所有事情。
 * 它是RecyclerView.ItemDecoration的子类，也就是说它可以轻易的添加到几乎所有的LayoutManager和Adapter中。
 * 它还可以和现有的item动画一起工作，提供受类型限制的拖放动画等等
 * 本类继承ItemTouchHelper（破除包访问权限问题），提供获取其各种处理回掉的方法
 *
 * @author Li Jianying
 * @version 2.0
 * @since 2016/8/1
 */
public class CompatItemTouchHelper extends ItemTouchHelper {

    public CompatItemTouchHelper(ItemTouchHelper.Callback callback) {
        super(callback);
    }

    /**
     * 获取ItemTouchHelper行为控制的回调
     *
     * @return {@link Callback}
     */
    public ItemTouchHelper.Callback getCallback() {
        return mCallback;
    }
}
