package com.jinoos.util.countque;

public class LruLink<T> {
	private LruLinkItem<T> newest;
	private LruLinkItem<T> oldest;
	private int size;
	private Object lock;

	public LruLink() {
		newest = oldest = null;
		size = 0;
		lock = new Object();
	}

	public LruLinkItem<T> newest() {
		return newest;
	}

	public LruLinkItem<T> oldest() {
		return oldest;
	}

	public int size() {
		return size;
	}
	
	public void gotoTop(LruLinkItem<T> item) {
		pull(item);
		put(item);
	}

	public void put(LruLinkItem<T> item) {
		synchronized (lock) {
			if (oldest == null) {
				newest = oldest = item;
			} else {
				item.setNewer(null);
				newest.setNewer(item);
				item.setOlder(newest);
				newest = item;
			}
			size++;
		}
	}

	public LruLinkItem<T> pull(LruLinkItem<T> item) {
		synchronized (lock) {
			if (oldest == null) {
				item.setNewer(null);
				item.setOlder(null);
				size--;
				return item;
			}

			if (item == oldest) {
				oldest = item.getNewer();
				if(oldest != null)
					oldest.setOlder(null);
			} else {
				if(item.getNewer() != null)
				item.getNewer().setOlder(item.getOlder());
			}

			if (item == newest) {
				newest = item.getOlder();
				if(newest != null)
					newest.setNewer(null);
			} else {
				if(item.getOlder() != null)
				item.getOlder().setNewer(item.getNewer());
			}

			item.setNewer(null);
			item.setOlder(null);
			size--;
			return item;
		}
	}
}
