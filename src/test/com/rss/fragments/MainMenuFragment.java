package test.com.rss.fragments;

import java.util.List;

import test.com.rss.ListListener;
import test.com.rss.async.RssLinkLoader;
import test.com.rss.db.DBLinkHandler;
import test.com.rss.items.RssLinkItem;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.rss.R;

public class MainMenuFragment extends Fragment {

	private static final String TAG = "rss";

	private static final String feedUrl = "http://www.npr.org/rss/rss.php?id=1006";
	private ArrayAdapter<RssLinkItem> arrayAdapter = null;

	private ListView lView;

	private DBLinkHandler dbHandler;
	private RssLinkLoader rssTask;
	private List<RssLinkItem> rssItems;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.main_fragment, container);
		lView = (ListView) view.findViewById(R.id.lstView);

		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Context context = getActivity();
		dbHandler = new DBLinkHandler(context);
		rssItems = dbHandler.getAllRssItems();
		Boolean isTablet = getActivity().getIntent().getBooleanExtra("tablet",
				false);
		arrayAdapter = new ArrayAdapter<RssLinkItem>(context,
				R.layout.list_item, rssItems);
		lView.setOnItemClickListener(new ListListener(rssItems, getActivity(),
				isTablet));
		lView.setAdapter(arrayAdapter);
	
		if (rssItems.isEmpty()) {
			Log.v(TAG, "Retrieving data from server");
			rssTask = new RssLinkLoader(context, rssItems, arrayAdapter);
			rssTask.execute(feedUrl);
		}
	}

	public void updateLinks() {
		rssTask = new RssLinkLoader(getActivity(), rssItems, arrayAdapter);
		rssTask.execute(feedUrl);
	}

	/*
	 * @Override public void onAttach(Activity activity) {
	 * super.onAttach(activity); Log.d(TAG, "Fragment2 onAttach"); }
	 * 
	 * public void onCreate(Bundle savedInstanceState) {
	 * super.onCreate(savedInstanceState);
	 * 
	 * }
	 */
}
