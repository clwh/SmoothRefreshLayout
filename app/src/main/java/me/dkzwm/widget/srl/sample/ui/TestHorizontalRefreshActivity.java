package me.dkzwm.widget.srl.sample.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.dkzwm.widget.srl.RefreshingListenerAdapter;
import me.dkzwm.widget.srl.SmoothRefreshLayout;
import me.dkzwm.widget.srl.extra.footer.MaterialFooter;
import me.dkzwm.widget.srl.extra.header.MaterialHeader;
import me.dkzwm.widget.srl.sample.R;
import me.dkzwm.widget.srl.sample.adapter.ViewPagerAdapter;
import me.dkzwm.widget.srl.sample.ui.fragment.PageFragment;
import me.dkzwm.widget.srl.util.PixelUtl;

/**
 * Created by dkzwm on 2017/9/13.
 *
 * @author dkzwm
 */
public class TestHorizontalRefreshActivity extends AppCompatActivity {
    private static final int[] sColors =
            new int[]{Color.WHITE, Color.GREEN, Color.YELLOW, Color.BLUE, Color.RED, Color.BLACK};
    private SmoothRefreshLayout mRefreshLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_horizontal_refresh);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.test_horizontal_refresh);
        mRefreshLayout = findViewById(R.id.smoothRefreshLayout_test_horizontal_refresh);
        MaterialHeader header = new MaterialHeader(this);
        header.setColorSchemeColors(new int[]{Color.RED, Color.BLUE, Color.GREEN, Color.BLACK});
        header.setPadding(PixelUtl.dp2px(this, 25), 0, PixelUtl.dp2px(this, 25), 0);
        mRefreshLayout.setHeaderView(header);
        MaterialFooter footer = new MaterialFooter(this);
        footer.setProgressBarColors(new int[]{Color.RED, Color.BLUE, Color.GREEN, Color.BLACK});
        mRefreshLayout.setFooterView(footer);
        mRefreshLayout.setDisableLoadMore(false);
        mRefreshLayout.setDisablePerformLoadMore(false);
        mRefreshLayout.setEnableKeepRefreshView(true);
        mRefreshLayout.setDisableWhenAnotherDirectionMove(true);
        mRefreshLayout.setOnRefreshListener(
                new RefreshingListenerAdapter() {
                    @Override
                    public void onRefreshing() {
                        mHandler.postDelayed(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        List<Fragment> fragments = new ArrayList<>();
                                        for (int i = 0; i < sColors.length; i++) {
                                            fragments.add(PageFragment.newInstance(i, sColors[i]));
                                        }
                                        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
                                        mViewPager.setAdapter(mAdapter);
                                        mRefreshLayout.refreshComplete();
                                        Toast.makeText(
                                                TestHorizontalRefreshActivity.this,
                                                R.string.sr_refresh_complete,
                                                Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                },
                                4000);
                    }

                    @Override
                    public void onLoadingMore() {
                        mHandler.postDelayed(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        mAdapter.addFragment(PageFragment.newInstance(
                                                mAdapter.getCount(),
                                                sColors[mAdapter.getCount() % sColors.length]
                                        ));
                                        mRefreshLayout.refreshComplete();
                                        Toast.makeText(
                                                TestHorizontalRefreshActivity.this,
                                                R.string.sr_refresh_complete,
                                                Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                },
                                4000);
                    }
                });
        mRefreshLayout.setRatioToKeep(1);
        mRefreshLayout.setRatioToRefresh(1);
        mRefreshLayout.autoRefresh(false);
        mViewPager = findViewById(R.id.viewPager_test_horizontal_refresh);
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < sColors.length; i++) {
            fragments.add(PageFragment.newInstance(i, sColors[i]));
        }
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TestHorizontalRefreshActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
