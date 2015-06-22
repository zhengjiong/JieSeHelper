package namofo.org.jiesehelper.app;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

import java.io.IOException;

import namofo.org.jiesehelper.util.DBUtil;

/**
 * create by zhengjiong
 * Date: 2015-06-22
 * Time: 22:14
 */
public class AppContext extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);

        try {
            DBUtil.copyDataBase(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
