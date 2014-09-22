package test.com.rss.async;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import test.com.rss.db.DBContentHandler;
import test.com.rss.items.RssContentPage;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;

public class RssPageLoader extends AsyncTask<String, Void, Void> {

	private static final String TAG = "rss";
	
	private ProgressDialog pd;
	private Context context;

	private DBContentHandler dbHandler;
	private WebView content;
	private String pageContent;
	private RssContentPage page;

	public RssPageLoader(WebView content, Context context) {
		this.content = content;
		this.context = context;
		dbHandler = new DBContentHandler(context);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pd = ProgressDialog.show(context, "Working...", "Request to server",
				true, false);
	}

	@Override
	protected Void doInBackground(String... urls) {

		Log.d(TAG, "Loading page from server. " + urls[0]);
		HttpResponse response = null;
		HttpGet httpGet = null;
		HttpClient mHttpClient = null;

		try {
			if (mHttpClient == null) {
				mHttpClient = new DefaultHttpClient();
			}

			httpGet = new HttpGet(urls[0]);

			response = mHttpClient.execute(httpGet);
			pageContent = EntityUtils.toString(response.getEntity(), "UTF-8");
			page = new RssContentPage(urls[0], pageContent);
		} catch (IOException e) {
			Log.e(TAG, "Unable to load page content");
		}
		return null;
	}

	
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		content.loadData(pageContent, "text/html", "UTF-8");
		pd.dismiss();
		dbHandler.addPage(page); 
	}
}
