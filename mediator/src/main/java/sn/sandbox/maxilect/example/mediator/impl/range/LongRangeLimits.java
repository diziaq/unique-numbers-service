package sn.sandbox.maxilect.example.mediator.impl.range;


public interface LongRangeLimits {

  long min();

  long max();

  static LongRangeLimits of(long min, long max) {
    if (min < 0 || max < 0 || min > max) {
      throw new RuntimeException("Invalid range limits: [" + min + ", " + max + "]");
    }

    return new DefaultLongRangeLimits(min, max);
  }
}
