package com.jinoos.util.countque;

public interface LruLinkItem<T> {
	public T getKey();
	public LruLinkItem<T> getNewer();
	public void setNewer(LruLinkItem<T> item);
	public LruLinkItem<T> getOlder();
	public void setOlder(LruLinkItem<T> item);
}
