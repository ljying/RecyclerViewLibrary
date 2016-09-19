package ljying.github.io.recyclerviewlibrary.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import ljying.github.io.recyclerviewlibrary.R;
import ljying.github.io.swiperecyclerview.SwipeSwitch;


/**
 * Description: 滑动布局条目展示
 *
 * @author Li Jianying
 * @version 2.0
 * @since 2016/8/4
 */
public class SwipeLayoutActivity extends AppCompatActivity {

    private ImageView mBtnLeft;
    private ImageView mBtnRight;

    private SwipeSwitch mSwipeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_swipe_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSwipeSwitch = (SwipeSwitch) findViewById(R.id.swipe_layout);
        mBtnLeft = (ImageView) findViewById(R.id.left_view);
        mBtnRight = (ImageView) findViewById(R.id.right_view);

        mBtnLeft.setOnClickListener(xOnClickListener);
        mBtnRight.setOnClickListener(xOnClickListener);
    }

    private View.OnClickListener xOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.left_view) {
                // 关闭菜单
                mSwipeSwitch.smoothCloseMenu();
                Toast.makeText(getApplicationContext(), "我是左面的", Toast.LENGTH_SHORT).show();
            } else if (v.getId() == R.id.right_view) {
                // 关闭菜单
                mSwipeSwitch.smoothCloseMenu();
                Toast.makeText(getApplicationContext(), "我是右面的", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}