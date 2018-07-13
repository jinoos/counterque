package com.jinoos.countque;

public class TimeQueSlot {
	private long time = 0;
	private long count = 0;

	private TimeQueSlot older = null;
	private TimeQueSlot newer = null;

	protected TimeQueSlot() {
	}

	protected TimeQueSlot(long time) {
		this.time = time;
	}

	protected TimeQueSlot(long time, long initalCount) {
		this.time = time;
		this.count = initalCount;
	}
	
	protected void upCount() {
		upCount(1);
	}

	protected void upCount(int count) {
		this.count += count;
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
	
	protected void clear() {
		time = count = 0;
		older = newer = null;
	}
}