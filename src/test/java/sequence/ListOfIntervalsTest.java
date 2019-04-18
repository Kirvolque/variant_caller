package sequence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class ListOfIntervalsTest {
  private static final Interval INTERVAL_1 = new Interval(0, 3);
  private static final Interval INTERVAL_2 = new Interval(4, 5);
  private static final Interval INTERVAL_3 = new Interval(6, 9);
  private static ListOfIntervals listOfIntervals;

  @BeforeAll
  @DisplayName("Init listOfIntervals")
  static void init() {
    List<Interval> intervals = new ArrayList<>();
    intervals.add(INTERVAL_1);
    intervals.add(INTERVAL_2);
    intervals.add(INTERVAL_3);
    listOfIntervals = new ListOfIntervals(intervals);
  }

  @Test
  void getNumberOfIntervals() {
    Assertions.assertEquals(3, listOfIntervals.getNumberOfIntervals());
  }

  @Test
  void getLength() {
    Assertions.assertEquals(10, listOfIntervals.getLength());
  }

  @Test
  void getIntervalByIndex() {
    Assertions.assertEquals(INTERVAL_1, listOfIntervals.getIntervalByIndex(0));
    Assertions.assertEquals(INTERVAL_2, listOfIntervals.getIntervalByIndex(1));
    Assertions.assertEquals(INTERVAL_3, listOfIntervals.getIntervalByIndex(2));
  }

  @Test
  void getNonexistentIntervalByIndex() {
    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> listOfIntervals.getIntervalByIndex(-1));
    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> listOfIntervals.getIntervalByIndex(5));
  }

  @Test
  void getIntervalByPosition() {
    Assertions.assertEquals(INTERVAL_1, listOfIntervals.getIntervalByPosition(0).get());
    Assertions.assertEquals(INTERVAL_1, listOfIntervals.getIntervalByPosition(1).get());
    Assertions.assertEquals(INTERVAL_2, listOfIntervals.getIntervalByPosition(5).get());
    Assertions.assertEquals(INTERVAL_3, listOfIntervals.getIntervalByPosition(8).get());
    Assertions.assertEquals(INTERVAL_3, listOfIntervals.getIntervalByPosition(9).get());
  }

  @Test
  void getNonexistentIntervalByPosition() {
    Assertions.assertEquals(Optional.empty(), listOfIntervals.getIntervalByPosition(-1));
    Assertions.assertEquals(Optional.empty(), listOfIntervals.getIntervalByPosition(11));
  }
}
