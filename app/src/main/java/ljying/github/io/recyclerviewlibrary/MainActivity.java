package ljying.github.io.recyclerviewlibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.Arrays;
import java.util.List;

import ljying.github.io.recyclerviewlibrary.activity.MultiCheckExpandActivity;
import ljying.github.io.recyclerviewlibrary.activity.MultiTypeExpandActivity;
import ljying.github.io.recyclerviewlibrary.activity.SimpleExpandActivity;
import ljying.github.io.recyclerviewlibrary.activity.SingleCheckExpandActivity;
import ljying.github.io.recyclerviewlibrary.activity.SwipeActivity;
import ljying.github.io.recyclerviewlibrary.activity.SwipeLayoutActivity;
import ljying.github.io.recyclerviewlibrary.adapter.MainItemAdapter;
import ljying.github.io.recyclerviewlibrary.listener.OnItemClickListener;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    private RecyclerView recyclerView;
    private List<String> titles;
    private List<String> descriptions;
    private MainItemAdapter mMainItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        titles = Arrays.asList(getResources().getStringArray(R.array.main_item));
        descriptions = Arrays.asList(getResources().getStringArray(R.array.main_item_des));
        mMainItemAdapter = new MainItemAdapter(titles, descriptions);
        mMainItemAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mMainItemAdapter);
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(this, SwipeLayoutActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, SwipeActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, SimpleExpandActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, MultiCheckExpandActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, SingleCheckExpandActivity.class));
                break;
            case 5:
                startActivity(new Intent(this, MultiTypeExpandActivity.class));
                break;
        }
    }

}
