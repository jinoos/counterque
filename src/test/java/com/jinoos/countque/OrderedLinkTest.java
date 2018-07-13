package com.jinoos.countque;

import org.junit.Test;

public class OrderedLinkTest {

	@Test
	public void test() {
		OrderedLink<String> order = new OrderedLink<String>();
		String aaa = "aaaa";
		OrderedLinkItemImpl itemAAA = new OrderedLinkItemImpl(aaa);
		itemAAA.upCount();
		order.put(itemAAA);
		System.out.println("Order size : " + order.size());

		String bbb = "bbbb";
		OrderedLinkItemImpl itemBBB = new OrderedLinkItemImpl(bbb);
		itemBBB.upCount();
		order.put(itemBBB);
		itemBBB.upCount();
		order.arrange(itemBBB);
		System.out.println("Order size : " + order.size());
		bbb = "";
	}
	
	class OrderedLinkItemImpl implements OrderedLinkItem<String> {
		String data = null;
		TimeQue tq = null;
		
		OrderedLinkItemImpl upper;
		OrderedLinkItemImpl lower;
		
		public OrderedLinkItemImpl(String data) {
			this.data = data;
			this.tq = new TimeQue(5, 1);
		}
		
		public String getKey() {
			return data;
		}

		public boolean isGreaterThan(OrderedLinkItem<String> it) {
			return tq.count() > it.getLong();
		}

		public OrderedLinkItem<String> getUpper() {
			return upper;
		}

		public void setUpper(OrderedLinkItem<String> it) {
			upper = (OrderedLinkItemImpl) it;
		}

		public OrderedLinkItem<String> getLower() {
			return lower;
		}

		public void setLower(OrderedLinkItem<String> it) {
			lower = (OrderedLinkItemImpl) it;
		}

		public long getLong() {
			return tq.count();
		}
		
		public long upCount() {
			return tq.beat();
		}
	}
}
