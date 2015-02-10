package com.jinoos.util.countque;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import com.jinoos.util.countque.ObjectPool;
import com.jinoos.util.countque.SimpleObjectPool;

public class SimpleObjectPoolTest {
	ObjectPool<String> pool = new SimpleObjectPool<String>(1000);
	int threadCount = 100;
	int rounds = 1000 * 1000;
	AtomicInteger finished = new AtomicInteger(0);
	
	@Test
	public void speedString() {
		List<Thread> threads = new ArrayList<Thread>();
		
		for(int i = 0; i<threadCount; i++) {
			threads.add(new TestThread(pool));
		}
		
		long start = System.currentTimeMillis();
		
		for(int i = 0; i<threadCount; i++) {
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
		ObjectPool<String> pool = null;
		public TestThread(ObjectPool<String> pool) {
			this.pool = pool;
		}
		
		public void run() {
			String str = null;
			for(int i = 0; i < rounds; i++) {
//				str = new String();
//				str += i;
				str = pool.get();
				str += i;
				str = "";
				pool.back(str);
			}
			finished.incrementAndGet();
		}
	}
	
	
}
