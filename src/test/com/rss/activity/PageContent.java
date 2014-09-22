package test.com.rss.activity;

import test.com.rss.async.RssPageLoader;
import test.com.rss.db.DBContentHandler;
import test.com.rss.items.RssContentPage;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.rss.R;

public class PageContent extends Activity {

	private static final String TAG = "rss";

	private WebView content;
	private String url;
	private DBContentHandler dbHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content);

		url = getIntent().getStringExtra("url");
		content = (WebView) findViewById(R.id.content);
		dbHandler = new DBContentHandler(PageContent.this);

		ProgressDialog pd = ProgressDialog.show(PageContent.this, "Working...",
				"Retrieving data from db", true, false);
		RssContentPage page = dbHandler.getPageByLink(url);
		if (page == null) {
			new RssPageLoader(content, PageContent.this).execute(url);
		} else {
			content.loadData(page.getContent(), "text/html", "UTF-8");
		}
		pd.dismiss();
	}

	public void mainMenu(View v) {

		Intent intent = new Intent(PageContent.this, MainMenu.class);
		startActivity(intent);
	}

	public void clearPageCache(View v) {
		dbHandler.deletePageByLink(url);
		Toast.makeText(PageContent.this, "Page cache removed",
				Toast.LENGTH_SHORT).show();
	}

	public void clearAllCache(View v) {
		dbHandler.clearTable();
		Toast.makeText(PageContent.this, "All pages cache has been removed",
				Toast.LENGTH_SHORT).show();
	}

}
