package namofo.org.jiesehelper.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.common.collect.Lists;

import java.util.List;

import namofo.org.jiesehelper.R;

/**
 * create by zhengjiong
 * Date: 2015-06-13
 * Time: 22:47
 */
public class ArticleFragment extends Fragment{
    private RecyclerView mRecyclerView;
    private List<String> mItems = Lists.newArrayList();
    private List<Fragment> mFragments = Lists.newArrayList();

    public static ArticleFragment newInstance() {

        Bundle args = new Bundle();

        ArticleFragment fragment = new ArticleFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.article_fragment_layout, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        for (int i = 0; i < 100; i++) {
            mItems.add(String.valueOf(i));
        }

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
            mTxtTitle.setText("item" + position);
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
            return mItems.size();
        }
    }
}
