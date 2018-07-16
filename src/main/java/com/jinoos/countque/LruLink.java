package com.jinoos.countque;

class LruLink<T> {
	private LruLinkItem<T> newestItem;
	private LruLinkItem<T> oldestItem;
	private int size;
	private Object lock = new Object();

	public LruLink() {
		// it is empty to do
	}

	public LruLinkItem<T> getNewestItem() {
		return newestItem;
	}

	public LruLinkItem<T> getOldestItem() {
		return oldestItem;
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
			if (oldestItem == null) {
				newestItem = oldestItem = item;
			} else {
				item.setNewerItem(null);
				newestItem.setNewerItem(item);
				item.setOlderItem(newestItem);
				newestItem = item;
			}
			size++;
		}
	}

	public LruLinkItem<T> pull(LruLinkItem<T> item) {
		synchronized (lock) {
			if (oldestItem == null) {
				item.setNewerItem(null);
				item.setOlderItem(null);
				size--;
				return item;
			}

			if (item == oldestItem) {
				oldestItem = item.getNewerItem();
				if(oldestItem != null)
					oldestItem.setOlderItem(null);
			} else {
				if(item.getNewerItem() != null)
				item.getNewerItem().setOlderItem(item.getOlderItem());
			}

			if (item == newestItem) {
				newestItem = item.getOlderItem();
				if(newestItem != null)
					newestItem.setNewerItem(null);
			} else {
				if(item.getOlderItem() != null)
				item.getOlderItem().setNewerItem(item.getNewerItem());
			}

			item.setNewerItem(null);
			item.setOlderItem(null);
			size--;
			return item;
		}
	}
}
