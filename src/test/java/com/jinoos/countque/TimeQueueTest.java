package com.jinoos.util.countque;

import org.junit.Test;

import com.jinoos.util.countque.TimeQue;

public class TimeQueueTest
{

    @Test
    public void test()
    {
        TimeQue tq = new TimeQue(5, 1);
        //TimeQueue tq = new ConcurrentTimeQueue(120, 1);
        
        long startT = System.currentTimeMillis();
        
        System.out.println("start");
        int i = 0;
        int num = 1000 * 1000 * 100;
        for(;i < num; i++)
        {
            tq.beat();
//            if(tq.getSize() != size)
//            {
//                System.out.println("Increased size : " + tq.getSize() + ", count :" + tq.getCount());
//                size = tq.getSize();
//            }
        }
        
        TimeQue tq2 = tq.clone();
        System.out.println(tq2.count());
        System.out.println("Count : " + tq.count());
        
        long endT = System.currentTimeMillis();

        System.out.println("end. " + (float)(endT - startT)/1000 + ", " + (float)((num)/(float)((endT - startT)/1000))/1000000 + " MHz");
    }

}
