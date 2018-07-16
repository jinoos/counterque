package com.jinoos.countque;

import org.junit.Test;

public class TimeQueueTest
{

    @Test
    public void test()
    {
        TimeQue tq = new TimeQue(5, 1);

        long startT = System.currentTimeMillis();
        
        System.out.println("start");
        int i = 0;
        int num = 1000 * 1000 * 100;
        for(;i < num; i++)
        {
            tq.beat();
        }
        
        TimeQue tq2 = tq.copy();
        System.out.println(tq2.count());
        System.out.println("Count : " + tq.count());
        
        long endT = System.currentTimeMillis();

        System.out.println("end. " + (float)(endT - startT)/1000 + ", " + (num)/(float)((endT - startT)/1000) /1000000 + " MHz");
    }

}
