package namofo.org.jiesehelper.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

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

    @ViewById(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    @AfterViews
    public void afterViews() {
        initToolbar("我的收藏");
        loadData();

        initListener();
    }

    private void initListener() {
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_dark, android.R.color.holo_green_dark, android.R.color.holo_orange_dark);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                List<Favorites> favorites = new Select().from(Favorites.class).where(Condition.column(Favorites$Table.DATE)).queryList();
                if (favorites != null) {
                    mFavorites.clear();
                    mFavorites.addAll(favorites);

                    mAdater.notifyDataSetChanged();
                }
                mRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void loadData() {
        mFavorites = new Select().from(Favorites.class).where(Condition.column(Favorites$Table.DATE)).queryList();
        mAdater = new MyFavoritesAdapter(getActivity(), mFavorites);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdater);

    }

}