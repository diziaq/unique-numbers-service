package sn.sandbox.maxilect.example.mediator.impl.range;


final class DefaultLongRangeLimits implements LongRangeLimits {

  private final long min;
  private final long max;

  DefaultLongRangeLimits(long min, long max) {
    this.min = min;
    this.max = max;
  }

  @Override
  public long min() {
    return min;
  }

  @Override
  public long max() {
    return max;
  }
}
