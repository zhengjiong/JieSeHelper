package namofo.org.jiesehelper.ui;

import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import de.greenrobot.event.EventBus;
import namofo.org.jiesehelper.R;
import namofo.org.jiesehelper.bean.Article;
import namofo.org.jiesehelper.bean.Favorites;
import namofo.org.jiesehelper.bean.Favorites$Table;

/**
 * create by zhengjiong
 * Date: 2015-07-06
 * Time: 18:26
 */
@EActivity(R.layout.article_detail_net_layout)
public class ArticleDetailForNetActivity extends AppCompatActivity{

    boolean mIsSaved;

    @ViewById(R.id.toolbar)
    Toolbar mToolbar;

    @ViewById(R.id.webview)
    WebView mWebView;

    @Extra("article")
    Article mArticle;

    @AfterExtras
    public void afterExtras() {

    }

    @AfterViews
    public void afterView(){
        initToolbar();
        setFavoritesStatus();
        initWebView();
    }

    @Background
    public void setFavoritesStatus(){

        Favorites favorites = new Select("id")
                .from(Favorites.class)
                .where(Condition.column(Favorites$Table.ID).eq(mArticle.getNid()))
                .querySingle();

        mIsSaved = (favorites != null);
        Log.i("zj", "favorites == null is " + mIsSaved);
        setOptionMenu(mIsSaved);
    }

    @UiThread
    public void setOptionMenu(boolean isSaved){
        invalidateOptionsMenu();
    }

    private void initWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);

        //设置是否支持缩放
        mWebView.getSettings().setBuiltInZoomControls(false);

        /*if (NetworkUtils.isNetworkConnected(this)) {
            Log.i("zj", "isNetworkConnected = true");
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            Log.i("zj", "isNetworkConnected = false");
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }*/
        mWebView.setWebChromeClient(new MyChromeClient());
        mWebView.setWebViewClient(new MyWebViewClient());

        Log.i("zj", "loadUrl = " + mArticle.getDetailUrl());
        //mWebView.loadUrl("http://v.qq.com/cover/t/tevaepwu8ehlrxp.html?vid=x0017lmdata");
        mWebView.loadUrl(mArticle.getDetailUrl());
    }

    void initToolbar() {
        mToolbar.setTitle("文章详情");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    class MyWebViewClient extends WebViewClient{

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            Log.i("zj", "shouldOverrideUrlLoading");
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            //mProgressView.setVisibility(View.VISIBLE);
            Log.i("zj", "onPageStarted");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //mProgressView.setVisibility(View.GONE);
            Log.i("zj", "onPageFinished");
        }
    }

    class MyChromeClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            Log.i("zj", "newProgrss=" + newProgress);
           // mProgressView.setProgress(newProgress);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem saveMenu = menu.findItem(R.id.action_save);
        if (mIsSaved) {
            saveMenu.setIcon(R.mipmap.ic_favorite_white);
            saveMenu.setTitle("取消收藏");
        } else {
            saveMenu.setIcon(R.mipmap.ic_favorite_outline_white);
            saveMenu.setTitle("收藏");
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_net_article, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_save:
                mIsSaved = !mIsSaved;
                if (mIsSaved) {
                    item.setIcon(R.mipmap.ic_favorite_white);
                    item.setTitle("取消收藏");
                    Snackbar.make(mToolbar, "收藏成功", Snackbar.LENGTH_SHORT).show();
                } else {
                    item.setIcon(R.mipmap.ic_favorite_outline_white);
                    item.setTitle("收藏");
                    Snackbar.make(mToolbar, "取消收藏成功", Snackbar.LENGTH_SHORT).show();
                }
                saveOrDelArticle(mIsSaved);
                break;
        }

        return true;
    }

    /**
     * 保存或刪除收藏
     * @param save, true:保存, false刪除
     */
    void saveOrDelArticle(boolean save) {
        Favorites favorites = new Favorites(
                mArticle.getNid(),
                mArticle.getSubject(),
                mArticle.getImgUrl(),
                mArticle.getDetailUrl(),
                System.currentTimeMillis()
        );
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
