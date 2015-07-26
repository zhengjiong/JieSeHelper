package namofo.org.jiesehelper.ui;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.umeng.analytics.MobclickAgent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import namofo.org.jiesehelper.R;
import namofo.org.jiesehelper.fragment.AboutFragment_;

/**
 * 關於我們
 * create by zhengjiong
 * Date: 2015-07-11
 * Time: 08:22
 */
@EActivity(R.layout.activity_replace_holder)
public class AboutActivity extends AppCompatActivity{

    @ViewById(R.id.toolbar)
    Toolbar mToolbar;

    @AfterViews
    public void afterViews(){
        initToolbar();
        getFragmentManager().beginTransaction().replace(
                R.id.content_frame,
                AboutFragment_.builder().build()
        ).commit();
    }

    private void initToolbar() {
        mToolbar.setTitle("关于");
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AboutActivity.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("设置界面"); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写)
        MobclickAgent.onResume(this);          //统计时长
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("设置界面"); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息
        MobclickAgent.onPause(this);
    }
}
