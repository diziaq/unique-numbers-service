package sn.sandbox.maxilect.example.mediator.impl;


import java.util.concurrent.locks.ReentrantLock;
import java.util.function.LongSupplier;
import sn.sandbox.maxilect.example.mediator.impl.range.LongRangeLimits;


public final class RangeRegistryServiceImpl implements RangeRegistryService {

  private final ReentrantLock lock = new ReentrantLock(true);
  private final LongSupplier rangeSizeSupplier;
  private long nextLowerBound;

  public RangeRegistryServiceImpl(LongSupplier rangeSizeSupplier, long initialLowerBound) {
    this.rangeSizeSupplier = rangeSizeSupplier;
    this.nextLowerBound = initialLowerBound;
  }

  @Override
  public LongRangeLimits nextRange() {
    try {
      lock.lock();

      long currentLowerBound = nextLowerBound;
      long currentUpperBound = currentLowerBound + rangeSizeSupplier.getAsLong();

      this.nextLowerBound = currentUpperBound + 1L;

      return LongRangeLimits.of(currentLowerBound, currentUpperBound);
    } finally {
      lock.unlock();
    }
  }
}
