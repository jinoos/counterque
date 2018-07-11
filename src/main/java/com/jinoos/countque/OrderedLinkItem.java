package com.jinoos.util.countque;

public interface OrderedLinkItem<T> {
	public T getKey();
	public long getLong();
	public boolean isGreaterThan(OrderedLinkItem<T> it);
	public OrderedLinkItem<T> getUpper();
	public void setUpper(OrderedLinkItem<T> it);
	public OrderedLinkItem<T> getLower();
	public void setLower(OrderedLinkItem<T> it);
}
