package com.jinoos.util.countque;

public class TimeQue {
	private TimeQueSlot last;
	private TimeQueSlot first;

	private int slotInterval;
	private int maxSlotCount;

	private long lastUpdateTime = TimeQue.getCurrentTime();

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	private long count = 0;
	private int size = 0;

	private ObjectPool<TimeQueSlot> pool = null;

	public TimeQue(int maxSlotCount, int term) {
		this.maxSlotCount = maxSlotCount;
		this.slotInterval = term;
		clear();
	}

	public TimeQue(ObjectPool<TimeQueSlot> pool, int maxSlotCount, int term) {
		this.maxSlotCount = maxSlotCount;
		this.slotInterval = term;
		this.pool = pool;
		clear();
	}

	public long getCount() {
		long time = getCurrentTime();
		if (time != lastUpdateTime)
			releaseOutOfInterval(time);
		return count;
	}

	public long upCount() {
		return upCount(getCurrentTime());
	}

	public long upCount(long currentTime) {
		lastUpdateTime = getCurrentTime();
		if (slotInterval > 1) {
			lastUpdateTime -= (lastUpdateTime % slotInterval);
		}

		TimeQueSlot slot = null;
		if (last == null) {
			slot = getSlot();
			slot.setTime(lastUpdateTime);
			last = first = slot;
			size++;
		} else if (last.getTime() != lastUpdateTime) {
			slot = getSlot();
			slot.setTime(lastUpdateTime);
			slot.setOlder(last);
			last.setNewer(slot);
			last = slot;
			size++;
			releaseOutOfInterval(lastUpdateTime);
		} else {
			slot = last;
		}

		slot.upCount();
		count++;

		return count;
	}

	protected TimeQue clear() {
		if (first != null) {
			TimeQueSlot slot = first;
			while (slot != null) {
				first = slot.getNewer();
				if (first != null) {
					first.setOlder(null);
				}
				backSlot(slot);
				slot = first;
			}
		}
		last = first = null;
		return this;
	}

	protected void releaseOutOfInterval(long time) {
		if (first == null) {
			return;
		}

		long cutTime = time - (maxSlotCount * slotInterval);

		TimeQueSlot slot = first;

		while (slot != null && slot.getTime() <= cutTime) {
			this.count -= slot.getCount();
			first = slot.getNewer();
			backSlot(slot);
			size--;

			if (first == null) {
				last = null;
			} else {
				first.setOlder(null);
			}

			slot = first;
		}
	}

	protected static long getCurrentTime() {
		return System.currentTimeMillis() / 1000;
	}

	public TimeQueSlot getLast() {
		return last;
	}

	public TimeQueSlot getFirst() {
		return first;
	}

	public long getSlotInterval() {
		return slotInterval;
	}

	public int getMaxSlotCount() {
		return maxSlotCount;
	}

	public int getSize() {
		return size;
	}

	protected TimeQue setSlotInterval(int term) {
		this.slotInterval = term;
		return this;
	}

	protected TimeQue setMaxSlotCount(int maxTermCount) {
		this.maxSlotCount = maxTermCount;
		return this;
	}

	private TimeQueSlot getSlot() {
		if (pool == null) {
			return new TimeQueSlot();
		} else {
			return pool.get();
		}
	}

	private void backSlot(TimeQueSlot slot) {
		if (pool != null) {
			slot.clear();
			pool.back(slot);
		}
	}

}
