package test.com.rss.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.rss.R;

public class PageContent extends FragmentActivity {

	private static final String TAG = "rss";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content);
		Log.d(TAG, "onCreate page content");
		/*
		 * url = getIntent().getStringExtra("url"); content = (WebView)
		 * findViewById(R.id.content); dbHandler = new
		 * DBContentHandler(PageContent.this);
		 * 
		 * ProgressDialog pd = ProgressDialog.show(PageContent.this,
		 * "Working...", "Retrieving data from db", true, false); RssContentPage
		 * page = dbHandler.getPageByLink(url); if (page == null) { new
		 * RssPageLoader(content, PageContent.this).execute(url); } else {
		 * content.loadData(page.getContent(), "text/html", "UTF-8"); }
		 * pd.dismiss();
		 */
	}

}
