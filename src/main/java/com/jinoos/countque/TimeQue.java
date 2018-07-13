package com.jinoos.countque;

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

	public long count() {
		return count(true);
	}

	public long count(boolean upToDate) {
		if (upToDate) {
			long time = getCurrentTime();
			if (time != lastUpdateTime)
				releaseOutOfInterval(time);
		}
		return count;
	}

	public long beat() {
		return beat(getCurrentTime(), 1);
	}

	public long beat(int countIncrese) {
		return beat(getCurrentTime(), countIncrese);
	}

	public long beat(long currentTime) {
		return beat(currentTime, 1);
	}

	public long beat(long currentTime, int countIncrese) {
		lastUpdateTime = currentTime;
		long time = currentTime;
		if (slotInterval > 1) {
			time -= (lastUpdateTime % slotInterval);
		}

		TimeQueSlot slot = null;
		if (last == null) {
			slot = getSlot();
			slot.setTime(time);
			last = first = slot;
			size++;
		} else if (last.getTime() != time) {
			slot = getSlot();
			slot.setTime(time);
			slot.setOlder(last);
			last.setNewer(slot);
			last = slot;
			size++;
			releaseOutOfInterval(currentTime);
		} else {
			slot = last;
		}

		slot.upCount(countIncrese);
		count += countIncrese;

		return count;
	}

	public TimeQue clone() {
		TimeQue clone = new TimeQue(maxSlotCount, slotInterval);
		clone.size = this.size;
		clone.count = this.count;
		TimeQueSlot tqs = this.first;
		TimeQueSlot slotPrev = null;
		for (int i = 0; i < clone.size; i++) {
			TimeQueSlot slot = new TimeQueSlot(tqs.getTime(), tqs.getCount());
			if (i == 0) {
				clone.first = slot;
			}
			slot.setOlder(slotPrev);
			if (slotPrev != null) {
				slotPrev.setNewer(slot);
			}
			slotPrev = slot;
			tqs = tqs.getNewer();
		}
		clone.last = slotPrev;

		return clone;
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
