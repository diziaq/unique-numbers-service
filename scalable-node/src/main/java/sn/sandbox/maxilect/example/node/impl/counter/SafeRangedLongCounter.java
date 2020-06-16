package sn.sandbox.maxilect.example.node.impl.counter;


import java.util.concurrent.atomic.AtomicLong;


final class SafeRangedLongCounter implements LongCounter {

  private final long lastValid;
  private final AtomicLong counter;

  public SafeRangedLongCounter(long min, long max) {
    this.lastValid = max;
    this.counter = new AtomicLong(min);
  }

  @Override
  public long next() {
    long current = counter.getAndIncrement();

    if (current > lastValid) {
      throw new RuntimeException("No more values in counter");
    }

    return current;
  }
}
