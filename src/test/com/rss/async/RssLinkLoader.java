package test.com.rss.async;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import test.com.rss.db.DBLinkHandler;
import test.com.rss.items.RssLinkItem;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

public class RssLinkLoader extends AsyncTask<String, Void, Void> {

	private static final String TAG = "rss";

	private static final String ITEM_TAG = "item";
	private static final String LINK_TAG = "link";
	private static final String PUBDATE_TAG = "pubDate";
	private static final String TITLE_TAG = "title";

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

				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();

				Document document = db.parse(is);
				Element element = document.getDocumentElement();

				NodeList nodeList = element.getElementsByTagName(ITEM_TAG);

				if (nodeList.getLength() > 0) {
					for (int i = 0; i < nodeList.getLength(); i++) {

						Element entry = (Element) nodeList.item(i);

						Element title = (Element) entry.getElementsByTagName(
								TITLE_TAG).item(0);
						Element pubDate = (Element) entry.getElementsByTagName(
								PUBDATE_TAG).item(0);
						Element link = (Element) entry.getElementsByTagName(
								LINK_TAG).item(0);

						String rssTitle = title.getFirstChild().getNodeValue();
						Date rssPubDate = new Date(pubDate.getFirstChild()
								.getNodeValue());
						String rssLink = link.getFirstChild().getNodeValue();

						RssLinkItem rssItem = new RssLinkItem(rssTitle, rssPubDate,
								rssLink);

						items.add(rssItem);
					}
					rssItems.clear();
					rssItems.addAll(items);
				}
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
