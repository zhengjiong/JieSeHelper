package namofo.org.jiesehelper.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import namofo.org.jiesehelper.constants.AppDatabase;

/**
 * @author zhengjiong 2014-5-20 上午6:45:46
 */
public class DBUtil {

	public static String COPY_DB_NAME = "jiesehelper.db";

	/**
	 * 判斷數據庫是否存在
	 * @param context
	 * @return
	 */
	public static boolean checkDataBase(Context context) {
		String packageName = context.getPackageName();
		String dbPath = "/data/data/" + packageName + "/databases/";

		SQLiteDatabase db = null;
		try {
			String dbFileName = dbPath + COPY_DB_NAME;
            Log.i("zj", "dbFileName = " + dbFileName);
            db = SQLiteDatabase.openDatabase(dbFileName, null, SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
            Log.i("zj", "數據庫不存在");
        }
        if (db != null) {
			db.close();
		}
		return db != null ? true : false;
	}

	/**
	 * 檢查數據庫文件是否存在
	 * @param context
	 * @return
	 */
	public static boolean checkDataBase2(Context context) {
		File dbFile = context.getDatabasePath(AppDatabase.NAME+".db");
		return dbFile.exists();
	}

	/**
	 * 複製數據庫文件
	 * @param context
	 * @throws IOException
	 */
	public static void copyDataBase(Context context) throws IOException {
		if (!checkDataBase2(context)) {
			File dbFile = context.getDatabasePath(AppDatabase.NAME+".db");
			if (!dbFile.getParentFile().exists()){
				//創建databases目錄
				dbFile.getParentFile().mkdir();
			}

			//待複製的數據庫文件
			String copyDatabaseFilenames = dbFile.getPath();

			FileOutputStream os = new FileOutputStream(copyDatabaseFilenames);// 得到数据库文件的写入流
			InputStream is = context.getAssets().open(COPY_DB_NAME);

			byte[] buffer = new byte[8192];
			int count = 0;
			while ((count = is.read(buffer)) > 0) {
				os.write(buffer, 0, count);
				os.flush();
			}
			is.close();
			os.close();
		}
	}
}
