package namofo.org.jiesehelper.ui;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import namofo.org.jiesehelper.R;
import namofo.org.jiesehelper.bean.Article;
import namofo.org.jiesehelper.util.NetworkUtils;

/**
 * create by zhengjiong
 * Date: 2015-07-06
 * Time: 18:26
 */
@EActivity(R.layout.article_detail_net_layout)
public class ArticleDetailForNetActivity extends AppCompatActivity{

    @ViewById(R.id.toolbar)
    Toolbar mToolbar;

    @ViewById(R.id.webview)
    WebView mWebView;

    @ViewById(R.id.progress_wrapper)
    View mProgressView;

    @Extra("article")
    Article mArticle;

    @AfterExtras
    public void afterExtras() {

    }

    @AfterViews
    public void afterView(){
        initToolbar();

        initWebView();
    }

    private void initWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);

        //设置是否支持缩放
        mWebView.getSettings().setBuiltInZoomControls(false);

        if (NetworkUtils.isNetworkConnected(this)) {
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        mWebView.setWebViewClient(new MyWebViewClient());

        mWebView.loadUrl(mArticle.getDetailUrl());
    }

    void initToolbar() {
        mToolbar.setTitle("文章详情");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mProgressView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mProgressView.setVisibility(View.GONE);
        }
    }
}
