package com.jinoos.util.countque;


public class CounterQueItem implements LruLinkItem<String>, OrderedLinkItem<String> {
	private String key = null;
	private TimeQue tq = null;
	
	private CounterQueItem upper = null;
	private CounterQueItem lower = null;
	private CounterQueItem newer = null;
	private CounterQueItem older = null;
	
	private CounterQueItem() {
	}
	
	public CounterQueItem(String key, int maxSlotCount, int term) {
		this.key = key;
		this.tq = new TimeQue(maxSlotCount, term);
	}
	
	public long upCount() {
		return tq.beat();
	}
	
	public long upCount(int countIncrese) {
		return tq.beat(countIncrese);
	}
	
	public long getCount() {
		return tq.count(true);
	}

	public long getCount(boolean upToDate) {
		return tq.count(upToDate);
	}

	public boolean isGreaterThan(OrderedLinkItem<String> item) {
		return getCount() > ((CounterQueItem) item).getCount();
	}

	public OrderedLinkItem<String> getUpper() {
		return upper;
	}

	public void setUpper(OrderedLinkItem<String> item) {
		upper = (CounterQueItem) item;
	}

	public OrderedLinkItem<String> getLower() {
		return lower;
	}

	public void setLower(OrderedLinkItem<String> item) {
		lower = (CounterQueItem) item;
	}

	public String getKey() {
		return key;
	}

	public LruLinkItem<String> getNewer() {
		return newer;
	}

	public void setNewer(LruLinkItem<String> item) {
		newer = (CounterQueItem) item;
	}

	public LruLinkItem<String> getOlder() {
		return older;
	}

	public void setOlder(LruLinkItem<String> item) {
		older = (CounterQueItem) item;
	}
	
	public long getLastUpdateTime() {
		return tq.getLastUpdateTime();
	}

	public long getLong() {
		return tq.count();
	}
	
	public CounterQueItem clone() {
		CounterQueItem item = new CounterQueItem();
		item.key = new String(this.key);
		item.tq = this.tq.clone();
		return item;
	}
}
