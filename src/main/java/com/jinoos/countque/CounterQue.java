package com.jinoos.countque;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CounterQue {
	private OrderedLink<String> orderList = null;
	private LruLink<String> lruList = null;
	private Map<String, CounterQueItem> itemMap = null;
	private int maxCapacity = 0;

	private int countTermSeconds = 60 * 60;
	private int releaseTermSeconds = 60;

	private Object lock = new Object();

	public CounterQue(int maxCapacity) {
		this.maxCapacity = maxCapacity;
		orderList = new OrderedLink<String>();
		lruList = new LruLink<String>();
		itemMap = new HashMap<String, CounterQueItem>();
	}

	public CounterQue(int maxCapacity, int countTermSeconds, int releaseTermSeconds) {
		this.maxCapacity = maxCapacity;
		orderList = new OrderedLink<String>();
		lruList = new LruLink<String>();
		itemMap = new ConcurrentHashMap<String, CounterQueItem>();
		this.countTermSeconds = countTermSeconds;
		this.releaseTermSeconds = releaseTermSeconds;
	}

	public long beat(String key) {
		return beat(key, 1);
	}

	public long beat(String key, int countIncrese) {
		if (key == null)
			throw new NullPointerException();
		if (key.length() == 0)
			return 0;

		long count = 0;
		synchronized (lock) {
			CounterQueItem item = itemMap.get(key);
			if (item == null) {
				item = new CounterQueItem(key, countTermSeconds / releaseTermSeconds, releaseTermSeconds);
				count = item.upCount(countIncrese);
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
		}
		return count;
	}

	private CounterQueItem getEvictionItem() {
		CounterQueItem item = null;
		if (size() >= maxCapacity) {
			item = (CounterQueItem) lruList.oldest();
			if (item == null) {
				return null;
			}
			lruList.pull(item);
			orderList.pull(item);
			itemMap.remove(item.getKey());
		}
		return item;
	}

	public int size() {
		return lruList.size();
	}

	public OrderedLink<String> orderList() {
		return orderList;
	}

	public LruLink<String> lruList() {
		return lruList;
	}

	public List<CounterQueItem> getTopItem(int start, int size) {
		List<CounterQueItem> list = new ArrayList<CounterQueItem>();
		synchronized (lock) {
			if (start >= orderList.size()) {
				return list;
			}
			CounterQueItem item = (CounterQueItem) orderList.top();
			for (int i = 0; i < start; i++) {
				item = (CounterQueItem) item.getLower();
			}

			for (int i = 0; i < size; i++) {
				if(item == null) {
					break;
				}
				list.add(item);
				item = (CounterQueItem) item.getLower();
			}
		}
		return list;
	}

	public Map<String, CounterQueItem> getItemMap() {
		Map<String, CounterQueItem> itemMap = new HashMap<String, CounterQueItem>();
		synchronized (lock) {
			for (String key : this.itemMap.keySet()) {
				itemMap.put(key, this.itemMap.get(key));
			}
		}
		return itemMap;
	}

}
