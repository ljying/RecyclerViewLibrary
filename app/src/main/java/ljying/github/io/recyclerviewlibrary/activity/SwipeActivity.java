package ljying.github.io.recyclerviewlibrary.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ljying.github.io.recyclerviewlibrary.R;
import ljying.github.io.recyclerviewlibrary.adapter.MultiViewTypeSwipeAdapter;
import ljying.github.io.recyclerviewlibrary.adapter.SwipeAdapter;
import ljying.github.io.recyclerviewlibrary.listener.OnItemClickListener;
import ljying.github.io.swiperecyclerview.Closeable;
import ljying.github.io.swiperecyclerview.OnSwipeMenuItemClickListener;
import ljying.github.io.swiperecyclerview.SwipeMenu;
import ljying.github.io.swiperecyclerview.SwipeMenuCreator;
import ljying.github.io.swiperecyclerview.SwipeMenuItem;
import ljying.github.io.swiperecyclerview.SwipeMenuRecyclerView;
import ljying.github.io.swiperecyclerview.touch.OnItemMoveListener;

public class SwipeActivity extends AppCompatActivity implements OnItemClickListener, OnSwipeMenuItemClickListener, SwipeMenuCreator, OnItemMoveListener {

    private List<String> mStrings;

    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;

    private SwipeAdapter adapter;

    private MultiViewTypeSwipeAdapter multiViewTypeSwipeAdapter;

    private boolean isShowLeftSwipeMenu;

    private boolean isShowRightMenu;

    private boolean isMultiTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mStrings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mStrings.add("条目" + (i + 1));
        }

        adapter = new SwipeAdapter(mStrings);
        adapter.setOnItemClickListener(this);
        multiViewTypeSwipeAdapter = new MultiViewTypeSwipeAdapter(mStrings);
        multiViewTypeSwipeAdapter.setOnItemClickListener(this);

        initSwipeRecyclerView();
    }

    private void initSwipeRecyclerView() {

        mSwipeMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.swipe_recycler_view);
        // 布局管理器。
        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setHasFixedSize(true);

        // 设置菜单创建器。
        mSwipeMenuRecyclerView.setSwipeMenuCreator(this);
        // 设置菜单Item点击监听。
        mSwipeMenuRecyclerView.setSwipeMenuItemClickListener(this);
        //注意menu必须要在设置adapter之前设置
        mSwipeMenuRecyclerView.setAdapter(isMultiTypeAdapter ? multiViewTypeSwipeAdapter : adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.swipe_control, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.add_left_swipe_menu:
                refreshRecyclerView(true, false, false);
                break;
            case R.id.add_right_swipe_menu:
                refreshRecyclerView(false, true, false);
                break;
            case R.id.add_swipe_menu:
                refreshRecyclerView(true, true, false);
                break;
            case R.id.multi_type_swipe_menu:
                refreshRecyclerView(false, false, true);
                break;
            case R.id.drop_item:
                setDragOrSwipeDeleteEnable(true, false);
                break;
            case R.id.swipe_delete_menu:
                setDragOrSwipeDeleteEnable(false, true);
        }
        return true;
    }

    private void refreshRecyclerView(boolean isShowLeft, boolean isShowRight, boolean isMultiType) {
        isShowLeftSwipeMenu = isShowLeft;
        isShowRightMenu = isShowRight;
        isMultiTypeAdapter = isMultiType;
        mSwipeMenuRecyclerView.setLongPressDragEnabled(false);
        mSwipeMenuRecyclerView.setItemViewSwipeEnabled(false);
        initSwipeRecyclerView();
    }

    private void setDragOrSwipeDeleteEnable(boolean dragEnable, boolean swipeDeleteEnable) {
        mSwipeMenuRecyclerView.setLongPressDragEnabled(dragEnable);
        mSwipeMenuRecyclerView.setItemViewSwipeEnabled(swipeDeleteEnable);
        mSwipeMenuRecyclerView.setOnItemMoveListener(this);
        isShowRightMenu = false;
        isShowLeftSwipeMenu = false;
        initSwipeRecyclerView();
    }

    @Override
    public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {

        int size = getResources().getDimensionPixelSize(R.dimen.item_height);

        if (isMultiTypeAdapter) {
            if (viewType == MultiViewTypeSwipeAdapter.VIEW_TYPE_NONE) {
                swipeLeftMenu.clearMenuItem();
                swipeRightMenu.clearMenuItem();
            } else if (viewType == MultiViewTypeSwipeAdapter.VIEW_TYPE_MENU) {
                swipeLeftMenu.addMenuItem(createSwipeMenuItem(R.drawable.selector_green, R.drawable.ic_action_add, null, 0, size, size));
                swipeLeftMenu.addMenuItem(createSwipeMenuItem(R.drawable.selector_red, R.drawable.ic_action_close, null, 0, size, size));
                swipeRightMenu.addMenuItem(createSwipeMenuItem(R.drawable.selector_red, R.drawable.ic_action_delete, "删除", Color.WHITE, size, size));
                swipeRightMenu.addMenuItem(createSwipeMenuItem(R.drawable.selector_purple, R.drawable.ic_action_close, null, 0, size, size));
                swipeRightMenu.addMenuItem(createSwipeMenuItem(R.drawable.selector_green, 0, "添加", Color.WHITE, size, size));
            }
        } else {

            if (isShowLeftSwipeMenu) {
                // 添加一个按钮到左侧菜单。
                swipeLeftMenu.addMenuItem(createSwipeMenuItem(R.drawable.selector_green, R.drawable.ic_action_add, null, 0, size, size));
                swipeLeftMenu.addMenuItem(createSwipeMenuItem(R.drawable.selector_red, R.drawable.ic_action_close, null, 0, size, size));
            } else {
                swipeLeftMenu.clearMenuItem();
            }

            if (isShowRightMenu) {
                // 添加一个按钮到右侧侧菜单。
                swipeRightMenu.addMenuItem(createSwipeMenuItem(R.drawable.selector_red, R.drawable.ic_action_delete, "删除", Color.WHITE, size, size));
                swipeRightMenu.addMenuItem(createSwipeMenuItem(R.drawable.selector_purple, R.drawable.ic_action_close, null, 0, size, size));
                swipeRightMenu.addMenuItem(createSwipeMenuItem(R.drawable.selector_green, 0, "添加", Color.WHITE, size, size));
            } else {
                swipeRightMenu.clearMenuItem();
            }
        }
    }


    /**
     * 创建菜单子条目
     *
     * @param bgDrawable
     * @param icon
     * @param text
     * @param textColor
     * @param width
     * @param height
     * @return
     */
    private SwipeMenuItem createSwipeMenuItem(@DrawableRes int bgDrawable, @DrawableRes int icon, String text, @ColorInt int textColor, int width, int height) {
        return new SwipeMenuItem(this)
                // 点击的背景。
                .setBackgroundDrawable(bgDrawable)
                // 图标。
                .setImage(icon)
                // 文字，还可以设置文字颜色，大小等。。
                .setText(text)
                .setTextColor(textColor)
                // 宽度。
                .setWidth(width)
                // 高度。
                .setHeight(height);
    }

    /**
     * 条目点击回调
     *
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "点击了" + mStrings.get(position), Toast.LENGTH_SHORT).show();
    }

    /**
     * 菜单条目点击回调
     *
     * @param closeable       是否能够关闭.
     * @param adapterPosition 菜单所在条目的位置.
     * @param menuPosition    菜单条目在菜单中的位置.
     * @param direction       滑动的方向 {@link SwipeMenuRecyclerView#LEFT_DIRECTION}, {@link SwipeMenuRecyclerView#RIGHT_DIRECTION}.
     */
    @Override
    public void onMenuItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
        // 关闭被点击的菜单
        closeable.smoothCloseMenu();

        if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
            Toast.makeText(this, "点击了" + mStrings.get(adapterPosition) + "; 右侧菜单第" + (menuPosition + 1) + "个子菜单", Toast.LENGTH_SHORT).show();
        } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
            Toast.makeText(this, "点击了" + mStrings.get(adapterPosition) + "; 左侧菜单第" + (menuPosition + 1) + "个子菜单", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 条目移动监听
     *
     * @param fromPosition 起始位置
     * @param toPosition   结束位置
     * @return 返回true表示处理了，返回false表示你没有处理。
     */
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        // 当Item被拖拽的时候的处理，这里是移动了数据的位置，也可以删除。
        Collections.swap(mStrings, fromPosition, toPosition);
        if (isMultiTypeAdapter) {
            multiViewTypeSwipeAdapter.notifyItemMoved(fromPosition, toPosition);
        } else {
            adapter.notifyItemMoved(fromPosition, toPosition);
        }
        return true;
    }

    /**
     * 滑动条目消失监听
     *
     * @param adapterPosition 侧滑位置.
     * @param direction       侧滑方向
     */
    @Override
    public void onItemSwipe(int adapterPosition, int direction) {
        Toast.makeText(this, "删除了" + mStrings.get(adapterPosition), Toast.LENGTH_SHORT).show();
        mStrings.remove(adapterPosition);
        if (isMultiTypeAdapter) {
            multiViewTypeSwipeAdapter.notifyDataSetChanged();
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}
