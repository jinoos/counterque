package com.jinoos.countque;

import java.lang.reflect.ParameterizedType;

public class SimpleObjectPool<T> implements ObjectPool<T> {
	public int MAX_SPARE = 1000;
	private int maxSpare = MAX_SPARE;

	private Object lock = new Object();

	private int capacity = 0;
	private long allocated = 0;
	private long recovered = 0;

	private Shell<T> first = null;
	private Shell<T> last = null;

	public SimpleObjectPool(int maxSpare) {
		this.maxSpare = maxSpare;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private T newInstance() {
		T instance = null;
		try {
			instance = (T) ((Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0])
					.newInstance();
		} catch (Exception e) {
		}
		return instance;
	}

	public T get() {
		allocated++;
		if (first == null) {
			return newInstance();
		}
		T o = null;
		synchronized (lock) {
			Shell<T> s = first;
			first = s.next;
			first.prev = null;
			o = (T) s.pullOut();
			capacity--;
		}
		return o;
	}

	public void back(T o) {
		recovered++;
		if (capacity <= maxSpare)
			return;
		synchronized (lock) {
			Shell<T> s = new Shell<T>(o);
			if (last == null) {
				first = last = s;
			} else {
				s.prev = last;
				last = s;
			}
			capacity++;
		}
	}

	public int capacity() {
		return capacity;
	}

	public void flush() {
		Shell<T> s = first;
		synchronized (lock) {
			while (s != null) {
				s.prev = null;
				s.data = null;
				s = s.next;
			}
			capacity = 0;
		}
	}

	public long allocated() {
		return allocated;
	}

	public long recovered() {
		return recovered;
	}

	@SuppressWarnings("hiding")
	private class Shell<T> {
		@SuppressWarnings("unused")
		Shell<T> prev = null;
		Shell<T> next = null;
		T data = null;

		Shell(T o) {
			data = o;
		}

		T pullOut() {
			T r = data;
			data = null;
			return r;
		}
	}
}
