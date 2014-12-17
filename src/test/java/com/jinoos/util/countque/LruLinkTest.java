package com.jinoos.util.countque;

import junit.framework.Assert;

import org.junit.Test;

import com.jinoos.util.countque.LruLink;
import com.jinoos.util.countque.LruLinkItem;

public class LruLinkTest {

	@Test
	public void lruTest() {
		LruLink<String> stringLru = new LruLink<String>();
		
		String str = new String("aaaa");
		LruItem itemAAAA = new LruItem(str);
		stringLru.put(itemAAAA);
		Assert.assertEquals(itemAAAA.getData(), stringLru.getNewest().getData());
		Assert.assertEquals(itemAAAA.getData(), stringLru.getOldest().getData());
		Assert.assertEquals(stringLru.getSize(), 1);
		
		stringLru.pull(itemAAAA);
		Assert.assertEquals(stringLru.getSize(), 0);
		Assert.assertEquals(stringLru.getNewest(), null);
		Assert.assertEquals(stringLru.getOldest(), null);

		stringLru.put(itemAAAA);
		Assert.assertEquals(itemAAAA.getData(), stringLru.getNewest().getData());
		Assert.assertEquals(itemAAAA.getData(), stringLru.getOldest().getData());
		Assert.assertEquals(stringLru.getSize(), 1);

		str = new String("bbbb");
		LruItem itemBBBB = new LruItem(str);
		stringLru.put(itemBBBB);
		Assert.assertEquals(itemBBBB.getData(), stringLru.getNewest().getData());
		Assert.assertEquals(itemAAAA.getData(), stringLru.getOldest().getData());
		Assert.assertEquals(stringLru.getSize(), 2);
		
		str = new String("cccc");
		LruItem itemCCCC = new LruItem(str);
		stringLru.put(itemCCCC);
		Assert.assertEquals(itemCCCC.getData(), stringLru.getNewest().getData());
		Assert.assertEquals(itemAAAA.getData(), stringLru.getOldest().getData());
		Assert.assertEquals(stringLru.getSize(), 3);

		LruItem item = (LruItem) stringLru.getNewest().getOlder();
		item = (LruItem) stringLru.pull(item);
		stringLru.put(item);
		Assert.assertEquals(itemBBBB.getData(), stringLru.getNewest().getData());
		Assert.assertEquals(itemAAAA.getData(), stringLru.getOldest().getData());
		Assert.assertEquals(stringLru.getSize(), 3);
	}
	
	class LruItem implements LruLinkItem<String> {
		String data = null;
		LruLinkItem<String> newer = null; 
		LruLinkItem<String> older = null;
		
		public LruItem(String item) {
			data = item;
		}
		
		public String getData() {
			return data;
		}

		public LruLinkItem<String> getNewer() {
			return newer;
		}

		public void setNewer(LruLinkItem<String> item) {
			newer = item;
		}

		public LruLinkItem<String> getOlder() {
			return older;
		}

		public void setOlder(LruLinkItem<String> item) {
			older = item;
		}
	}
}
