package com.jinoos.util.countque;

public class OrderedLink<T> {
	private OrderedLinkItem<T> top;
	private OrderedLinkItem<T> bottom;
	private Object lock;

	private int size;

	public OrderedLink() {
		top = bottom = null;
		size = 0;
		lock = new Object();
	}
	
	private void arrangeWithoutLock(OrderedLinkItem<T> item) {
		
		if (item.getUpper() == null) {
			return;
		}
		
		OrderedLinkItem<T> curItem = item.getUpper();
		pullWithoutLock(item);
		
		while (curItem != null) {
			if (item.isGreaterThan(curItem)) {
				curItem = curItem.getUpper();
				continue;
			}
			
			if (curItem.getLower() == null) {
				bottom = item;
			} else {
				item.setLower(curItem.getLower());
				item.getLower().setUpper(item);
			}
			curItem.setLower(item);
			item.setUpper(curItem);
			break;
		}

		if (item.getUpper() == null && item.getLower() == null) {
			item.setLower(top);
			top.setUpper(item);
			top = item;
		}
	}

	public void arrange(OrderedLinkItem<T> item) {
		synchronized (lock) {
			arrangeWithoutLock(item);
		}
	}

	public void put(OrderedLinkItem<T> item) {
		synchronized (lock) {
			if (bottom == null) {
				top = bottom = item;
				size++;
				return;
			}
			
			item.setUpper(bottom);
			bottom.setLower(item);
			bottom = item;
			size++;
			arrangeWithoutLock(item);
			return;
		}
	}
	
	private OrderedLinkItem<T> pullWithoutLock(OrderedLinkItem<T> item) {
		if (bottom == null) {
			item.setUpper(null);
			item.setLower(null);
			size--;
			return item;
		}

		if (item == bottom) {
			bottom = item.getUpper();
			if (bottom != null)
				bottom.setLower(null);
		} else {
			if (item.getUpper() != null)
				item.getUpper().setLower(item.getLower());
		}

		if (item == top) {
			top = item.getLower();
			if (top != null)
				top.setUpper(null);
		} else {
			if (item.getLower() != null)
				item.getLower().setUpper(item.getUpper());
		}

		item.setUpper(null);
		item.setLower(null);
		size--;
		return item;
	}

	public OrderedLinkItem<T> pull(OrderedLinkItem<T> item) {
		synchronized (lock) {
			return pullWithoutLock(item);
		}
	}

	public int getSize() {
		return size;
	}

	public OrderedLinkItem<T> getTop() {
		return top;
	}

	public OrderedLinkItem<T> getBottom() {
		return bottom;
	}
}
