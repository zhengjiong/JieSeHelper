package namofo.org.jiesehelper.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import namofo.org.jiesehelper.R;
import namofo.org.jiesehelper.ui.MainActivity;

/**
 * create by zhengjiong
 * Date: 2015-06-22
 * Time: 15:59
 */
@EFragment
public class BaseFragment extends Fragment{

    @ViewById(R.id.toolbar)
    public Toolbar mToolbar;

    public void initToolbar(String title){
        mToolbar.setTitle(title);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        MainActivity mainActivity =(MainActivity)getActivity();

        mainActivity.initDrawerLayout(mToolbar);
        //mainActivity.mDrawerToggle.syncState();
        //mainActivity.mDrawerLayout.postInvalidate();
        mToolbar.setNavigationIcon(R.mipmap.ic_menu_white);
    }
}
