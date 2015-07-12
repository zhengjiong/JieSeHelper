package namofo.org.jiesehelper.app;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

import net.danlew.android.joda.JodaTimeAndroid;

import org.androidannotations.annotations.EApplication;

/**
 * create by zhengjiong
 * Date: 2015-06-22
 * Time: 22:14
 */
@EApplication
public class AppContext extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);

        /*boolean delResult = deleteDatabase("jiesehelper.db");
        Log.i("zj", "delDatabase = " + delResult);*/
        FlowManager.init(this);
        /*try {
            DBUtil.copyDataBase(this);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
