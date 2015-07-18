package namofo.org.jiesehelper.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import namofo.org.jiesehelper.R;
import namofo.org.jiesehelper.bean.Article;
import namofo.org.jiesehelper.bean.Favorites;
import namofo.org.jiesehelper.ui.ArticleDetailForNetActivity_;
import namofo.org.jiesehelper.ui.ArticleDetailForTxtActivity_;

/**
 * create by zhengjiong
 * Date: 2015-07-12
 * Time: 21:51
 */
public class MyFavoritesAdapter extends RecyclerView.Adapter {

    Context mContext;
    List<Favorites> mFavorites = new ArrayList<>();

    static class MyFavoritesViewHolder extends RecyclerView.ViewHolder{
        View mView;
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final Favorites favorite = mFavorites.get(position);
        ((MyFavoritesViewHolder)holder).bindData(favorite);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (favorite.getFile_type()) {
                    case 6://net
                        Article article = new Article(
                                favorite.getId(),
                                favorite.getSubject(),
                                favorite.getImgUrl(),
                                favorite.getDetailUrl()
                        );
                        ArticleDetailForNetActivity_
                                .intent(mContext)
                                .mArticle(article)
                                .start();
                        break;
                    case 1://txt
                        Snackbar.make(holder.itemView, "txt", Snackbar.LENGTH_SHORT).show();
                        ArticleDetailForTxtActivity_.intent(mContext)
                                .mId(favorite.getId())
                                .mTitle(favorite.getSubject())
                                .start();
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFavorites == null ? 0 : mFavorites.size();
    }
}
