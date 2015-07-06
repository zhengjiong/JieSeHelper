package namofo.org.jiesehelper.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import namofo.org.jiesehelper.R;
import namofo.org.jiesehelper.adapter.ArticlePagerAdapter;
import namofo.org.jiesehelper.bean.ArticleCategory;

/**
 * 文章
 * create by zhengjiong
 * Date: 2015-06-14
 * Time: 09:58
 */
@EFragment(R.layout.article_layout)
public class ArticleFragment extends NavigationBaseFragment {

    @ViewById(R.id.tablayout)
    public TabLayout mTabLayout;

    @ViewById(R.id.viewpager)
    public ViewPager mViewPager;

    private ArticlePagerAdapter mPagerAdapter;
    private List<Fragment> mFragments = new ArrayList<>();
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
                .where(Condition.column("show").eq(1))
                .orderBy(true, "sort")//按sort升序查詢
                .queryList();
        mPagerAdapter = new ArticlePagerAdapter(getChildFragmentManager(), mTabTitles, mFragments);
        mViewPager.setAdapter(mPagerAdapter);
    }

    private void initTab() {
        for (int i = 0; i < mTabTitles.size(); i++) {
            //增加fragment
            Fragment fragment;
            if (i == 0) {
                fragment = NetArticleListFragment_.builder().build();
            } else {
                fragment = ArticleListFragment_.builder().arg("category", mTabTitles.get(i).getId()).build();

            }
            mFragments.add(fragment);

            //增加tab
            TabLayout.Tab tab = mTabLayout.newTab();
            tab.setText(mTabTitles.get(i).getName());
            mTabLayout.addTab(tab);
        }

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mPagerAdapter);
    }

}
