package namofo.org.jiesehelper.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import namofo.org.jiesehelper.R;
import namofo.org.jiesehelper.bean.Article;

/**
 * create by zhengjiong
 * Date: 2015-06-28
 * Time: 11:39
 */
@EActivity(R.layout.article_detail_db_layout)
public class ArticleDetailForDbActivity extends AppCompatActivity{

    @ViewById(R.id.content)
    TextView mTxtContent;

    @ViewById(R.id.appbar)
    AppBarLayout mAppBarLayout;

    @ViewById(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;

    @ViewById(R.id.img_header)
    ImageView mImgHead;

    @ViewById(R.id.toolbar)
    Toolbar mToolbar;

    @Extra("id")
    int mId;

    @Extra("title")
    String mTitle;

    Article mArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("zj", "onCreate");
    }

    @AfterExtras
    public void afterExtras(){
        mArticle = new Select("title", "content")
                .from(Article.class)
                .where(Condition.column("id").eq(mId))
                .querySingle();
    }

    @AfterViews
    public void afterViews(){
        Log.i("zj", "afterViews");
        mTxtContent.setText(mArticle.getContent());
        initToolbar();

        mCollapsingToolbar.setExpandedTitleTextAppearance(R.style.CollapsingToolbarTextAppearance);
        mCollapsingToolbar.setTitle(mTitle);
    }

    void initToolbar(){
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ArticleDetailForDbActivity.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
