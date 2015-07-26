package namofo.org.jiesehelper.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.umeng.analytics.MobclickAgent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import namofo.org.jiesehelper.R;
import namofo.org.jiesehelper.bean.Article;
import namofo.org.jiesehelper.ui.ArticleDetailForDbActivity_;
import namofo.org.jiesehelper.ui.ArticleDetailForTxtActivity_;
import namofo.org.jiesehelper.ui.PDFViewActivity_;

/**
 * 文章列表
 * create by zhengjiong
 * Date: 2015-06-13
 * Time: 22:47
 */
@EFragment(R.layout.article_list_fragment_layout)
public class ArticleListFragment extends Fragment{

    //分类id
    private int mCategoryId;
    //文章
    private List<Article> mItems = new ArrayList<>();

    @ViewById(R.id.recyclerview)
    public RecyclerView mRecyclerView;

    @AfterViews
    public void init(){
        mCategoryId = getArguments().getInt("category", 0);
        mItems = new Select("id", "subject", "file_type")
                .from(Article.class)
                .where(Condition.column("category").eq(mCategoryId))
                .orderBy(true, "sort")//按sort升序查詢
                .queryList();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(new MyRecyclerAdapter());

        /*Request request = new Request.Builder()
                .url("http://jiese.ptxin.com/web.do?key=getAppNews&type=new&page=3")
                .build();

        OkHttpUtils.enqueue(request, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.i("zj", "onFailure");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {

                    Log.i("zj", "response.body().string()=" + response.body().string());
                }
            }
        });*/
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private View mView;
        private TextView mTxtTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mTxtTitle = (TextView) mView.findViewById(R.id.txt_title);
        }

        public void bindData(int position){
            mTxtTitle.setText(mItems.get(position).getSubject());
        }
    }

    class MyRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.article_item_layout, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.bindData(position);
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Article article = mItems.get(position);
                    switch (article.getArticleFileType().getFile_name()) {
                        case "db":
                            ArticleDetailForDbActivity_
                                    .intent(getActivity())
                                    .mId(article.getId())
                                    .mTitle(article.getSubject())
                                    .start();
                            break;
                        case "txt":
                            ArticleDetailForTxtActivity_
                                    .intent(getActivity())
                                    .mId(article.getId())
                                    .mTitle(article.getSubject())
                                    .start();
                            break;
                        case "html":

                            break;
                        case "doc":

                            break;
                        case "pdf":
                            PDFViewActivity_
                                    .intent(getActivity())
                                    .mId(article.getId())
                                    .mTitle(article.getSubject())
                                    .start();
                            /*Intent intent = new Intent(getActivity(), PDFViewActivity.class);
                            intent.putExtra("id", article.getId());
                            intent.putExtra("title", article.getTitle());
                            startActivity(intent);*/
                            break;
                        case "net":

                            break;
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mItems == null ? 0 : mItems.size();
        }
    }

}
