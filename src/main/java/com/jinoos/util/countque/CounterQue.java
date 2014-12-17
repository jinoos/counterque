package com.jinoos.util.countque;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CounterQue {
	private OrderedLink<String> orderList = null;
	private LruLink<String> lruList = null;
	private Map<String, CounterQueItem> itemMap = null;
	private int maxCapacity = 0;

	private int countTerm = 60 * 60;
	private int releaseTerm = 60;

	public CounterQue(int maxCapacity) {
		this.maxCapacity = maxCapacity;
		orderList = new OrderedLink<String>();
		lruList = new LruLink<String>();
		itemMap = new ConcurrentHashMap<String, CounterQueItem>();
	}

	public CounterQue(int maxCapacity, int countTerm, int releaseTerm) {
		this.maxCapacity = maxCapacity;
		orderList = new OrderedLink<String>();
		lruList = new LruLink<String>();
		itemMap = new ConcurrentHashMap<String, CounterQueItem>();
		this.countTerm = countTerm;
		this.releaseTerm = releaseTerm;
	}

	public long beat(String key) {
		if (key == null)
			throw new NullPointerException();
		if (key.length() == 0)
			return 0;

		CounterQueItem item = itemMap.get(key);
		long count = 0;
		if (item == null) {
			item = new CounterQueItem(key, countTerm / releaseTerm, releaseTerm);
			count = item.upCount();
			while (getEvictionItem() != null)
				;
			orderList.put(item);
			lruList.put(item);
			itemMap.put(key, item);
		} else {
			count = item.upCount();
			orderList.arrange(item);
			lruList.gotoTop(item);
		}

		return count;
	}

	private CounterQueItem getEvictionItem() {
		CounterQueItem item = null;
		if (getSize() >= maxCapacity) {
			item = (CounterQueItem) lruList.getOldest();
			lruList.pull(item);
			orderList.pull(item);
			itemMap.remove(item.getData());
		}
		return item;
	}

	public int getSize() {
		return lruList.getSize();
	}

	public OrderedLink<String> getOrderList() {
		return orderList;
	}

	public LruLink<String> getLruList() {
		return lruList;
	}

	public Map<String, CounterQueItem> getItemMap() {
		return itemMap;
	}

}
