package test.com.rss;

import java.util.List;

import test.com.rss.activity.PageContent;
import test.com.rss.items.RssLinkItem;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ListListener implements OnItemClickListener {
	
    private List<RssLinkItem> listItems;
    private Activity activity;
    
 
    public ListListener(List<RssLinkItem> listItems, Activity activity) {
        this.listItems = listItems;
        this.activity  = activity;
    }
 
    @Override
    public void onItemClick(AdapterView parent, View view, int pos, long id) {
        Intent intent = new Intent(activity, PageContent.class);
        intent.putExtra("url", Uri.parse(listItems.get(pos).getLink()).toString());    
        activity.startActivity(intent);
    }
}
