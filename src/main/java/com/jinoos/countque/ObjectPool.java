package com.jinoos.countque;

public interface ObjectPool<T> {
	public T get();
	public void back(T o);
	public int capacity();
	public void flush();
	public long allocated();
	public long recovered();
}
