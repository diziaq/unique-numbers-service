package sn.sandbox.maxilect.example.node.impl.counter;


import java.util.Map;


final class Numbers {

  static long requireLong(Object value) {

    if (value instanceof Long) {
      return (Long) value;
    } else if (value instanceof Integer) {
      return Long.valueOf((Integer) value);
    } else if (value instanceof String) {
      return Long.parseLong((String) value);
    } else {
      throw new RuntimeException("Unable to recognize long value in [" + value + "]");
    }
  }

  static long requireLong(Map<?, ?> map, Object key) {

    if (map == null || key == null) {
      throw new RuntimeException(
          "Unable to recognize long value in map [" + map + "] with key [" + key + "]");
    }

    return requireLong(map.get(key));
  }
}
