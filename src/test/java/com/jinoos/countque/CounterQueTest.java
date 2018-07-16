package com.jinoos.countque;

import org.junit.Test;

import java.util.Random;

public class CounterQueTest {
	private int capacity = 500;
	private int releaseTerm = 10;
	private int loop = capacity;

	@Test
	public void basicTest() {
		int countTerm = 20;
		CounterQue counterQue = new CounterQue(capacity, countTerm, releaseTerm);

		long startT = System.currentTimeMillis();
		long endT;
		Random rand = new Random();
		int round = 20000;
		for (int j = 0; j < round; j++) {
			String key = "Key";
			for (int i = 0; i < loop; i++) {
				counterQue.beat(key + rand.nextInt(501));
			}
		}

		endT = System.currentTimeMillis();

		System.out.println("end. " + (float) (endT - startT) / 1000 + ", "
				+ (loop * round) / (float) ((endT - startT) / 1000) / 1000000 + " MHz");

		LruLink<String> lru = counterQue.lruList();
		LruLinkItem<String> lruItem = lru.getNewestItem();
		int i = 0;
		while (lruItem != null) {
			CounterQueItem item = (CounterQueItem) lruItem;
			System.out.println("" + ++i + " : " + item.getKey() + " - " + item.getCount() + " = " + item.getLastUpdateTime());
			lruItem = lruItem.getOlderItem();
		}

		OrderedLink<String> order = counterQue.orderList();
		OrderedLinkItem<String> orderItem = order.getTopItem();
		i = 0;
		while (orderItem != null) {
			CounterQueItem item = (CounterQueItem) orderItem;
			System.out.println("Ordered " + ++i + " : " + item.getKey() + " - " + item.getCount());
			orderItem = orderItem.getLowerItem();
		}
	}
}
