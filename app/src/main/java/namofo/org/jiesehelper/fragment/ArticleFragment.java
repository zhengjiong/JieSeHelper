package namofo.org.jiesehelper.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.google.common.collect.Lists;
import com.raizlabs.android.dbflow.sql.language.Select;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import namofo.org.jiesehelper.R;
import namofo.org.jiesehelper.adapter.ArticlePagerAdapter;
import namofo.org.jiesehelper.bean.ArticleCategory;

/**
 * create by zhengjiong
 * Date: 2015-06-14
 * Time: 09:58
 */
@EFragment(R.layout.article_layout)
public class ArticleFragment extends BaseFragment{

    @ViewById(R.id.tablayout)
    public TabLayout mTabLayout;

    @ViewById(R.id.viewpager)
    public ViewPager mViewPager;

    private ArticlePagerAdapter mPagerAdapter;
    private List<Fragment> mFragments = Lists.newArrayList();
    private List<ArticleCategory> mTabTitles;

    @AfterViews
    public void afterViews(){
        initToolbar("文章");
        initViewPager();
        initTab();
    }

    private void initViewPager() {
        mTabTitles = new Select()
                .from(ArticleCategory.class)
                .where()
                .orderBy(true, "sort")//按sort升序查詢
                .queryList();
        mPagerAdapter = new ArticlePagerAdapter(getChildFragmentManager(), mTabTitles, mFragments);
        mViewPager.setAdapter(mPagerAdapter);
    }

    private void initTab() {
        mFragments.add(ArticleListFragment.newInstance());
        mFragments.add(ArticleListFragment.newInstance());
        mFragments.add(ArticleListFragment.newInstance());
        mFragments.add(ArticleListFragment.newInstance());
        mFragments.add(ArticleListFragment.newInstance());
        mFragments.add(ArticleListFragment.newInstance());

        TabLayout.Tab tab1 = mTabLayout.newTab().setText(mTabTitles.get(0).getName());
        TabLayout.Tab tab2 = mTabLayout.newTab().setText(mTabTitles.get(1).getName());
        TabLayout.Tab tab3 = mTabLayout.newTab().setText(mTabTitles.get(2).getName());
        TabLayout.Tab tab4 = mTabLayout.newTab().setText(mTabTitles.get(3).getName());
        TabLayout.Tab tab5 = mTabLayout.newTab().setText(mTabTitles.get(4).getName());
        TabLayout.Tab tab6 = mTabLayout.newTab().setText(mTabTitles.get(5).getName());

        mTabLayout.addTab(tab1);
        mTabLayout.addTab(tab2);
        mTabLayout.addTab(tab3);
        mTabLayout.addTab(tab4);
        mTabLayout.addTab(tab5);
        mTabLayout.addTab(tab6);

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mPagerAdapter);
    }

}
