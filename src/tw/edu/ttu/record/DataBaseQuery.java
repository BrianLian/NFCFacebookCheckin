package tw.edu.ttu.record;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataBaseQuery {
	private static final String TAG = "nfccheckin";
	private SQLiteDatabase db;
	private String DB_NAME = "nfc";
	private int DB_VERSION = 1;
	
	public DataBaseQuery(Context contex) {
		DataBase dataBase = new DataBase(contex, DB_NAME, null, DB_VERSION);
		db = dataBase.getWritableDatabase();
	}
	
	public void insert(String store_name, String coupon_msg, String feedback_url, String checkin_datetime) {
		
		ContentValues contentValues = new ContentValues();
		contentValues.put("store_name", store_name);
		contentValues.put("coupon_msg", coupon_msg);
		contentValues.put("feedback_url", feedback_url);
		contentValues.put("is_feedback", 0);
		contentValues.put("checkin_datetime", checkin_datetime);
		
		db.insert("checkin", null, contentValues);
		
/*		String sqlString =
				"INSERT INTO 'checkin' "
				+ "('store_name', 'coupon_msg', 'feedback_url', 'is_feedback', 'checkin_datetime') "
				+ "VALUES ('" + store_name + "', '" + coupon_msg + "', '" + feedback_url + "', 0, '" + checkin_datetime + "')";
		db.execSQL(sqlString);
*/
	}
	
	public Cursor selectAll() {
		Cursor cursor = db.query("checkin", null, null, null, null, null, null);
				
		return cursor; 
	}
	
	public Cursor selectById(int id) {
		String[] argStr = {String.valueOf(id)};
		
		Cursor cursor = db.query("checkin", null, "id=?", argStr, null, null, null);
				
		return cursor; 
	}	
	
	public void setIsFeedback(int id, int is_feedback) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("is_feedback", 1);
		String[] argStr = {String.valueOf(id)}; 
		int i = db.update("checkin", contentValues, "id=?", argStr);
		Log.d(TAG, "setIsFeedback: " + i);
		
	}
}
