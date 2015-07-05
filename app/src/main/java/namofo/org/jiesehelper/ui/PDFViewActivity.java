package namofo.org.jiesehelper.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnLoadCompleteListener;
import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;

import namofo.org.jiesehelper.R;
import namofo.org.jiesehelper.bean.Article;


/**
 * @Author: zhengjiong
 * Date: 2014-07-12
 * Time: 16:01
 */
public class PDFViewActivity extends AppCompatActivity {
    //@ViewById(R.id.toolbar)
    Toolbar mToolbar;

    /*@ViewById(R.id.pdfview)*/
    PDFView mPdfView;

    //@Extra("id")
    int mId;

    //@Extra("title")
    String mTitle;

    Article mArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview);

        mId = getIntent().getIntExtra("id", 0);
        mTitle = getIntent().getStringExtra("title");

        afterViews();
        afterExtras();
    }

    public void afterViews(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mPdfView = (PDFView) findViewById(R.id.pdfview);

        initToolbar();
    }

    public void afterExtras() {
        mArticle = new Select("file_path", "start_page", "end_page")
                .from(Article.class)
                .where(Condition.column("id").eq(mId))
                .querySingle();
        initData();
    }

    void initToolbar() {
        mToolbar.setTitle(mTitle);
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

    protected void initData() {
        if (mArticle != null) {
            PDFView.Configurator config = mPdfView
                    .fromAsset(mArticle.getFile_path())
                    .enableSwipe(true);

            /*if (mArticle.getStart_page() != 0 || mArticle.getEnd_page() != 0){
                int[] pages = new int[mArticle.getEnd_page() - mArticle.getStart_page() + 1];
                for (int i = 0; i < pages.length; i++){
                    pages[i] = mArticle.getStart_page()+i;
                }
                config.pages(pages);//要显示的页
                config.defaultPage(mArticle.getStart_page());//默认从第一页开始
            }else{
                //config.defaultPage(0);//默认从第一页开始
            }*/
            config.onLoad(onLoadCompleteListener);
            config.load();
        }
    }

    OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
        @Override
        public void loadComplete(int i) {
            mPdfView.jumpTo(mArticle.getStart_page());
        }
    };


}
