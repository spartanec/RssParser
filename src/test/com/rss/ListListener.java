package test.com.rss;

import java.util.List;

import com.example.rss.R;

import test.com.rss.activity.MainMenu;
import test.com.rss.activity.PageContent;
import test.com.rss.activity.TabletPage;
import test.com.rss.fragments.ContentFragment;
import test.com.rss.items.RssLinkItem;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ListListener implements OnItemClickListener {

	private static final String TAG = "rss";
	
	private List<RssLinkItem> listItems;
	private Activity activity;
	private Boolean isTablet;

	public ListListener(List<RssLinkItem> listItems, Activity activity,
			Boolean isTablet) {
		this.listItems = listItems;
		this.activity = activity;
		this.isTablet = isTablet;
	}

	@Override
	public void onItemClick(AdapterView parent, View view, int pos, long id) {

		String url = Uri.parse(listItems.get(pos).getLink()).toString();

		if (!isTablet) {
			Intent intent = new Intent(activity, PageContent.class);
			intent.putExtra("url", url);
			activity.startActivity(intent);
		} else {
			Log.d(TAG,activity.getClass().toString());
			ContentFragment fragment = (ContentFragment)((TabletPage) activity)
					.getSupportFragmentManager().findFragmentById(
							R.id.content_fragment);
			fragment.showPageContent(url);
		}
	}
}
