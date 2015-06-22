package namofo.org.jiesehelper.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.common.collect.Lists;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import namofo.org.jiesehelper.R;
import namofo.org.jiesehelper.fragment.ArticleFragment_;
import namofo.org.jiesehelper.fragment.BaseFragment;
import namofo.org.jiesehelper.fragment.TopUsersFragment_;

/**
 * 應用程序首頁
 *
 * @author zhengjiong
 * @date 2015年06月12日11:17:05
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    private int mCurrentIndex;
    @ViewById(R.id.drawerlayout)
    public DrawerLayout mDrawerLayout;

    @ViewById(R.id.navigationview)
    public NavigationView mNavigationView;

    public ActionBarDrawerToggle mDrawerToggle;

    public List<BaseFragment> mFragments = Lists.newArrayList();

    @AfterViews
    public void initData() {
        mFragments.add(ArticleFragment_.builder().build());
        mFragments.add(TopUsersFragment_.builder().build());

        initListener();
        selectItem();
    }

    public void initListener(){
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.article:
                        mCurrentIndex = 0;
                        break;
                    case R.id.top:
                        mCurrentIndex = 1;
                        break;
                    default:

                        return true;
                }
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                selectItem();
                //mDrawerToggle.syncState();
                return true;
            }
        });
    }

    /*private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("首页");
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }*/

    public void initDrawerLayout(Toolbar toolbar) {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //mDrawerToggle.syncState();
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

    /**
     * onPostCreate是activity創建完成以後執行的
     * <p/>
     * 設置這個才可以顯示出左上角3條橫線的圖標
     *
     * @param savedInstanceState
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        /*
         * 屏蔽掉syncState,讓左上角三條線不轉動,不然會有bug
         * 重新設置NavaigationIcon:
         * mToolbar.setNavigationIcon(R.mipmap.ic_menu_white);
         */
        //mDrawerToggle.syncState();
    }

    public void selectItem(){
        /*getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, mFragments.get(mCurrentIndex), String.valueOf(mCurrentIndex))
                .commit();*/

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        List<Fragment> fragments = getSupportFragmentManager().getFragments();

        Fragment fragment = mFragments.get(mCurrentIndex);

        if (fragments != null) {
            for (int i = 0; i < fragments.size(); i++) {
                transaction.hide(fragments.get(i));
            }
            if (fragments.contains(fragment)) {
                transaction.show(fragment);
            }else {
                transaction.add(R.id.content_frame, fragment, String.valueOf(mCurrentIndex));
            }
        }else {
            transaction.add(R.id.content_frame, fragment, String.valueOf(mCurrentIndex));
        }
        transaction.commit();

        /*Fragment addFragment = getSupportFragmentManager().findFragmentByTag(String.valueOf(position));
        //currentFragment.is
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            transaction.hide(fragments.get(i));
        }
        Fragment showFragment = getSupportFragmentManager().findFragmentByTag(String.valueOf(mCurrentIndex));
        if (showFragment != null) {
            transaction.show(showFragment)
                    .commit();*/
    }

}
