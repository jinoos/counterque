package com.jinoos.countque;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class LruLinkTest {

	@Test
	public void lruTest() {
		LruLink<String> stringLru = new LruLink<>();

		String str = "aaaa";
		LruItem itemAAAA = new LruItem(str);
		stringLru.put(itemAAAA);
		assertThat(itemAAAA.getKey()).isEqualTo(stringLru.getNewestItem().getKey());
		assertThat(itemAAAA.getKey()).isEqualTo(stringLru.getOldestItem().getKey());
		assertThat(stringLru.size()).isEqualTo(1);

		stringLru.pull(itemAAAA);
		assertThat(stringLru.size()).isEqualTo(0);
		assertThat(stringLru.getNewestItem()).isEqualTo(null);
		assertThat(stringLru.getOldestItem()).isEqualTo(null);

		stringLru.put(itemAAAA);
		assertThat(itemAAAA.getKey()).isEqualTo(stringLru.getNewestItem().getKey());
		assertThat(itemAAAA.getKey()).isEqualTo(stringLru.getOldestItem().getKey());
		assertThat(stringLru.size()).isEqualTo(1);

		str = "bbbb";
		LruItem itemBBBB = new LruItem(str);
		stringLru.put(itemBBBB);
		assertThat(itemBBBB.getKey()).isEqualTo(stringLru.getNewestItem().getKey());
		assertThat(itemAAAA.getKey()).isEqualTo(stringLru.getOldestItem().getKey());
		assertThat(stringLru.size()).isEqualTo(2);

		str = "cccc";
		LruItem itemCCCC = new LruItem(str);
		stringLru.put(itemCCCC);
		assertThat(itemCCCC.getKey()).isEqualTo(stringLru.getNewestItem().getKey());
		assertThat(itemAAAA.getKey()).isEqualTo(stringLru.getOldestItem().getKey());
		assertThat(stringLru.size()).isEqualTo(3);

		LruItem item = (LruItem) stringLru.getNewestItem().getOlderItem();
		item = (LruItem) stringLru.pull(item);
		stringLru.put(item);
		assertThat(itemBBBB.getKey()).isEqualTo(stringLru.getNewestItem().getKey());
		assertThat(itemAAAA.getKey()).isEqualTo(stringLru.getOldestItem().getKey());
		assertThat(stringLru.size()).isEqualTo(3);
	}

	class LruItem implements LruLinkItem<String> {
		String data;
		LruLinkItem<String> newer = null;
		LruLinkItem<String> older = null;

		LruItem(String item) {
			data = item;
		}

		public String getKey() {
			return data;
		}

		public LruLinkItem<String> getNewerItem() {
			return newer;
		}

		public void setNewerItem(LruLinkItem<String> item) {
			newer = item;
		}

		public LruLinkItem<String> getOlderItem() {
			return older;
		}

		public void setOlderItem(LruLinkItem<String> item) {
			older = item;
		}
	}
}
