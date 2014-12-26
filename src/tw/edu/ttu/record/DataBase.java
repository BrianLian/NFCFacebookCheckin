package tw.edu.ttu.record;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

	public DataBase(Context context, String dataBaseName, CursorFactory factory, int version) {
		super(context, dataBaseName, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table checkin(" +
				   "id INTEGER PRIMARY KEY AUTOINCREMENT," +
				   "store_name TEXT," +
				   "coupon_msg TEXT," +
				   "feedback_url TEXT," +
				   "is_feedback INTEGER," +
				   "checkin_datetime TEXT" +
				   ")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
