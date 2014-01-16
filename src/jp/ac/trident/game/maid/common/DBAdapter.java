package jp.ac.trident.game.maid.common;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Debug;
import android.util.Log;

public class DBAdapter {
	// 
	protected final Context context;
	protected DatabaseHelper dbHelper;
	protected SQLiteDatabase db;
	
	// DB設定
	static final String DATABASE_NAME = "CafeDeMaid.db";
	static final int DATABASE_VERSION = 1;

	// mydataテーブル
	public static final String TABLE_MYDATA = "mydata";
	public static final String MYDATA_ID = "_id";
	public static final String MYDATA_NAME = "name";
	public static final String MYDATA_X = "x";
	public static final String MYDATA_Y = "y";
	public static final String MYDATA_ROTATE = "rotate";

	// foodテーブル
	public static final String TABLE_FOOD = "food";
	public static final String FOOD_ID = "_id";
	public static final String FOOD_NAME = "name";
	public static final String FOOD_WEIGHT = "weight";
	public static final String FOOD_BASE_COOKING_TIME = "baseCookingTime";
	public static final String FOOD_PRICE = "price";


	public DBAdapter(Context context){
		this.context = context;
		dbHelper = new DatabaseHelper(this.context);
	}

	//
	// SQLiteOpenHelper
	//

	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(
					"CREATE TABLE " + TABLE_MYDATA + " ("
							+ MYDATA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
							+ MYDATA_NAME + " TEXT NOT NULL,"
							+ MYDATA_X + " FLOAT, "
							+ MYDATA_Y + " FLOAT, "
							+ MYDATA_ROTATE + " FLOAT );");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYDATA);
			onCreate(db);
		}

	}

	//
	// Adapter Methods
	//

	public DBAdapter open() {
		db = dbHelper.getWritableDatabase();
		return this;
	}


	public void close(){
		dbHelper.close();
	}


	//
	// App Methods
	//


	public boolean deleteAllData(){
		return db.delete(TABLE_MYDATA, null, null) > 0;
	}

	public boolean deleteData(int id){
		return db.delete(TABLE_MYDATA, MYDATA_ID + "=" + id, null) > 0;
	}

	public Cursor getAllData(){
		return db.query(TABLE_MYDATA, null, null, null, null, null, null);
	}

	public void saveData(String name,float x,float y,float rotate){
		open();
		ContentValues values = new ContentValues();
		values.put(MYDATA_NAME, name);
		values.put(MYDATA_X,x);
		values.put(MYDATA_Y,y);
		values.put(MYDATA_ROTATE,rotate);

		db.insertOrThrow(TABLE_MYDATA, null, values);
		close();
	}

	public void loadData(){
		HashMap<String,Data> map = new HashMap<String,Data>();
		// Read
		open();
		Cursor c = getAllData();

		if(c.moveToFirst()){
			do {
				Data data = new Data(
						c.getInt(c.getColumnIndex(MYDATA_ID)),
						c.getString(c.getColumnIndex(MYDATA_NAME)),
						c.getFloat(c.getColumnIndex(MYDATA_X)),
						c.getFloat(c.getColumnIndex(MYDATA_Y)),
						c.getFloat(c.getColumnIndex(MYDATA_ROTATE)));

				map.put("" + data.getId(),data);
			} while(c.moveToNext());
		}

		close();
		for(int i = 0;i < map.size();i++){
			if(map.get("" + (i + 1)).equals(null)){
				continue;
			}
			System.out.println(map.get("" + (i + 1)).getName());
		}
	}
}
