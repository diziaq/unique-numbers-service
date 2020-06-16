package sn.sandbox.maxilect.example.node.impl.counter;


import static sn.sandbox.maxilect.example.node.impl.counter.Numbers.requireLong;

import java.util.Map;


public interface LongCounter {

  long next();

  static LongCounter ranged(long min, long max) {
    if (min < 0 || max < 0 || min > max) {
      throw new RuntimeException("Invalid limits for counter: [" + min + ", " + max + "]");
    }

    return new SafeRangedLongCounter(min, max);
  }

  static LongCounter fromMinMaxMap(Map<?, ?> map) {
    return ranged(
        requireLong(map, "min"),
        requireLong(map, "max")
    );
  }
}
