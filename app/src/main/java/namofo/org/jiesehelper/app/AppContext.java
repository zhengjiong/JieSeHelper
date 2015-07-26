package namofo.org.jiesehelper.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.raizlabs.android.dbflow.DatabaseHelperListener;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.umeng.analytics.MobclickAgent;

import org.androidannotations.annotations.EApplication;

import namofo.org.jiesehelper.constants.AppDatabase;

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
        //JodaTimeAndroid.init(this);
        MobclickAgent.openActivityDurationTrack(false);//禁止默认的页面统计方式，这样将不会再自动统计Activity。
        /*boolean delResult = deleteDatabase("jiesehelper.db");
        Log.i("zj", "delDatabase = " + delResult);*/

        FlowManager.setDatabaseListener(AppDatabase.NAME, new DatabaseHelperListener() {
            @Override
            public void onOpen(SQLiteDatabase sqLiteDatabase) {
                Log.i("zj", "onOpen " + sqLiteDatabase.getVersion());
            }

            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {
                Log.i("zj", "onCreate " + sqLiteDatabase.getVersion());
            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
                Log.i("zj", "onUpgrade i=" + i + " ,i1" + i1);
            }
        });
        int version = FlowManager.getDatabase(AppDatabase.NAME).getDatabaseVersion();
        Log.i("zj", "version=" + version);
        FlowManager.init(this);
        /*try {
            DBUtil.copyDataBase(this);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
