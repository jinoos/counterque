package com.jinoos.countque;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CounterQue {
	private OrderedLink<String> orderList = new OrderedLink<>();
	private LruLink<String> lruList = new LruLink<>();
	private Map<String, CounterQueItem> itemMap = new HashMap<>();
	private int maxCapacity;

	private int countTermSeconds = 60 * 60;
	private int releaseTermSeconds = 60;

	private Object lock = new Object();

	public CounterQue(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public CounterQue(int maxCapacity, int countTermSeconds, int releaseTermSeconds) {
		this.maxCapacity = maxCapacity;
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

		long count;
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
			item = (CounterQueItem) lruList.getOldestItem();
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
		List<CounterQueItem> list = new ArrayList<>();
		synchronized (lock) {
			if (start >= orderList.size()) {
				return list;
			}
			CounterQueItem item = (CounterQueItem) orderList.getTopItem();
			for (int i = 0; i < start; i++) {
				item = (CounterQueItem) item.getLowerItem();
			}

			for (int i = 0; i < size; i++) {
				if(item == null) {
					break;
				}
				list.add(item);
				item = (CounterQueItem) item.getLowerItem();
			}
		}
		return list;
	}

	public Map<String, CounterQueItem> getItemMap() {
		Map<String, CounterQueItem> returnMap = new HashMap<>();
		synchronized (lock) {
		    returnMap.putAll(itemMap);
		}
		return returnMap;
	}
}
