package namofo.org.jiesehelper.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import namofo.org.jiesehelper.R;
import namofo.org.jiesehelper.bean.Article;
import namofo.org.jiesehelper.bean.ArticleFileType;
import namofo.org.jiesehelper.bean.ArticleFileType$Table;

/**
 * create by zhengjiong
 * Date: 2015-06-13
 * Time: 22:47
 */
@EFragment(R.layout.article_list_fragment_layout)
public class ArticleListFragment extends Fragment{
    @ViewById(R.id.recyclerview)
    public RecyclerView mRecyclerView;

    //分类id
    private int mCategoryId;
    //文章
    private List<Article> mItems = Lists.newArrayList();

    @AfterViews
    public void init(){
        mCategoryId = getArguments().getInt("category", 0);
        Select select = new Select("title","file_type");
        mItems = select.from(Article.class)
        .where(Condition.column("category").eq(mCategoryId))
        .queryList();

        select.toString();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(new MyRecyclerAdapter());
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private View mView;
        private TextView mTxtTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mTxtTitle = (TextView) mView.findViewById(R.id.txt_item);
        }

        public void bindData(int position){
            mTxtTitle.setText(mItems.get(position).title);
        }
    }

    class MyRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.article_item_layout, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.bindData(position);
        }

        @Override
        public int getItemCount() {
            return mItems == null ? 0 : mItems.size();
        }
    }
}
