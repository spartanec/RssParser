package test.com.rss.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import test.com.rss.items.RssLinkItem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBLinkHandler {

	private static final String TAG = "rss";

	private static final String LINK_COLUMN = "link";
	private static final String PUBDATE_COLUMN = "pubDate";
	private static final String TITLE_COLUMN = "title";
	private static final String TABLE_NAME = "linktable";
	private static final String DB_NAME = "rssDB";

	private SQLiteDatabase db;
	private ContentValues cv;

	private DBHelper dbHelper;

	public DBLinkHandler(Context context) {
		dbHelper = new DBHelper(context);
		cv = new ContentValues();
		createTableIfNotExist();
	}

	public void addAll(List<RssLinkItem> items) {
		db = dbHelper.getWritableDatabase();
		Log.d(TAG, "Insert data in table " + TABLE_NAME);
		for (RssLinkItem item : items) {
			cv.put(TITLE_COLUMN, item.getTitle());
			cv.put(PUBDATE_COLUMN, item.getPubDate().toString());
			cv.put(LINK_COLUMN, item.getLink());
			db.insert(TABLE_NAME, null, cv);
		}
		db.close();
	}

	public void clearTable() {
		db = dbHelper.getWritableDatabase();
		Log.d(TAG, "Clear table " + TABLE_NAME);
		int clearCount = db.delete(TABLE_NAME, null, null);
		Log.d(TAG, "deleted rows count = " + clearCount);
		db.close();
	}

	public List<RssLinkItem> getAllRssItems() {
		db = dbHelper.getWritableDatabase();
		List<RssLinkItem> rssItems = new ArrayList<>();
		Log.d(TAG, "Get all from table " + TABLE_NAME);

		Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
		if (c.moveToFirst()) {
			int titleColIndex = c.getColumnIndex(TITLE_COLUMN);
			int pubDateColIndex = c.getColumnIndex(PUBDATE_COLUMN);
			int linkColIndex = c.getColumnIndex(LINK_COLUMN);
			do {
				rssItems.add(new RssLinkItem(c.getString(titleColIndex),
						new Date(c.getString(pubDateColIndex)), c
								.getString(linkColIndex)));
			} while (c.moveToNext());
		}
		db.close();
		return rssItems;
	}

	private void createTableIfNotExist() {

		db = dbHelper.getWritableDatabase();
		db.execSQL("create table if not exists " + TABLE_NAME + " ("
				+ "id integer primary key autoincrement, " + TITLE_COLUMN
				+ " text, " + PUBDATE_COLUMN + " text, " + LINK_COLUMN
				+ " text" + ");");

		db.close();
	}

	class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			super(context, DB_NAME, null, 1);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			Log.d(TAG, "Create Table " + TABLE_NAME);

			db.execSQL("create table " + TABLE_NAME + " ("
					+ "id integer primary key autoincrement, " + TITLE_COLUMN
					+ " text, " + PUBDATE_COLUMN + " text, " + LINK_COLUMN
					+ " text" + ");");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
	}
}
