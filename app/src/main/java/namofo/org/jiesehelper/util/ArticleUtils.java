package namofo.org.jiesehelper.util;

import android.database.sqlite.SQLiteException;
import android.util.Log;

import de.greenrobot.event.EventBus;
import namofo.org.jiesehelper.bean.Article;
import namofo.org.jiesehelper.bean.Favorites;

/**
 * create by zhengjiong
 * Date: 2015-07-18
 * Time: 17:19
 */
public class ArticleUtils {


    /**
     * /**
     * 保存或刪除收藏
     * @param save, true:保存, false刪除
     */
    public static void saveOrDeleteArticle(Article article, boolean save) {
        Favorites favorites = null;
        switch (article.getArticleFileType().getId()) {
            case 6://net
                favorites = new Favorites(
                        article.getNid(),
                        article.getSubject(),
                        article.getImgUrl(),
                        article.getDetailUrl(),
                        System.currentTimeMillis(),
                        article.getArticleFileType().getId()
                );
                break;
            case 1://txt
                favorites = new Favorites(
                        article.getId(),
                        article.getSubject(),
                        System.currentTimeMillis(),
                        article.getArticleFileType().getId()
                );
                break;
        }

        try {
            if (save) {
                /*if (favorites.exists()) {
                    favorites.update();
                } else {
                    favorites.insert();
                }*/
                favorites.update();
                Log.i("zj", "update");
            } else {
                favorites.delete();
                Log.i("zj", "delete");
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        EventBus.getDefault().post(new Boolean(save));
    }
}
