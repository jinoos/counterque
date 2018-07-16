package com.jinoos.countque;

public interface OrderedLinkItem<T> {
	public T getKey();
	public long getLong();
	public boolean isGreaterThan(OrderedLinkItem<T> it);
	public OrderedLinkItem<T> getUpperItem();
	public void setUpperItem(OrderedLinkItem<T> it);
	public OrderedLinkItem<T> getLowerItem();
	public void setLowerItem(OrderedLinkItem<T> it);
}
