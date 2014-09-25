package test.com.rss.parser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import test.com.rss.items.RssLinkItem;

public class RssLinkParser extends DefaultHandler {

	private List<RssLinkItem> rssItems;

	private RssLinkItem currentItem;
	private boolean parsingTitle;
	private boolean parsingLink;
	private boolean parsingPubDate;

	public RssLinkParser() {
		rssItems = new ArrayList<RssLinkItem>();
	}

	public List<RssLinkItem> getItems() {
		return rssItems;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		if ("item".equals(qName)) {
			currentItem = new RssLinkItem();
		} else if ("title".equals(qName)) {
			parsingTitle = true;
		} else if ("link".equals(qName)) {
			parsingLink = true;
		} else if ("pubDate".equals(qName)) {
			parsingPubDate = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if ("item".equals(qName)) {
			rssItems.add(currentItem);
			currentItem = null;
		} else if ("title".equals(qName)) {
			parsingTitle = false;
		} else if ("link".equals(qName)) {
			parsingLink = false;
		} else if ("pubDate".equals(qName)) {
			parsingPubDate = false;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		if (parsingTitle) {
			if (currentItem != null)
				currentItem.setTitle(new String(ch, start, length));
		} else if (parsingLink) {
			if (currentItem != null) {
				currentItem.setLink(new String(ch, start, length));
				parsingLink = false;
			}
		} else if (parsingPubDate) {
			if (currentItem != null) {
				currentItem.setPubDate(new Date(new String(ch, start, length)));
				parsingPubDate = false;
			}
		}
	}
}
