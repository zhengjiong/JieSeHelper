package namofo.org.jiesehelper.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.HashMap;

import namofo.org.jiesehelper.R;
import namofo.org.jiesehelper.constants.Constants;
import namofo.org.jiesehelper.http.OkHttpUtils;

/**
 * create by zhengjiong
 * Date: 2015-07-06
 * Time: 08:21
 */
@EActivity(R.layout.net_article_list_fragment_layout)
public class NetArticleListFragment extends Fragment{

    @ViewById(R.id.progress_wrapper)
    View mProgressWrapper;

    @ViewById(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @AfterViews
    public void aftetViews() {

        HashMap<String, String> params = new HashMap<>();
        params.put("key", "getAppNews");
        params.put("type", "new");
        params.put("page", "0");

        OkHttpUtils.get(Constants.HTTP.HOST_URL, params, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                mProgressWrapper.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                mProgressWrapper.setVisibility(View.GONE);
                if (response.isSuccessful()) {

                }
            }
        });
    }

}
