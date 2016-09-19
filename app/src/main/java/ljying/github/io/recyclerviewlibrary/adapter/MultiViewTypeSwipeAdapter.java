package ljying.github.io.recyclerviewlibrary.adapter;

import java.util.List;

/**
 * Description: 多条目类型滑动菜单数据适配器
 *
 * @author Li Jianying
 * @version 2.0
 * @since 2016-9-19 0019
 */


public class MultiViewTypeSwipeAdapter extends SwipeAdapter {

    public static final int VIEW_TYPE_MENU = 1;
    public static final int VIEW_TYPE_NONE = 2;

    public MultiViewTypeSwipeAdapter(List<String> titles) {
        super(titles);
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 != 0 ? VIEW_TYPE_MENU : VIEW_TYPE_NONE;
    }
}
