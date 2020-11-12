package base.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {
    private final AtomicInteger counter;



    public AtomicCounter(int initialValue) {
        counter = new AtomicInteger(initialValue);
    }

    public int getValue() {
        return counter.get();
    }


    public void increment(int n) {
        while (true) {
            int currentValue = getValue();
            int newValue = currentValue + n;
            if (counter.compareAndSet(currentValue, newValue)) {
                return;
            }
        }
    }


}
