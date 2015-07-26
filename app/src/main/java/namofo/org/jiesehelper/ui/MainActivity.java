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


import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import namofo.org.jiesehelper.R;
import namofo.org.jiesehelper.fragment.ArticleFragment_;
import namofo.org.jiesehelper.fragment.MyFavoritesFragment_;
import namofo.org.jiesehelper.fragment.NavigationBaseFragment;

/**
 * 應用程序首頁
 *
 * @author zhengjiong
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    private int mCurrentIndex;

    @ViewById(R.id.drawerlayout)
    public DrawerLayout mDrawerLayout;

    @ViewById(R.id.navigationview)
    public NavigationView mNavigationView;

    public ActionBarDrawerToggle mDrawerToggle;

    public List<NavigationBaseFragment> mFragments = new ArrayList<>();

    @AfterViews
    public void initData() {
        UmengUpdateAgent.update(this);
        mFragments.add(ArticleFragment_.builder().build());
        mFragments.add(MyFavoritesFragment_.builder().build());

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
                    case R.id.favorites:
                        mCurrentIndex = 1;
                        break;
                    case R.id.setting:
                        mDrawerLayout.closeDrawers();
                        AboutActivity_.intent(MainActivity.this).start();
                        return true;
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
            AboutActivity_.intent(MainActivity.this).start();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * onPostCreate是activity創建完成以後執行的
     *
     * 設置mDrawerToggle.syncState()才可以顯示出左上角3條橫線的圖標
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

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
