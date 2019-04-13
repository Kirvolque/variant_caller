package sequence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IntervalTest {
  private static final Interval INTERVAL = new Interval(0, 10);

  @Test
  @DisplayName("Test contain() method")
  void contains() {
    Assertions.assertTrue(INTERVAL.contains(0));
    Assertions.assertTrue(INTERVAL.contains(5));
    Assertions.assertTrue(INTERVAL.contains(10));
    Assertions.assertFalse(INTERVAL.contains(-1));
    Assertions.assertFalse(INTERVAL.contains(11));
  }
}
