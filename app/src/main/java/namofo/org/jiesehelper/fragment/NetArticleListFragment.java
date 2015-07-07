package namofo.org.jiesehelper.fragment;

import android.support.v4.app.Fragment;
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

    @ViewById(R.id.progress_wrapper)
    View mProgressWrapper;

    @ViewById(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @AfterViews
    public void aftetViews() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new ArticleAdapter();
        mRecyclerView.setAdapter(mAdapter);

        loadData();
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
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {

                    String jsonResult = response.body().string();
                    Log.i("zj", "getArticle = "+jsonResult);
                    List<Article> articles = Article.json2List(jsonResult);

                    if (mPage == 0) {
                        mItems.clear();
                    }
                    mItems.addAll(articles);
                    getSuccess();
                }
            }
        });
    }

    @UiThread
    public void getFailure(){
        ToastUtils.show(getActivity(), R.string.net_error);
        mProgressWrapper.setVisibility(View.GONE);
    }

    @UiThread
    public void getSuccess(){
        mProgressWrapper.setVisibility(View.GONE);
        mAdapter.notifyDataSetChanged();
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

    class ArticleAdapter extends RecyclerView.Adapter<ArticleHolder>{

        @Override
        public ArticleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.article_item_layout, parent, false);
            return new ArticleHolder(view);
        }

        @Override
        public void onBindViewHolder(ArticleHolder holder, final int position) {
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
    }
}
