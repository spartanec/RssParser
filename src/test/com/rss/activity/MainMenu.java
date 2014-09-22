package test.com.rss.activity;

import java.util.ArrayList;
import java.util.List;

import test.com.rss.ListListener;
import test.com.rss.async.RssLinkLoader;
import test.com.rss.db.DBLinkHandler;
import test.com.rss.items.RssLinkItem;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.rss.R;

public class MainMenu extends Activity implements OnClickListener {

	private static final String TAG = "rss";

	private ListView lView;
	private Button button;

	private static final String feedUrl = "http://www.npr.org/rss/rss.php?id=1006";
	private ArrayAdapter<RssLinkItem> arrayAdapter = null;

	private DBLinkHandler dbHandler;
	private RssLinkLoader rssTask;

	private List<RssLinkItem> rssItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		

		lView = (ListView) findViewById(R.id.lstView);
		button = (Button) findViewById(R.id.btnUpadte);
		button.setOnClickListener(this);

		dbHandler = new DBLinkHandler(MainMenu.this);
		rssItems = dbHandler.getAllRssItems();
		arrayAdapter = new ArrayAdapter<RssLinkItem>(this, R.layout.list_item,
				rssItems);
		lView.setOnItemClickListener(new ListListener(rssItems, MainMenu.this));
		lView.setAdapter(arrayAdapter);
		
		if (rssItems.isEmpty()) {
			Log.v(TAG, "Retrieving data from server");
			rssTask = new RssLinkLoader(MainMenu.this, rssItems, arrayAdapter);
			rssTask.execute(feedUrl);
		}
	}

	@Override
	public void onClick(View v) {
		rssTask = new RssLinkLoader(MainMenu.this, rssItems, arrayAdapter);
		rssTask.execute(feedUrl);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbHandler.clearTable();
		Log.v(TAG, "Destroy main activity");
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.v(TAG, "ORIENTATION CHANGED");
		rssItems.clear();
		rssItems.addAll(dbHandler.getAllRssItems());
		arrayAdapter.notifyDataSetChanged();
	}

}