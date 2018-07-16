package com.jinoos.countque;

interface LruLinkItem<T> {
	public T getKey();
	public LruLinkItem<T> getNewerItem();
	public void setNewerItem(LruLinkItem<T> item);
	public LruLinkItem<T> getOlderItem();
	public void setOlderItem(LruLinkItem<T> item);
}
