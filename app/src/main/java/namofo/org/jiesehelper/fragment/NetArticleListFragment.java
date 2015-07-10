package namofo.org.jiesehelper.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import namofo.org.jiesehelper.R;
import namofo.org.jiesehelper.adapter.HeaderRecyclerViewAdapter;
import namofo.org.jiesehelper.bean.Article;
import namofo.org.jiesehelper.constants.Constants;
import namofo.org.jiesehelper.http.OkHttpUtils;
import namofo.org.jiesehelper.ui.ArticleDetailForNetActivity_;
import namofo.org.jiesehelper.util.ToastUtils;

/**
 * create by zhengjiong
 * Date: 2015-07-06
 * Time: 08:21
 */
@EFragment(R.layout.net_article_list_fragment_layout)
public class NetArticleListFragment extends Fragment{

    int mPage = 0;

    List<Article> mItems = new ArrayList<>();

    ArticleAdapter mAdapter;
    HeaderRecyclerViewAdapter mFooterRecyclerViewAdapter;

    OnScrollListener mOnScrollListener;

    @ViewById(R.id.progress_wrapper)
    View mProgressWrapper;

    @ViewById(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @ViewById(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    @AfterViews
    public void aftetViews() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new ArticleAdapter();
        mFooterRecyclerViewAdapter = new HeaderRecyclerViewAdapter(mAdapter);

        mOnScrollListener = new OnScrollListener();
        mRecyclerView.addOnScrollListener(mOnScrollListener);

        mRecyclerView.setAdapter(mFooterRecyclerViewAdapter);
        mRefreshLayout.setColorSchemeResources(R.color.blue_light, android.R.color.holo_green_light, android.R.color.holo_orange_light);
        mRefreshLayout.setOnRefreshListener(new RefreshListener());

        loadData();
    }

    @UiThread
    void onRefreshComplete(){
        mRefreshLayout.setRefreshing(false);
    }

    private void loadData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("key", "getAppNews");
        params.put("type", "new");
        params.put("page", String.valueOf(mPage));

        OkHttpUtils.get(Constants.HTTP.HOST_URL, params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
                getFailure();
                mOnScrollListener.setIsLoading(false);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                mOnScrollListener.setIsEnd(false);
                mOnScrollListener.setIsLoading(false);

                String jsonResult = response.body().string();
                Log.i("zj", response.request().urlString()+"|getArticle = " + jsonResult);
                List<Article> articles = Article.json2List(jsonResult);

                if (mPage == 0) {
                    mItems.clear();
                }
                if (articles == null || articles.size() == 0 || articles.size() != 10) {
                    mOnScrollListener.setIsEnd(true);
                }
                mItems.addAll(articles);
                getSuccess();
            }
        });
    }

    @UiThread
    public void getFailure(){
        mFooterRecyclerViewAdapter.setShowFooter(false);
        mRefreshLayout.setRefreshing(false);
        ToastUtils.show(getActivity(), R.string.net_error);
        mProgressWrapper.setVisibility(View.GONE);
    }

    @UiThread
    public void getSuccess(){
        mFooterRecyclerViewAdapter.setShowFooter(false);
        mRefreshLayout.setRefreshing(false);
        mProgressWrapper.setVisibility(View.GONE);
        mFooterRecyclerViewAdapter.notifyDataSetChanged();
    }

    class ArticleHolder extends RecyclerView.ViewHolder{
        private View mView;
        private TextView mTxtTitle;

        public ArticleHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mTxtTitle = (TextView) mView.findViewById(R.id.txt_title);
        }

        public void bindData(int position) {
            mTxtTitle.setText(mItems.get(position).getSubject());
        }
    }

    class FooterHolder extends RecyclerView.ViewHolder{

        public FooterHolder(View itemView) {
            super(itemView);
        }
    }

    class ArticleAdapter extends RecyclerView.Adapter<ArticleHolder> implements HeaderRecyclerViewAdapter.FooterRecyclerView{

        @Override
        public ArticleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.article_item_layout, parent, false);
            return new ArticleHolder(view);
        }

        @Override
        public void onBindViewHolder(ArticleHolder holder, final int position) {
            Log.i("zj", "onBindViewHolder position =" + position);
            holder.bindData(position);
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Article article = mItems.get(position);
                    ArticleDetailForNetActivity_
                            .intent(getActivity())
                            .mArticle(article)
                            .start();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mItems == null ? 0 : mItems.size();
        }

        @Override
        public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
            View footer = LayoutInflater.from(getActivity()).inflate(R.layout.loading_more_layout, parent, false);
            return new FooterHolder(footer);
        }

        @Override
        public void onBindFooterView(RecyclerView.ViewHolder holder, int position) {
            Log.i("zj", "onBindFooterView position =" + position);
        }
    }

    class RefreshListener implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            mPage = 0;
            loadData();
        }
    }

    class OnScrollListener extends OnRcvScrollListener{

        @Override
        public void onBottom() {
            mPage++;
            mOnScrollListener.setIsLoading(true);
            mFooterRecyclerViewAdapter.setShowFooter(true);
            mRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadData();
                }
            }, 2000);

        }
    }

    /*public  static abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

        private int previousTotal = 0; // The total number of items in the dataset after the last load
        private boolean loading = true; // True if we are still waiting for the last set of data to load.
        private int visibleThreshold = 5; // The minimum amount of items to have below your current scroll position before loading more.
        int firstVisibleItem, visibleItemCount, totalItemCount;

        private int current_page = 0;

        private LinearLayoutManager mLinearLayoutManager;

        public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
            this.mLinearLayoutManager = linearLayoutManager;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = mLinearLayoutManager.getItemCount();
            firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            if (!loading && (totalItemCount - visibleItemCount)
                    <= (firstVisibleItem + visibleThreshold)) {
                // End has been reached

                // Do something
                current_page++;

                onLoadMore(current_page);

                loading = true;
            }
        }

        public abstract void onLoadMore(int current_page);
    }*/
}
