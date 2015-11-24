package namofo.org.jiesehelper.app;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.raizlabs.android.dbflow.DatabaseHelperListener;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.tencent.bugly.crashreport.CrashReport;
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
        //getDeviceInfo(this);
        CrashReport.initCrashReport(this, "900012747", false);
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

    public static String getDeviceInfo(Context context) {
        try{
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            String device_id = tm.getDeviceId();

            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            if( TextUtils.isEmpty(device_id) ){
                device_id = mac;
            }

            if( TextUtils.isEmpty(device_id) ){
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
            }

            json.put("device_id", device_id);

            return json.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
