package sequence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IntervalTest {
  private static final Interval INTERVAL_1 = new Interval(0, 10);
  private static final Interval INTERVAL_2 = new Interval(0, 100);

  @Test
  @DisplayName("Test length() method")
  void length() {
    Assertions.assertEquals(11, INTERVAL_1.length());
    Assertions.assertEquals(101, INTERVAL_2.length());
  }

  @Test
  @DisplayName("Test contain() method")
  void contains() {
    Assertions.assertTrue(INTERVAL_1.contains(0));
    Assertions.assertTrue(INTERVAL_1.contains(5));
    Assertions.assertTrue(INTERVAL_1.contains(10));
    Assertions.assertFalse(INTERVAL_1.contains(-1));
    Assertions.assertFalse(INTERVAL_1.contains(11));
  }
}
