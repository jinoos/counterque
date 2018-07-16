package com.jinoos.countque;

import org.junit.Test;

public class OrderedLinkTest {

	@Test
	public void test() {
		OrderedLink<String> order = new OrderedLink<>();
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
	}
	
	class OrderedLinkItemImpl implements OrderedLinkItem<String> {
		String data;
		TimeQue tq;
		
		OrderedLinkItemImpl upper;
		OrderedLinkItemImpl lower;
		
		OrderedLinkItemImpl(String data) {
			this.data = data;
			this.tq = new TimeQue(5, 1);
		}
		
		public String getKey() {
			return data;
		}

		public boolean isGreaterThan(OrderedLinkItem<String> it) {
			return tq.count() > it.getLong();
		}

		public OrderedLinkItem<String> getUpperItem() {
			return upper;
		}

		public void setUpperItem(OrderedLinkItem<String> it) {
			upper = (OrderedLinkItemImpl) it;
		}

		public OrderedLinkItem<String> getLowerItem() {
			return lower;
		}

		public void setLowerItem(OrderedLinkItem<String> it) {
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
