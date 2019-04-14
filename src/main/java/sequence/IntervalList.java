package sequence;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class IntervalList {
  private List<Interval> listWithIntervals;

  /**
   * Class constructor.
   *
   * @param listWithIntervals list with intervals
   */
  public IntervalList(List<Interval> listWithIntervals) {
    this.listWithIntervals = listWithIntervals;
  }

  /**
   * Gets the number of intervals contained in listWithIntervals.
   *
   * @return a number of intervals
   */
  public int getNumberOfIntervals() {
    return listWithIntervals.size();
  }

  /**
   * Gets the number of all positions covered by intervals.
   *
   * @return a length of intervals
   */
  public int getLength() {
    AtomicInteger length = new AtomicInteger();
    listWithIntervals.forEach(i -> length.addAndGet(i.getEnd() - i.getBegin() + 1));
    return length.get();
  }

  /**
   * Gets interval from the listWithIntervals by it`s index in the list.
   *
   * @param index index to be found in the listWithIntervals
   * @return interval from the listWithIntervals
   * @throws IndexOutOfBoundsException if there is no interval under such index
   */
  public Interval getIntervalByIndex(int index) throws IndexOutOfBoundsException {
    return listWithIntervals.get(index);
  }

  /**
   * Gets interval from the listWithIntervals by the position it covers.
   *
   * @param position position to be found in each of the intervals
   * @return interval from the listWithIntervals if this position contains
   * in any of the intervals
   */
  public Optional<Interval> getIntervalByPosition(int position) {
    return listWithIntervals.stream().filter(i -> i.contains(position)).findFirst();
  }
}
