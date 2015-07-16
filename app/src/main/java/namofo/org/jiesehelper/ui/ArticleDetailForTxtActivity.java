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
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import namofo.org.jiesehelper.R;
import namofo.org.jiesehelper.bean.Article;
import namofo.org.jiesehelper.util.FileUtils;

/**
 * 文章詳情(Txt文檔)
 * create by zhengjiong
 * Date: 2015-06-30
 * Time: 08:35
 */
@EActivity(R.layout.article_detail_db_layout)
public class ArticleDetailForTxtActivity extends AppCompatActivity{

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
        mArticle = new Select("subject", "file_path")
                .from(Article.class)
                .where(Condition.column("id").eq(mId))
                .querySingle();
    }

    @AfterViews
    public void afterViews(){
        Log.i("zj", "afterViews");
        initToolbar();

        mCollapsingToolbar.setExpandedTitleTextAppearance(R.style.CollapsingToolbarTextAppearance);
        mCollapsingToolbar.setTitle(mTitle);

        loadTxt();
    }

    @Background
    void loadTxt(){
        String content = FileUtils.readFileFromAsset("txt/jieweiliangyao/" + mArticle.getFile_path(), this);
        setTxt(content);
    }

    @UiThread
    void setTxt(String content){
        mTxtContent.setText(content);
    }

    void initToolbar(){
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ArticleDetailForTxtActivity.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
