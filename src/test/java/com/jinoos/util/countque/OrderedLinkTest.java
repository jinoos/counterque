package com.jinoos.util.countque;

import org.junit.Test;

import com.jinoos.util.countque.OrderedLink;
import com.jinoos.util.countque.OrderedLinkItem;
import com.jinoos.util.countque.TimeQue;

public class OrderedLinkTest {

	@Test
	public void test() {
		OrderedLink<String> order = new OrderedLink<String>();
		String aaa = "aaaa";
		OrderedLinkItemImpl itemAAA = new OrderedLinkItemImpl(aaa);
		itemAAA.upCount();
		order.put(itemAAA);

		String bbb = "bbbb";
		OrderedLinkItemImpl itemBBB = new OrderedLinkItemImpl(bbb);
		itemBBB.upCount();
		order.put(itemBBB);
		itemBBB.upCount();
		order.arrange(itemBBB);
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
		
		public String getData() {
			return data;
		}

		public boolean isGreaterThan(OrderedLinkItem<String> it) {
			return tq.getCount() > it.getLong();
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
			return tq.getCount();
		}
		
		public long upCount() {
			return tq.upCount();
		}
	}
}
