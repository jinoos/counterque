package com.jinoos.countque;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleObjectPoolTest {
	private ObjectPool<String> pool = new SimpleObjectPool<>(1000);
	private AtomicInteger finished = new AtomicInteger(0);
	
	@Test
	public void speedString() {
		List<Thread> threads = new ArrayList<>();

		int threadCount = 100;
		for(int i = 0; i< threadCount; i++) {
			threads.add(new TestThread(pool));
		}
		
		long start = System.currentTimeMillis();
		
		for(int i = 0; i< threadCount; i++) {
			threads.get(i).start();
		}
		
		while(threadCount != finished.get()) {
			System.out.println("Current finished - " + finished.get());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("Elapsed time " + (end - start) + "ms");
		
	}
	
	class TestThread extends Thread {
		ObjectPool<String> pool;
		TestThread(ObjectPool<String> pool) {
			this.pool = pool;
		}
		
		public void run() {
			String str;
			int rounds = 1000 * 1000;
			for(int i = 0; i < rounds; i++) {
				str = pool.get() + i + "";
				pool.back(str);
			}
			finished.incrementAndGet();
		}
	}
	
	
}
