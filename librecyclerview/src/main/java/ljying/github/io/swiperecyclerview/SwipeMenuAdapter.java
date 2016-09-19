package ljying.github.io.swiperecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Description: 滑动菜单适配器
 *
 * @author Li Jianying
 * @version 2.0
 * @since 2016/7/27
 */
public abstract class SwipeMenuAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    /**
     * 滑动菜单创建器
     */
    private SwipeMenuCreator mSwipeMenuCreator;

    /**
     * 滑动菜单点击事件监听
     */
    private OnSwipeMenuItemClickListener mSwipeMenuItemClickListener;

    void setSwipeMenuCreator(SwipeMenuCreator swipeMenuCreator) {
        this.mSwipeMenuCreator = swipeMenuCreator;
    }

    void setSwipeMenuItemClickListener(OnSwipeMenuItemClickListener swipeMenuItemClickListener) {
        this.mSwipeMenuItemClickListener = swipeMenuItemClickListener;
    }

    @Override
    public final VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView = onCreateContentView(parent, viewType);
        if (mSwipeMenuCreator != null) {
            SwipeMenuLayout swipeMenuLayout = (SwipeMenuLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_item_default, parent, false);

            SwipeMenu swipeLeftMenu = new SwipeMenu(swipeMenuLayout, viewType);
            SwipeMenu swipeRightMenu = new SwipeMenu(swipeMenuLayout, viewType);

            mSwipeMenuCreator.onCreateMenu(swipeLeftMenu, swipeRightMenu, viewType);

            int leftMenuCount = swipeLeftMenu.getMenuItems().size();
            if (leftMenuCount > 0) {
                SwipeMenuView swipeLeftMenuView = (SwipeMenuView) swipeMenuLayout.findViewById(R.id.swipe_left);
                swipeLeftMenuView.bindMenu(swipeLeftMenu, SwipeMenuRecyclerView.LEFT_DIRECTION);
                swipeLeftMenuView.bindMenuItemClickListener(mSwipeMenuItemClickListener, swipeMenuLayout);
            }

            int rightMenuCount = swipeRightMenu.getMenuItems().size();
            if (rightMenuCount > 0) {
                SwipeMenuView swipeRightMenuView = (SwipeMenuView) swipeMenuLayout.findViewById(R.id.swipe_right);
                swipeRightMenuView.bindMenu(swipeRightMenu, SwipeMenuRecyclerView.RIGHT_DIRECTION);
                swipeRightMenuView.bindMenuItemClickListener(mSwipeMenuItemClickListener, swipeMenuLayout);
            }

            if (leftMenuCount > 0 || rightMenuCount > 0) {
                ViewGroup viewGroup = (ViewGroup) swipeMenuLayout.findViewById(R.id.swipe_content);
                viewGroup.addView(contentView);
                contentView = swipeMenuLayout;

            }
        }
        return onCompatCreateViewHolder(contentView, viewType);
    }

    @Override
    public final void onBindViewHolder(VH holder, int position, List<Object> payloads) {
        View itemView = holder.itemView;
        if (itemView instanceof SwipeMenuLayout) {
            SwipeMenuLayout swipeMenuLayout = (SwipeMenuLayout) itemView;
            int childCount = swipeMenuLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = swipeMenuLayout.getChildAt(i);
                if (childView instanceof SwipeMenuView) {
                    ((SwipeMenuView) childView).bindAdapterPosition(position);
                }
            }
        }
        onCompatBindViewHolder(holder, position, payloads);
    }


    /**
     * 创建条目内容视图
     *
     * @param parent   条目父布局容器
     * @param viewType 条目类型
     * @return 条目试图
     */
    public abstract View onCreateContentView(ViewGroup parent, int viewType);

    /**
     * 代替 {@link #onBindViewHolder(RecyclerView.ViewHolder, int, List)}.
     *
     * @param holder   条目的viewHolder对象
     * @param position 条目的viewHolder 位置
     * @param payloads A non-null list of merged payloads. Can be empty list if requires full update.
     */
    public void onCompatBindViewHolder(VH holder, int position, List<Object> payloads) {
        onBindViewHolder(holder, position);
    }

    /**
     * 代替 {@link #onCreateViewHolder(ViewGroup, int)}.
     *
     * @param realContentView View 的itemView
     * @param viewType        View 的itemView 的类型
     * @return 条目的viewHolder对象
     */
    public abstract VH onCompatCreateViewHolder(View realContentView, int viewType);
}