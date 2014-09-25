package test.com.rss.fragments;

import test.com.rss.activity.MainMenu;
import test.com.rss.async.RssPageLoader;
import test.com.rss.db.DBContentHandler;
import test.com.rss.items.RssContentPage;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.example.rss.R;

public class ContentFragment extends Fragment implements OnClickListener {

	private static final String TAG = "rss";

	private WebView content;
	private String url;
	private DBContentHandler dbHandler;

	private Button btnMenu;
	private Button btnClearCache;
	private Button btnClearPageCache;
	private Bundle webViewBundle;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.content_fragment, container);
		content = (WebView) view.findViewById(R.id.content);

		btnMenu = (Button) view.findViewById(R.id.btnMenu);
		btnClearCache = (Button) view.findViewById(R.id.btnClearCache);
		btnClearPageCache = (Button) view.findViewById(R.id.btnClearPageCache);

		btnMenu.setOnClickListener(this);
		btnClearCache.setOnClickListener(this);
		btnClearPageCache.setOnClickListener(this);

		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Log.v(TAG, "Create fragment Content");

		if (webViewBundle != null) {
			content.restoreState(webViewBundle);
		}
		
		setRetainInstance(true);

		Context context = getActivity();
		url = getActivity().getIntent().getStringExtra("url");
		if (url != null) {
			dbHandler = new DBContentHandler(context);

			ProgressDialog pd = ProgressDialog.show(context, "Working...",
					"Retrieving data from db", true, false);
			RssContentPage page = dbHandler.getPageByLink(url);
			if (page == null) {
				new RssPageLoader(content, context).execute(url);
			} else {
				content.loadData(page.getContent(), "text/html", "UTF-8");
			}
			pd.dismiss();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		webViewBundle = new Bundle();
		content.saveState(webViewBundle);
		Log.v(TAG, "Save page Content");
	}

	public void showPageContent(String url) {

		Context context = getActivity();
		if (url != null) {
			dbHandler = new DBContentHandler(context);

			ProgressDialog pd = ProgressDialog.show(context, "Working...",
					"Retrieving data from db", true, false);
			RssContentPage page = dbHandler.getPageByLink(url);
			if (page == null) {
				new RssPageLoader(content, context).execute(url);
			} else {
				content.loadData(page.getContent(), "text/html", "UTF-8");
			}
			pd.dismiss();
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btnMenu:
			Intent intent = new Intent(getActivity(), MainMenu.class);
			intent.putExtra("tablet", false);
			startActivity(intent);
			break;
		case R.id.btnClearCache:
			dbHandler.clearTable();
			Toast.makeText(getActivity(), "All pages cache has been removed",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.btnClearPageCache:
			dbHandler.deletePageByLink(url);
			Toast.makeText(getActivity(), "Page cache removed",
					Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

}
