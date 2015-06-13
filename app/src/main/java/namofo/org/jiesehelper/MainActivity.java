package namofo.org.jiesehelper;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.common.collect.Lists;

import java.util.List;

import namofo.org.jiesehelper.adapter.MyPagerAdapter;
import namofo.org.jiesehelper.fragment.ArticleFragment;

/**
 * 應用程序首頁
 * @author zhengjiong
 * @date 2015年06月12日11:17:05
 */
public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private MyPagerAdapter mPagerAdapter;

    private List<Fragment> mFragments = Lists.newArrayList();
    private List<String> mTabTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        initToolbar();
        initViewPager();
        initTab();
    }

    private void initViewPager() {
        mTabTitles = Lists.newArrayList("全部", "item1", "item2", "item3", "item4");
        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), mTabTitles, mFragments);
        mViewPager.setAdapter(mPagerAdapter);
    }

    private void initTab() {
        mFragments.add(ArticleFragment.newInstance());
        mFragments.add(ArticleFragment.newInstance());
        mFragments.add(ArticleFragment.newInstance());
        mFragments.add(ArticleFragment.newInstance());
        mFragments.add(ArticleFragment.newInstance());

        TabLayout.Tab tab1 = mTabLayout.newTab().setText(mTabTitles.get(0));
        TabLayout.Tab tab2 = mTabLayout.newTab().setText(mTabTitles.get(1));
        TabLayout.Tab tab3 = mTabLayout.newTab().setText(mTabTitles.get(2));
        TabLayout.Tab tab4 = mTabLayout.newTab().setText(mTabTitles.get(3));
        TabLayout.Tab tab5 = mTabLayout.newTab().setText(mTabTitles.get(4));

        mTabLayout.addTab(tab1);
        mTabLayout.addTab(tab2);
        mTabLayout.addTab(tab3);
        mTabLayout.addTab(tab4);
        mTabLayout.addTab(tab5);

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mPagerAdapter);
    }

    private void initToolbar() {
        mToolbar.setTitle("首页");
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
