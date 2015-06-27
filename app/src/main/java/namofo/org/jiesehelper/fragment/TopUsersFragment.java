package namofo.org.jiesehelper.fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import namofo.org.jiesehelper.R;

/**
 * create by zhengjiong
 * Date: 2015-06-22
 * Time: 15:01
 */
@EFragment(R.layout.top_users_fragment_layout)
public class TopUsersFragment extends NavigationBaseFragment {

    @AfterViews
    public void afterViews() {
        initToolbar("排行榜");
    }

}
