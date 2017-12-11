package teamseth.cs340.common.persistence;

import java.util.concurrent.Semaphore;

/**
 * @author Scott Leland Crossen
 * @copyright (C) Copyright 2017 Scott Leland Crossen
 */
public class OrderedSemaphore extends Semaphore {

    // Style this after the DMV ticket-calling system.
    int currentCalling = 0;
    int currentNumber = 0;

    public OrderedSemaphore(int i) {
        super(i);
    }

    @Override
    public void acquire() throws InterruptedException {
        int threadNumber = currentNumber++; // I could make this sync but then it might throw off the order.
        while(true) {
            super.acquire();
            if (currentCalling != threadNumber) {
                super.release();
            } else {
                currentCalling++;
                break;
            }
        }
    }
}
