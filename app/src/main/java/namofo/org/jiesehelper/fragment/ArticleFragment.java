package namofo.org.jiesehelper.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.collect.Lists;

import java.util.List;

import namofo.org.jiesehelper.R;
import namofo.org.jiesehelper.adapter.MyPagerAdapter;
import namofo.org.jiesehelper.ui.MainActivity;

/**
 * create by zhengjiong
 * Date: 2015-06-14
 * Time: 09:58
 */
public class ArticleFragment extends Fragment{
    public Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private MyPagerAdapter mPagerAdapter;

    private List<Fragment> mFragments = Lists.newArrayList();
    private List<String> mTabTitles;


    public static ArticleFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ArticleFragment fragment = new ArticleFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.article_layout, container, false);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);

        initToolbar();
        initViewPager();
        initTab();
        return view;
    }

    private void initToolbar() {
        mToolbar.setTitle("首页");
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        /*mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });*/

        ((MainActivity)getActivity()).initDrawerLayout(mToolbar);
    }

    private void initViewPager() {
        mTabTitles = Lists.newArrayList("邪淫危害", "戒色方法", "戒邪淫报名手册", "经典语录", "身体康复", "积福改命");
        mPagerAdapter = new MyPagerAdapter(getChildFragmentManager(), mTabTitles, mFragments);
        mViewPager.setAdapter(mPagerAdapter);
    }

    private void initTab() {
        mFragments.add(ArticleListFragment.newInstance());
        mFragments.add(ArticleListFragment.newInstance());
        mFragments.add(ArticleListFragment.newInstance());
        mFragments.add(ArticleListFragment.newInstance());
        mFragments.add(ArticleListFragment.newInstance());
        mFragments.add(ArticleListFragment.newInstance());

        TabLayout.Tab tab1 = mTabLayout.newTab().setText(mTabTitles.get(0));
        TabLayout.Tab tab2 = mTabLayout.newTab().setText(mTabTitles.get(1));
        TabLayout.Tab tab3 = mTabLayout.newTab().setText(mTabTitles.get(2));
        TabLayout.Tab tab4 = mTabLayout.newTab().setText(mTabTitles.get(3));
        TabLayout.Tab tab5 = mTabLayout.newTab().setText(mTabTitles.get(4));
        TabLayout.Tab tab6 = mTabLayout.newTab().setText(mTabTitles.get(5));

        mTabLayout.addTab(tab1);
        mTabLayout.addTab(tab2);
        mTabLayout.addTab(tab3);
        mTabLayout.addTab(tab4);
        mTabLayout.addTab(tab5);
        mTabLayout.addTab(tab6);

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mPagerAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
