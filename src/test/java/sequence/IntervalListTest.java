package sequence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class IntervalListTest {
  private static final Interval INTERVAL_1 = new Interval(0, 3);
  private static final Interval INTERVAL_2 = new Interval(4, 5);
  private static final Interval INTERVAL_3 = new Interval(6, 9);
  private static IntervalList intervalList;

  @BeforeAll
  @DisplayName("Init intervalList")
  static void init() {
    List<Interval> intervals = new ArrayList<>();
    intervals.add(INTERVAL_1);
    intervals.add(INTERVAL_2);
    intervals.add(INTERVAL_3);
    intervalList = new IntervalList(intervals);
  }

  @Test
  void getNumberOfIntervals() {
    Assertions.assertEquals(3, intervalList.getNumberOfIntervals());
  }

  @Test
  void getLength() {
    Assertions.assertEquals(10, intervalList.getLength());
  }

  @Test
  void getIntervalByIndex() {
    Assertions.assertEquals(INTERVAL_1, intervalList.getIntervalByIndex(0));
    Assertions.assertEquals(INTERVAL_2, intervalList.getIntervalByIndex(1));
    Assertions.assertEquals(INTERVAL_3, intervalList.getIntervalByIndex(2));
  }

  @Test
  void getNonexistentIntervalByIndex() {
    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> intervalList.getIntervalByIndex(-1));
    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> intervalList.getIntervalByIndex(5));
  }

  @Test
  void getIntervalByPosition() {
    Assertions.assertEquals(INTERVAL_1, intervalList.getIntervalByPosition(0).get());
    Assertions.assertEquals(INTERVAL_1, intervalList.getIntervalByPosition(1).get());
    Assertions.assertEquals(INTERVAL_2, intervalList.getIntervalByPosition(5).get());
    Assertions.assertEquals(INTERVAL_3, intervalList.getIntervalByPosition(8).get());
    Assertions.assertEquals(INTERVAL_3, intervalList.getIntervalByPosition(9).get());
  }

  @Test
  void getNonexistentIntervalByPosition() {
    Assertions.assertEquals(Optional.empty(), intervalList.getIntervalByPosition(-1));
    Assertions.assertEquals(Optional.empty(), intervalList.getIntervalByPosition(11));
  }
}
