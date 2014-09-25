package test.com.rss.async;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import test.com.rss.db.DBLinkHandler;
import test.com.rss.items.RssLinkItem;
import test.com.rss.parser.RssLinkParser;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

public class RssLinkLoader extends AsyncTask<String, Void, Void> {

	private static final String TAG = "rss";

	private ProgressDialog pd;
	private Context context;

	private List<RssLinkItem> rssItems;
	private ArrayAdapter<RssLinkItem> arrayAdapter;
	private DBLinkHandler dbHandler;

	public RssLinkLoader(Context context, List<RssLinkItem> rssItems,
			ArrayAdapter<RssLinkItem> arrayAdapter) {
		this.context = context;
		this.rssItems = rssItems;
		this.arrayAdapter = arrayAdapter;
		dbHandler = new DBLinkHandler(context);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pd = ProgressDialog.show(context, "Working...", "Request to server",
				true, false);
	}

	@Override
	protected Void doInBackground(String... args) {

		Log.d(TAG, "Loading data from server...");
		ArrayList<RssLinkItem> items = new ArrayList<RssLinkItem>();
		try {
			URL url = new URL(args[0]);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream is = conn.getInputStream();

				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser saxParser = factory.newSAXParser();
				RssLinkParser parser = new RssLinkParser();
				saxParser.parse(is, parser);
		
				rssItems.clear();
				rssItems.addAll(parser.getItems());
			}
		} catch (Exception e) {
			Log.e(TAG, "Unbale to get rss page info. " + e.getMessage());
		}
		Log.d(TAG, "Document processing finished...");
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		arrayAdapter.notifyDataSetChanged();
		pd.dismiss();
		dbHandler.clearTable();
		dbHandler.addAll(rssItems);
	}
}
