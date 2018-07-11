package com.jinoos.util.countque;

public class OrderedLink<T> {
	private OrderedLinkItem<T> top = null;
	private OrderedLinkItem<T> bottom = null;
	private Object lock = new Object();

	private int size = 0;

	public OrderedLink() {
	}

	public void arrange(OrderedLinkItem<T> item) {
		synchronized (lock) {
			arrangeWithoutLock(item);
		}
	}

	public OrderedLinkItem<T> put(OrderedLinkItem<T> item) {
		synchronized (lock) {
			if (bottom == null) {
				top = bottom = item;
				this.size++;
				return item;
			}

			item.setUpper(bottom);
			bottom.setLower(item);
			bottom = item;
			this.size++;
			arrangeWithoutLock(item);
			return item;
		}
	}

	public OrderedLinkItem<T> pull(OrderedLinkItem<T> item) {
		synchronized (lock) {
			return pullWithoutLock(item);
		}
	}

	public int size() {
		return size;
	}

	public OrderedLinkItem<T> top() {
		return top;
	}

	public OrderedLinkItem<T> bottom() {
		return bottom;
	}

	private void arrangeWithoutLock(OrderedLinkItem<T> item) {

		if (item.getUpper() == null) {
			return;
		}

		OrderedLinkItem<T> curItem = item.getUpper();
		if (!item.isGreaterThan(curItem)) {
			return;
		}
		pullWithoutLock(item);
		curItem = curItem.getUpper();

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
		size++;
	}

	private OrderedLinkItem<T> pullWithoutLock(OrderedLinkItem<T> item) {
		if (bottom == null) {
			item.setUpper(null);
			item.setLower(null);
			return null;
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

}
