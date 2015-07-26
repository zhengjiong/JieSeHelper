package namofo.org.jiesehelper.fragment;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.umeng.analytics.MobclickAgent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import de.greenrobot.event.EventBus;
import namofo.org.jiesehelper.R;
import namofo.org.jiesehelper.adapter.MyFavoritesAdapter;
import namofo.org.jiesehelper.bean.Favorites;
import namofo.org.jiesehelper.bean.Favorites$Table;

/**
 * create by zhengjiong
 * Date: 2015-06-22
 * Time: 15:01
 */
@EFragment(R.layout.my_favorites_fragment_layout)
public class MyFavoritesFragment extends NavigationBaseFragment {

    List<Favorites> mFavorites;

    MyFavoritesAdapter mAdater;

    @ViewById(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @AfterViews
    public void afterViews() {
        initToolbar("我的收藏");
        loadData();

        initListener();
    }

    private void initListener() {
    }

    private void reLoad() {
        List<Favorites> favorites = new Select().from(Favorites.class).where(Condition.column(Favorites$Table.DATE)).queryList();
        if (favorites != null) {
            mFavorites.clear();
            mFavorites.addAll(favorites);

            mAdater.notifyDataSetChanged();
        }
    }

    private void loadData() {
        mFavorites = new Select().from(Favorites.class).where(Condition.column(Favorites$Table.DATE)).queryList();
        mAdater = new MyFavoritesAdapter(getActivity(), mFavorites);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdater);

    }

    /**
     * eventbus
     * 使用eventbus重新刷新列表
     * @param save
     */
    public void onEventMainThread(Boolean save) {
        reLoad();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("我的收藏"); //统计页面
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("我的收藏");
    }
}
