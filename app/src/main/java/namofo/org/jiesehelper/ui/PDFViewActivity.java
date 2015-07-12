package namofo.org.jiesehelper.ui;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.exception.FileNotFoundException;
import com.joanzapata.pdfview.listener.OnLoadCompleteListener;
import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import namofo.org.jiesehelper.R;
import namofo.org.jiesehelper.bean.Article;
import namofo.org.jiesehelper.util.ToastUtils;


/**
 * @Author: zhengjiong
 * Date: 2014-07-12
 * Time: 16:01
 */
@EActivity(R.layout.activity_pdfview)
public class PDFViewActivity extends AppCompatActivity {
    @ViewById(R.id.toolbar)
    Toolbar mToolbar;

    @ViewById(R.id.pdfview)
    PDFView mPdfView;

    View mProgressBar;

    @Extra("id")
    int mId;

    @Extra("title")
    String mTitle;

    Article mArticle;

    @AfterViews
    public void afterViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mPdfView = (PDFView) findViewById(R.id.pdfview);
        mProgressBar = findViewById(R.id.progress_wrapper);

        initToolbar();
        loadPDF();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setVisibility(View.GONE);
            }
        }, 3000);
    }

    @AfterExtras
    public void afterExtras() {
        mArticle = new Select("file_path", "start_page", "end_page")
                .from(Article.class)
                .where(Condition.column("id").eq(mId))
                .querySingle();
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

    protected void loadPDF() {
        if (mArticle != null) {

            try {
                PDFView.Configurator config = mPdfView.fromAsset(mArticle.getFile_path());

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
                config.swipeVertical(true);
                config.enableDoubletap(true);
                config.enableSwipe(true);
                config.load();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                ToastUtils.show(PDFViewActivity.this, "文件未找到");
            }
        }
    }

    OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
        @Override
        public void loadComplete(int i) {
            mPdfView.jumpTo(mArticle.getStart_page());
        }
    };


}
