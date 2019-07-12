package sequence;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class RegionSequence {
  private static final String INTERVAL_MISS_EXCEPTION_MESSAGE = "Current position is not covered by any region";
  private static final String REGION_MISS_EXCEPTION_MESSAGE = "No region fit interval with this position";
  private static final String INCORRECT_INPUT_EXCEPTION_MESSAGE = "Nucleotides do not fit intervals";
  private static final String INTERVAL_IS_NOT_PRESENT_EXCEPTION_MESSAGE = "No region fit interval with this position";
  private final ListOfIntervals listOfIntervals;
  private final List<Region> regionList;

  /**
   * Private constructor. Use createInstance() method to get instance of the class.
   *
   * @param listOfIntervals ListOfIntervals class
   * @param regionList list with intervals and nucleotide lists in each interval
   */
  private RegionSequence(ListOfIntervals listOfIntervals, List<Region> regionList) {
    this.listOfIntervals = listOfIntervals;
    this.regionList = regionList;
  }

  /**
   * This method slices the passed string, making the substrings for each of the intervals from the
   * ListOfIntervals.
   *
   * <p>String with nucleotides must fit passed interval list.
   *
   * @param intervals ListOfIntervals that contains intervals for which the string will be cut
   * @param nucleotides string with nucleotides
   * @return instance of RegionSequence
   * @throws NoSuchElementException if nucleotides string is bigger than intervals it should be
   *     covered with
   */
  public static RegionSequence createInstance(ListOfIntervals intervals, String nucleotides) {
    if (intervals.getLength() > nucleotides.length()) {
      throw new NoSuchElementException(INCORRECT_INPUT_EXCEPTION_MESSAGE);
    }

    List<Region> regionList =
        intervals.asList().stream()
            .map(
                interval ->
                    new Region(
                        Nucleotide.fromString(
                            nucleotides.substring(interval.getBegin(), interval.getEnd() + 1)),
                        interval))
            .collect(Collectors.toList());

    return new RegionSequence(intervals, regionList);
  }

  /**
   * Gets the list of intervals representing intervals which are covered by current regionSequence
   * class.
   *
   * @return a list of intervals
   */
  public ListOfIntervals getIntervalList() {
    return listOfIntervals;
  }

  /**
   * Gets the map with intervals and strings with nucleotides in this intervals.
   *
   * @return a map with intervals and strings
   */
  public List<Region> getRegionList() {
    return regionList;
  }

  /**
   * Returns nucleotide in the given position.
   *
   * @param position position to be found in the nucleotidesIntervals
   * @return nucleotide in this position if it exists
   * @throws NoSuchElementException if position is not covered by any region
   */
  public Nucleotide getNucleotideAt(int position) {
    Interval interval =
        listOfIntervals
            .getIntervalByPosition(position)
            .orElseThrow(
                () -> new NoSuchElementException(INTERVAL_MISS_EXCEPTION_MESSAGE));

    Region regionInInterval =
        regionList.stream()
            .filter(region -> region.getInterval().equals(interval))
            .findFirst()
            .orElseThrow(
                () -> new NoSuchElementException(REGION_MISS_EXCEPTION_MESSAGE));

    final int positionInSubstring = position - interval.getBegin();
    return regionInInterval.getNucleotideAt(positionInSubstring);
  }

  /**
   * Returns region in the given interval.
   *
   * @param interval interval to be found in the nucleotidesIntervals
   * @param position position to be found in the interval
   * @return nucleotide list in this interval if it is present
   * @throws NoSuchElementException if position is not covered by any region
   */
  public Nucleotide getNucleotideAt(Interval interval, int position) {
    if (!listOfIntervals.intervalIsPresent(interval)) {
      throw new NoSuchElementException(INTERVAL_IS_NOT_PRESENT_EXCEPTION_MESSAGE);
    }
    Region regionInInterval =
        regionList.stream()
            .filter(region -> region.getInterval().equals(interval))
            .findFirst()
            .orElseThrow(
                () -> new NoSuchElementException(REGION_MISS_EXCEPTION_MESSAGE));

    return regionInInterval.getNucleotideAt(position);
  }

  /**
   * Returns region in the given interval.
   *
   * @param interval interval to be found in the nucleotidesIntervals
   * @return nucleotide list in this interval if it is present
   * @throws NoSuchElementException if position is not covered by any region
   */
  public Region getRegion(Interval interval) {
    if (!listOfIntervals.intervalIsPresent(interval)) {
      throw new NoSuchElementException(INTERVAL_IS_NOT_PRESENT_EXCEPTION_MESSAGE);
    }

    return regionList.stream()
        .filter(region -> region.getInterval().equals(interval))
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException(REGION_MISS_EXCEPTION_MESSAGE));
  }
}
