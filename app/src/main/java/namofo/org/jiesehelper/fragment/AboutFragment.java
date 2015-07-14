package namofo.org.jiesehelper.fragment;

import android.content.Intent;
import android.net.Uri;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.PreferenceByKey;
import org.androidannotations.annotations.PreferenceClick;
import org.androidannotations.annotations.PreferenceScreen;

import namofo.org.jiesehelper.R;

/**
 * create by zhengjiong
 * Date: 2015-07-11
 * Time: 09:19
 */
@PreferenceScreen(R.xml.about)
@EFragment
public class AboutFragment extends PreferenceFragment {

    @PreferenceByKey(R.string.preference_url)
    Preference mPreferenceUrl;

    @PreferenceByKey(R.string.preference_yy)
    Preference mPreferenceYY;

    @PreferenceByKey(R.string.preference_qq)
    Preference mPreferenceQQ;

    @PreferenceClick(R.string.preference_url)
    void onPreferenceClick(Preference preference) {

        String summary = preference.getSummary().toString();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(summary.toString()));
        startActivity(intent);
    }
}
