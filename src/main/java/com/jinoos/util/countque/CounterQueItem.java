package com.jinoos.util.countque;


public class CounterQueItem implements LruLinkItem<String>, OrderedLinkItem<String> {
	private String data = null;
	private TimeQue tq = null;
	
	private CounterQueItem upper = null;
	private CounterQueItem lower = null;
	private CounterQueItem newer = null;
	private CounterQueItem older = null;
	
	public CounterQueItem(String data, int maxSlotCount, int term) {
		this.data = data;
		this.tq = new TimeQue(maxSlotCount, term);
	}
	
	public long upCount() {
		return tq.upCount();
	}
	
	public long getCount() {
		return tq.getCount();
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

	public String getData() {
		return data;
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
		return tq.getCount();
	}
	
}
