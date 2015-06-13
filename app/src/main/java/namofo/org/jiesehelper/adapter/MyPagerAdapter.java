package namofo.org.jiesehelper.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * create by zhengjiong
 * Date: 2015-06-13
 * Time: 22:57
 */
public class MyPagerAdapter extends FragmentPagerAdapter {
    private List<String> mTitles = Lists.newArrayList();
    private List<Fragment> mFragments = Lists.newArrayList();

    public MyPagerAdapter(FragmentManager fm, List<String> titles, List<Fragment> fragments) {
        super(fm);
        this.mTitles = titles;
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
