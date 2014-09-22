package test.com.rss.db;

import test.com.rss.items.RssContentPage;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBContentHandler {

	private static final String TAG = "rss";

	private static final String LINK_COLUMN = "link";
	private static final String CONTENT_COLUMN = "content";
	private static final String TABLE_NAME = "contenttable";
	private static final String DB_NAME = "rssDB";

	private SQLiteDatabase db;
	private ContentValues cv;

	private DBHelper dbHelper;

	public DBContentHandler(Context context) {
		dbHelper = new DBHelper(context);
		cv = new ContentValues();
		createTableIfNotExist();
	}

	public void addPage(RssContentPage item) {

		db = dbHelper.getWritableDatabase();
		Log.d(TAG, "Insert data in table " + TABLE_NAME);
		cv.put(LINK_COLUMN, item.getLink());
		cv.put(CONTENT_COLUMN, item.getContent());
		db.insert(TABLE_NAME, null, cv);
		db.close();
	}

	public void clearTable() {

		db = dbHelper.getWritableDatabase();
		Log.d(TAG, "Clear table " + TABLE_NAME);
		int clearCount = db.delete(TABLE_NAME, null, null);
		Log.d(TAG, "deleted rows count = " + clearCount);
		db.close();
	}

	public RssContentPage getPageByLink(String url) {

		db = dbHelper.getReadableDatabase();
		Log.d(TAG, "Get page from table " + TABLE_NAME);
		Cursor cursor = db.query(TABLE_NAME, new String[] { LINK_COLUMN,
				CONTENT_COLUMN }, LINK_COLUMN + "=?", new String[] { url },
				null, null, null, null);
		
		if (cursor != null) {
			if (!cursor.moveToFirst()){
				return null;
			}
		} else {
			return null;
		}

		Integer linkIndex = cursor.getColumnIndex(LINK_COLUMN);
		Integer contentIndex = cursor.getColumnIndex(CONTENT_COLUMN);
		
		RssContentPage item = new RssContentPage(cursor.getString(linkIndex),
				cursor.getString(contentIndex));

		db.close();
		return item;
	}

	public void deletePageByLink(String url) {

		db = dbHelper.getWritableDatabase();
		Log.d(TAG, "Delete page from table " + TABLE_NAME);
		db.delete(TABLE_NAME, LINK_COLUMN + " = ?", new String[] { url });
		dbHelper.close();
	}
	
	private void createTableIfNotExist(){		
		db = dbHelper.getWritableDatabase();
		db.execSQL("create table if not exists " + TABLE_NAME + " ("
				+ "id integer primary key autoincrement," + LINK_COLUMN
				+ " text," + CONTENT_COLUMN + " text" + ");");
		db.close();
	}

	
	
	class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			super(context, DB_NAME, null, 1);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			Log.d(TAG, "Create table " + TABLE_NAME);

			db.execSQL("create table " + TABLE_NAME + " ("
					+ "id integer primary key autoincrement," + LINK_COLUMN
					+ " text," + CONTENT_COLUMN + " text" + ");");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			
			Log.d(TAG, "Upgrade table " + TABLE_NAME);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
	}
}
