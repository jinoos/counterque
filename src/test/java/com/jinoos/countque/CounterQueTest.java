package com.jinoos.util.countque;

import java.util.Random;

import org.junit.Test;

public class CounterQueTest {
	private int capacity = 1 * 500;
	private int countTerm = 20;
	private int releaseTerm = 10;

	private int loop = capacity;
	private int round = 20000 * 1;

	@Test
	public void basicTest() {
		CounterQue counterQue = new CounterQue(capacity, countTerm, releaseTerm);

		long startT = System.currentTimeMillis();
		long endT = System.currentTimeMillis();
		Random rand = new Random();
		for (int j = 0; j < round; j++) {
			String key = "Key";
			for (int i = 0; i < loop; i++) {
				// counterQue.bitSignal(key + (loop*10/9));
				counterQue.beat(key + rand.nextInt(501));
				// try {
				// Thread.sleep(1);
				// } catch (InterruptedException e) {
				// e.printStackTrace();
				// }
			}
			endT = System.currentTimeMillis();
		}

		endT = System.currentTimeMillis();

		System.out.println("end. " + (float) (endT - startT) / 1000 + ", "
				+ (float) ((loop * round) / (float) ((endT - startT) / 1000)) / 1000000 + " MHz");

		LruLink<String> lru = counterQue.lruList();
		LruLinkItem<String> lruItem = lru.newest();
		int i = 0;
		while (lruItem != null) {
			CounterQueItem item = (CounterQueItem) lruItem;
			System.out.println("" + ++i + " : " + item.getKey() + " - " + item.getCount() + " = " + item.getLastUpdateTime());
			lruItem = lruItem.getOlder();
		}

		OrderedLink<String> order = counterQue.orderList();
		OrderedLinkItem<String> orderItem = order.top();
		i = 0;
		while (orderItem != null) {
			CounterQueItem item = (CounterQueItem) orderItem;
			System.out.println("Ordered " + ++i + " : " + item.getKey() + " - " + item.getCount());
			orderItem = orderItem.getLower();
		}
	}
}
