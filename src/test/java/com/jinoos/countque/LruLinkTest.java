package com.jinoos.countque;

import junit.framework.Assert;

import org.junit.Test;

public class LruLinkTest {

	@Test
	public void lruTest() {
		LruLink<String> stringLru = new LruLink<String>();
		
		String str = new String("aaaa");
		LruItem itemAAAA = new LruItem(str);
		stringLru.put(itemAAAA);
		Assert.assertEquals(itemAAAA.getKey(), stringLru.newest().getKey());
		Assert.assertEquals(itemAAAA.getKey(), stringLru.oldest().getKey());
		Assert.assertEquals(stringLru.size(), 1);
		
		stringLru.pull(itemAAAA);
		Assert.assertEquals(stringLru.size(), 0);
		Assert.assertEquals(stringLru.newest(), null);
		Assert.assertEquals(stringLru.oldest(), null);

		stringLru.put(itemAAAA);
		Assert.assertEquals(itemAAAA.getKey(), stringLru.newest().getKey());
		Assert.assertEquals(itemAAAA.getKey(), stringLru.oldest().getKey());
		Assert.assertEquals(stringLru.size(), 1);

		str = new String("bbbb");
		LruItem itemBBBB = new LruItem(str);
		stringLru.put(itemBBBB);
		Assert.assertEquals(itemBBBB.getKey(), stringLru.newest().getKey());
		Assert.assertEquals(itemAAAA.getKey(), stringLru.oldest().getKey());
		Assert.assertEquals(stringLru.size(), 2);
		
		str = new String("cccc");
		LruItem itemCCCC = new LruItem(str);
		stringLru.put(itemCCCC);
		Assert.assertEquals(itemCCCC.getKey(), stringLru.newest().getKey());
		Assert.assertEquals(itemAAAA.getKey(), stringLru.oldest().getKey());
		Assert.assertEquals(stringLru.size(), 3);

		LruItem item = (LruItem) stringLru.newest().getOlder();
		item = (LruItem) stringLru.pull(item);
		stringLru.put(item);
		Assert.assertEquals(itemBBBB.getKey(), stringLru.newest().getKey());
		Assert.assertEquals(itemAAAA.getKey(), stringLru.oldest().getKey());
		Assert.assertEquals(stringLru.size(), 3);
	}
	
	class LruItem implements LruLinkItem<String> {
		String data = null;
		LruLinkItem<String> newer = null; 
		LruLinkItem<String> older = null;
		
		public LruItem(String item) {
			data = item;
		}
		
		public String getKey() {
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
