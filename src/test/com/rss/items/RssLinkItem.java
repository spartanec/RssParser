package test.com.rss.items;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RssLinkItem {

	private String title;
	private Date pubDate;
	private String link;

	public RssLinkItem() {
	}

	public RssLinkItem(String title, Date pubDate, String link) {
		this.title = title;
		this.pubDate = pubDate;
		this.link = link;
	}

	public String getTitle() {
		return this.title;
	}

	public String getLink() {
		return this.link;
	}

	public Date getPubDate() {
		return this.pubDate;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public String toString() {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy - hh:mm:ss");
		String result = getTitle() + "  ( " + sdf.format(this.getPubDate())
				+ " )";
		return result;
	}
}
