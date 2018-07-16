package com.jinoos.countque;

public class CounterQueItem implements LruLinkItem<String>, OrderedLinkItem<String> {
	private String key = null;
	private TimeQue timeQue = null;
	
	private CounterQueItem upperItem = null;
	private CounterQueItem lowerItem = null;
	private CounterQueItem newerItem = null;
	private CounterQueItem olderItem = null;
	
	private CounterQueItem() {
	}
	
	public CounterQueItem(String key, int maxSlotCount, int term) {
		this.key = key;
		this.timeQue = new TimeQue(maxSlotCount, term);
	}
	
	public long upCount() {
		return timeQue.beat();
	}
	
	public long upCount(int countIncrese) {
		return timeQue.beat(countIncrese);
	}
	
	public long getCount() {
		return timeQue.count(true);
	}

	public long getCount(boolean upToDate) {
		return timeQue.count(upToDate);
	}

	public boolean isGreaterThan(OrderedLinkItem<String> item) {
		return getCount() > ((CounterQueItem) item).getCount();
	}

	public OrderedLinkItem<String> getUpperItem() {
		return upperItem;
	}

	public void setUpperItem(OrderedLinkItem<String> item) {
		upperItem = (CounterQueItem) item;
	}

	public OrderedLinkItem<String> getLowerItem() {
		return lowerItem;
	}

	public void setLowerItem(OrderedLinkItem<String> item) {
		lowerItem = (CounterQueItem) item;
	}

	public String getKey() {
		return key;
	}

	public LruLinkItem<String> getNewerItem() {
		return newerItem;
	}

	public void setNewerItem(LruLinkItem<String> item) {
		newerItem = (CounterQueItem) item;
	}

	public LruLinkItem<String> getOlderItem() {
		return olderItem;
	}

	public void setOlderItem(LruLinkItem<String> item) {
		olderItem = (CounterQueItem) item;
	}
	
	public long getLastUpdateTime() {
		return timeQue.getLastUpdateTime();
	}

	public long getLong() {
		return timeQue.count();
	}
	
	public CounterQueItem copy() {
		CounterQueItem item = new CounterQueItem();
		item.key = String.valueOf(this.key);
		item.timeQue = this.timeQue.copy();
		return item;
	}
}
