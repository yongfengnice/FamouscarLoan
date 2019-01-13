package com.famous.zhifu.loan.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.famous.zhifu.loan.R;


/**
 * 本地数据库管理工具类
 */
public class CityDBManager {
	private final int BUFFER_SIZE = 1024;// 缓冲文件大小
	public static final String DB_NAME = "city_cn.s3db";// 数据库名
	public static final String PACKAGE_NAME = "com.famous.zhifu.loan";// 包名
	public static final String DB_PATH = "/data"// SD卡路径
			+ Environment.getDataDirectory().getAbsolutePath() + "/"
			+ PACKAGE_NAME;
	private SQLiteDatabase database;
	private Context context;
	private File file = null;

	public CityDBManager(Context context) {
		this.context = context;
	}
	
	// 返回 SQLiteDatabase 实例
	public SQLiteDatabase getDatabase() {
		return this.database;
	}
	
	public void openDatabase() {
		this.database = this.openDatabase(DB_PATH + "/" + DB_NAME);
	}
	
	// 开启数据库，将本地db文件 用流写入SQLite
	private SQLiteDatabase openDatabase(String dbfile) {
		try {
			file = new File(dbfile);
			if (!file.exists()) {
				InputStream is = context.getResources().openRawResource(
						R.raw.city);
				
				FileOutputStream fos = new FileOutputStream(dbfile);
				if (is != null) {

				} else {
				}
				byte[] buffer = new byte[BUFFER_SIZE];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
					fos.flush();
				}
				fos.close();
				is.close();
			}
			database = SQLiteDatabase.openOrCreateDatabase(dbfile, null);
			return database;
		} catch (FileNotFoundException e) {
			Log.e("cc", "File not found");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("cc", "IO exception");
			e.printStackTrace();
		} catch (Exception e) {
			Log.e("cc", "exception " + e.toString());
		}
		return null;
	}
	
	// 关闭SQLite
	public void closeDatabase() {
		if (this.database != null)
			this.database.close();
	}
}
