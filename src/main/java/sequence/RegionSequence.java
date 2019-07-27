package sequence;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegionSequence {
  private static final String INTERVAL_MISS_EXCEPTION_MESSAGE =
      "Current position is not covered by any region";
  private static final String INCORRECT_INPUT_EXCEPTION_MESSAGE =
      "Nucleotides do not fit intervals";
  private static final String INTERVAL_IS_NOT_PRESENT_EXCEPTION_MESSAGE =
      "No region fit interval with this position";
  @Getter
  private final ListOfIntervals listOfIntervals;
  @Getter
  private final Map<Interval, String> nucleotidesIntervals;

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

    Map<Interval, String> nucleotidesIntervals =
        intervals.asList().stream()
            .collect(
                Collectors.toMap(
                    interval -> interval,
                    interval -> nucleotides.substring(interval.getBegin(), interval.getEnd() + 1)));

    return new RegionSequence(intervals, nucleotidesIntervals);
  }

  /**
   * @deprecated Returns nucleotide in the given position.
   * @param position position to be found in the nucleotidesIntervals
   * @return nucleotide in this position if it exists
   * @throws NoSuchElementException if position is not covered by any region
   */
  @Deprecated
  public Nucleotide getNucleotideAt(int position) {
    Interval interval =
        listOfIntervals
            .getIntervalByPosition(position)
            .orElseThrow(() -> new NoSuchElementException(INTERVAL_MISS_EXCEPTION_MESSAGE));
    final int positionInSubstring = position - interval.getBegin();
    return Nucleotide.fromCharacter(nucleotidesIntervals.get(interval).charAt(positionInSubstring));
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
      throw new NoSuchElementException(INTERVAL_MISS_EXCEPTION_MESSAGE);
    }
    return Nucleotide.fromCharacter(nucleotidesIntervals.get(interval).charAt(position));
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
    return new Region(
        Nucleotide.fromString(nucleotidesIntervals.get(interval)), interval.getBegin());
  }
}
