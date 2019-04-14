package sequence;

import java.util.HashMap;
import java.util.Map;

public class RegionsSequence {
  private static IntervalList intervalList;
  private static Map<Interval, String> nucleotidesIntervals;

  /**
   * Private constructor.
   * Use createInstance() method to get instance of the class.
   *
   * @param intervalList         IntervalList that contains intervals for which the string was cut
   * @param nucleotidesIntervals map with intervals and nucleotide substrings in each interval
   */
  private RegionsSequence(IntervalList intervalList, Map<Interval, String> nucleotidesIntervals) {
    RegionsSequence.intervalList = intervalList;
    RegionsSequence.nucleotidesIntervals = nucleotidesIntervals;
  }

  /**
   * This method slices the passed string,
   * making the substrings for each of the intervals
   * from the IntervalList.
   * <p>
   * String with nucleotides must fit passed interval list.
   *
   * @param intervals   IntervalList that contains intervals
   *                    for which the string will be cut
   * @param nucleotides string with nucleotides
   * @return instance of RegionsSequence
   * @throws Exception if nucleotides string is bigger
   *                   than intervals it should be covered with
   */
  public static RegionsSequence createInstance(IntervalList intervals, String nucleotides) throws Exception {
    if (intervals.getLength() > nucleotides.length()) {
      throw new Exception("Nucleotides do not fit intervals");
    }

    Map<Interval, String> nucleotidesIntervals = new HashMap<>();

    for (int i = 0; i < intervals.getNumberOfIntervals(); i++) {
      Interval interval = intervals.getIntervalByIndex(i);
      nucleotidesIntervals.put(interval, nucleotides.substring(interval.getBegin(), interval.getEnd() + 1));
    }

    return new RegionsSequence(intervals, nucleotidesIntervals);
  }

  /**
   * Gets the list of intervals representing intervals which
   * are covered by current regionSequence class.
   *
   * @return a list of intervals
   */
  public IntervalList getIntervalList() {
    return intervalList;
  }

  /**
   * Gets the map with intervals and strings with nucleotides in this intervals.
   *
   * @return a map with intervals and strings
   */
  public Map<Interval, String> getNucleotidesIntervals() {
    return nucleotidesIntervals;
  }

  /**
   * Returns nucleotide in the given position.
   *
   * @param position position to be found in the nucleotidesIntervals
   * @return nucleotide in this position if it exists
   * @throws Exception if position is not covered by any region
   */
  public Nucleotide getNucleotideAt(int position) throws Exception {
    Interval interval = intervalList.getIntervalByPosition(position).orElseThrow(() -> new Exception("Current position is not covered by any region"));
    final int positionInSubstring = position - interval.getBegin();
    return Nucleotide.fromString(Character.toString(nucleotidesIntervals.get(interval).charAt(positionInSubstring)));
  }
}
