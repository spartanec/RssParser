package test.com.rss.async;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class ImageLoader extends AsyncTask<String, Void, Bitmap> {

	private static final String TAG = "rss";

	private ImageView downloadedImage;

	public ImageLoader(ImageView image){
		downloadedImage = image;
	}
	
	@Override
	protected Bitmap doInBackground(String... urls) {
		return download_Image(urls[0]);
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		downloadedImage.setImageBitmap(result);
	}

	private Bitmap download_Image(String url) {
		Bitmap bitmap = null;
		try {
			URL aURL = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) aURL.openConnection();
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			bitmap = BitmapFactory.decodeStream(bis);
			bis.close();
			is.close();
		} catch (IOException e) {
			Log.e(TAG, "Error getting the image from server : " + e.getMessage());
		}
		
		return bitmap;
	}
}
