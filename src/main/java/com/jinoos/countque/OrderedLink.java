package com.jinoos.countque;

class OrderedLink<T> {
    private OrderedLinkItem<T> topItem = null;
    private OrderedLinkItem<T> bottomItem = null;
    private Object lock = new Object();

    private int size = 0;

    public OrderedLink() {
        // Do nothing
    }

    public void arrange(OrderedLinkItem<T> item) {
        synchronized (lock) {
            arrangeItemsWithoutLock(item);
        }
    }

    public OrderedLinkItem<T> put(OrderedLinkItem<T> item) {
        synchronized (lock) {
            return putWithoutLock(item);
        }
    }

    public OrderedLinkItem<T> pull(OrderedLinkItem<T> item) {
        synchronized (lock) {
            return pullWithoutLock(item);
        }
    }

    public int size() {
        return size;
    }

    public OrderedLinkItem<T> getTopItem() {
        return topItem;
    }

    public OrderedLinkItem<T> getBottomItem() {
        return bottomItem;
    }

    public OrderedLinkItem<T> putWithoutLock(OrderedLinkItem<T> item) {
        if (bottomItem == null) {
            topItem = bottomItem = item;
            this.size++;
            return item;
        }

        item.setUpperItem(bottomItem);
        bottomItem.setLowerItem(item);
        bottomItem = item;
        this.size++;
        arrangeItemsWithoutLock(item);
        return item;
    }

    private void arrangeItemsWithoutLock(OrderedLinkItem<T> item) {

        if (item.getUpperItem() == null) {
            return;
        }

        OrderedLinkItem<T> curItem = item.getUpperItem();
        if (!item.isGreaterThan(curItem)) {
            return;
        }
        pullWithoutLock(item);
        curItem = curItem.getUpperItem();

        while (curItem != null) {
            if (item.isGreaterThan(curItem)) {
                curItem = curItem.getUpperItem();
                continue;
            }

            if (curItem.getLowerItem() == null) {
                bottomItem = item;
            } else {
                item.setLowerItem(curItem.getLowerItem());
                item.getLowerItem().setUpperItem(item);
            }
            curItem.setLowerItem(item);
            item.setUpperItem(curItem);
            break;
        }

        if (item.getUpperItem() == null && item.getLowerItem() == null) {
            item.setLowerItem(topItem);
            topItem.setUpperItem(item);
            topItem = item;
        }
        size++;
    }

    private OrderedLinkItem<T> pullWithoutLock(OrderedLinkItem<T> item) {
        if (bottomItem == null) {
            item.setUpperItem(null);
            item.setLowerItem(null);
            return null;
        }

        if (item == bottomItem) {
            bottomItem = item.getUpperItem();
            if (bottomItem != null) {
                bottomItem.setLowerItem(null);
            }
        } else {
            if (item.getUpperItem() != null) {
                item.getUpperItem().setLowerItem(item.getLowerItem());
            }
        }

        if (item == topItem) {
            topItem = item.getLowerItem();
            if (topItem != null) {
                topItem.setUpperItem(null);
            }
        } else {
            if (item.getLowerItem() != null) {
                item.getLowerItem().setUpperItem(item.getUpperItem());
            }
        }

        item.setUpperItem(null);
        item.setLowerItem(null);
        size--;
        return item;
    }
}
