
package test.com.rss.activity;

import test.com.rss.fragments.MainMenuFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.example.rss.R;

public class MainMenu extends FragmentActivity {

	private static final String TAG = "rss";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG,"Create new activity");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void updateLinks(View v) {
		MainMenuFragment mainMenuFragment = (MainMenuFragment) getSupportFragmentManager()
				.findFragmentById(R.id.link_fragment);
		mainMenuFragment.updateLinks();
	}

	public void showTabletView(View v) {
		Intent intent = new Intent(MainMenu.this, TabletPage.class);
		intent.putExtra("tablet", true);  
		startActivity(intent);
	}

	/*
	  @Override protected void onDestroy() { super.onDestroy();
	  dbHandler.clearTable(); Log.v(TAG, "Destroy main activity"); }
	  
	 @Override public void onConfigurationChanged(Configuration newConfig) {
	 super.onConfigurationChanged(newConfig); Log.v(TAG,
	  "ORIENTATION CHANGED"); rssItems.clear();
	  rssItems.addAll(dbHandler.getAllRssItems());
	  arrayAdapter.notifyDataSetChanged(); }
	 */
}