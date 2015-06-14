package namofo.org.jiesehelper.util;

import android.util.Log;

import namofo.org.jiesehelper.BuildConfig;


/**
 * create by zhengjiong
 * Date: 2015-05-07
 * Time: 22:15
 */
public class Debug {
    private static final boolean DEBUG = BuildConfig.DEBUG;

    public static void i(String tag, String msg) {
        Log.i(tag, msg);
    }
}
