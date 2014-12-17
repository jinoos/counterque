package com.jinoos.util.countque;

public class TimeQueSlot {
	private long time;
	private long count;

	private TimeQueSlot older;
	private TimeQueSlot newer;

	protected TimeQueSlot() {
		clear();
	}

	protected TimeQueSlot(long time) {
		clear();
		this.time = time;
	}

	protected void upCount() {
		count++;
	}

	protected TimeQueSlot clear() {
		time = count = 0;
		older = newer = null;
		return this;
	}

	protected long getTime() {
		return time;
	}

	protected TimeQueSlot setTime(long time) {
		this.time = time;
		return this;
	}

	protected long getCount() {
		return count;
	}

	protected TimeQueSlot setCount(long count) {
		this.count = count;
		return this;
	}

	protected TimeQueSlot getOlder() {
		return older;
	}

	protected TimeQueSlot setOlder(TimeQueSlot older) {
		this.older = older;
		return this;
	}

	protected TimeQueSlot getNewer() {
		return newer;
	}

	protected TimeQueSlot setNewer(TimeQueSlot newer) {
		this.newer = newer;
		return this;
	}
}