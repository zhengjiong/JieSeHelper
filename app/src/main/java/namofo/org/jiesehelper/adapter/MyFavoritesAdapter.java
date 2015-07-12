package namofo.org.jiesehelper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import namofo.org.jiesehelper.R;
import namofo.org.jiesehelper.bean.Favorites;

/**
 * create by zhengjiong
 * Date: 2015-07-12
 * Time: 21:51
 */
public class MyFavoritesAdapter extends RecyclerView.Adapter {

    Context mContext;
    List<Favorites> mFavorites = new ArrayList<>();

    static class MyFavoritesViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle;

        public MyFavoritesViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_title);
        }

        public void bindData(Favorites favorites) {
            txtTitle.setText(favorites.getSubject());
        }
    }

    public MyFavoritesAdapter(Context context, List<Favorites> favorites) {
        this.mContext = context;
        this.mFavorites = favorites;
    }

    @Override
    public MyFavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.article_item_layout, parent, false);
        return new MyFavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Favorites favorites = mFavorites.get(position);
        ((MyFavoritesViewHolder)holder).bindData(favorites);
    }

    @Override
    public int getItemCount() {
        return mFavorites == null ? 0 : mFavorites.size();
    }
}