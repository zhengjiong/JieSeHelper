package namofo.org.jiesehelper.http;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * create by zhengjiong
 * Date: 2015-07-04
 * Time: 11:32
 */
public class OkHttpUtils {
    private OkHttpUtils(){}
    private static OkHttpClient mOkHttpClient = new OkHttpClient();

    public static void get(String url, HashMap<String, ?> params, Callback callback) {
        String paramsStr = generateGetParams(params);
        Request request = getRequest(url + paramsStr);
        enqueue(request, callback);

    }

    public static void enqueue(Request request, Callback callback) {
        mOkHttpClient.newCall(request).enqueue(callback);
    }

    public static Request getRequest(String url){
       return new Request.Builder().url(url).build();
    }

    /**
     *
     * @param params
     * @return
     */
    public static String generateGetParams(HashMap<String, ?> params) {
        StringBuilder url = new StringBuilder("?");
        Iterator<? extends Map.Entry<String, ?>> i = params.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry<String, ?> entry = i.next();
            String key = entry.getKey();
            String value = entry.getValue().toString();

            url.append(key);
            url.append("=");
            url.append(value);
            if (i.hasNext()) {
                url.append("&");
            }
        }
        return url.toString();
    }
}
