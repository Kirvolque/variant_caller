package sequence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListOfIntervals {
  private List<Interval> listOfIntervals;

  /**
   * Class constructor.
   *
   * @param listOfIntervals list with intervals
   */
  public ListOfIntervals(List<Interval> listOfIntervals) {
    this.listOfIntervals = listOfIntervals;
  }

  /**
   * Gets the list of intervals.
   *
   * @return a listOfIntervals as a list
   */
  public List<Interval> asList() {
    return new ArrayList<>(this.listOfIntervals);
  }

  /**
   * Gets the number of intervals contained in listOfIntervals.
   *
   * @return a number of intervals
   */
  public int getNumberOfIntervals() {
    return listOfIntervals.size();
  }

  /**
   * Gets the number of all positions covered by intervals.
   *
   * @return a length of intervals
   */
  public int getLength() {
    return listOfIntervals.stream().mapToInt(Interval::length).sum();
  }

  /**
   * Gets interval from the listOfIntervals by it`s index in the list.
   *
   * @param index index to be found in the listOfIntervals
   * @return interval from the listOfIntervals
   * @throws IndexOutOfBoundsException if there is no interval under such index
   */
  public Interval getIntervalByIndex(int index) {
    return listOfIntervals.get(index);
  }

  /**
   * Gets interval from the listOfIntervals by the position it covers.
   *
   * @param position position to be found in each of the intervals
   * @return interval from the listOfIntervals if this position contains
   * in any of the intervals
   */
  public Optional<Interval> getIntervalByPosition(int position) {
    return listOfIntervals.stream().filter(i -> i.contains(position)).findFirst();
  }

  /**
   * Check presence of the interval in the list.
   *
   * @param interval interval to be checked
   * @return true if it is present or false if not
   */
  public Boolean intervalIsPresent(Interval interval) {
    return listOfIntervals.contains(interval);
  }
}
