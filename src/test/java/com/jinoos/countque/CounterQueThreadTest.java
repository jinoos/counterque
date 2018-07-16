package com.jinoos.countque;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class CounterQueThreadTest {
	private int capacity = 500;
	private int countTerm = 10;
	private int releaseTerm = 1;

	private AtomicInteger finished = new AtomicInteger();
	int threadCount = 100;
	int loop = 1000 * 10;
	int randMax = capacity;
	
	@Test
	public void threadTest() {
		finished.set(0);
		CounterQue counterQue = new CounterQue(capacity, countTerm, releaseTerm);
		
		List<Thread> threadList = new ArrayList<>();
		
		for(int i = 0; i < threadCount; i++) {
			Thread t = new Worker(counterQue);
			threadList.add(t);
		}
		for(int i = 0; i < threadCount; i++) {
			threadList.get(i).start();
		}
		long startT = System.currentTimeMillis();
		
		while(finished.get() < threadCount) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long endT = System.currentTimeMillis();
		System.out.println("end. " + (float) (endT - startT) / 1000 + ", "
				+ (loop * threadCount) / (float) ((endT - startT) / 1000) / 1000000 + " MHz");

		
		LruLink<String> lru = counterQue.lruList();
		LruLinkItem<String> lruItem = lru.newest();
		int i = 0;
		while (lruItem != null) {
			CounterQueItem item = (CounterQueItem) lruItem;
			System.out.println("" + ++i + " : " + item.getKey() + " - " + item.getCount(false) + " = " + item.getLastUpdateTime());
			lruItem = lruItem.getOlder();
		}

		OrderedLink<String> order = counterQue.orderList();
		OrderedLinkItem<String> orderItem = order.top();
		i = 0;
		while (orderItem != null) {
			CounterQueItem item = (CounterQueItem) orderItem;
			System.out.println("Ordered " + ++i + " : " + item.getKey() + " - " + item.getCount(false));
			orderItem = orderItem.getLower();
		}
		
		List<CounterQueItem> top100 = counterQue.getTopItem(0, 100);
		i = 0;
		long cnt = 0;
		for(CounterQueItem item: top100) {
			i++;
			System.out.println("" + i + " " + item.getKey() + " : " + item.getCount(false));
			cnt += item.getCount(false);
		}
		System.out.println("total : " + cnt);
		
	}
	
	class Worker extends Thread {
		CounterQue cq;
		Random rand = new Random();
		
		Worker(CounterQue cq) {
			this.cq = cq;
		}
		
		public void run() {
			for(int i = 0; i<loop; i++) {
				cq.beat("key" + rand.nextInt(randMax));
			}
			finished.incrementAndGet();
		}
	}
}
