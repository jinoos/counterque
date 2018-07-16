package com.jinoos.countque;

class TimeQueSlot {
	private long time = 0;
	private long count = 0;

	private TimeQueSlot olderSlot = null;
	private TimeQueSlot newerSlot = null;

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

	protected TimeQueSlot getOlderSlot() {
		return olderSlot;
	}

	protected TimeQueSlot setOlderSlot(TimeQueSlot olderSlot) {
		this.olderSlot = olderSlot;
		return this;
	}

	protected TimeQueSlot getNewerSlot() {
		return newerSlot;
	}

	protected TimeQueSlot setNewerSlot(TimeQueSlot newerSlot) {
		this.newerSlot = newerSlot;
		return this;
	}
	
	protected void clear() {
		time = count = 0;
		olderSlot = newerSlot = null;
	}
}