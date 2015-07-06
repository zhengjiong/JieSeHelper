package namofo.org.jiesehelper.ui;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import namofo.org.jiesehelper.R;
import namofo.org.jiesehelper.bean.Article;

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

    @Extra("article")
    Article mArticle;

    @AfterExtras
    public void afterExtras() {

    }

    @AfterViews
    public void afterView(){
        initToolbar();

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
}
