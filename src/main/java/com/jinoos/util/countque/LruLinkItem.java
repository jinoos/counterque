package com.jinoos.util.countque;

public interface LruLinkItem<T> {
	public T getData();
	public LruLinkItem<T> getNewer();
	public void setNewer(LruLinkItem<T> item);
	public LruLinkItem<T> getOlder();
	public void setOlder(LruLinkItem<T> item);
}
