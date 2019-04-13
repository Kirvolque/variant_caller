package sequence;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RegionsSequence {
  private static IntervalList intervalList;
  private static Map<Interval, String> nucleotidesIntervals;

  private RegionsSequence(IntervalList intervalList, Map<Interval, String> nucleotidesIntervals) {
    RegionsSequence.intervalList = intervalList;
    RegionsSequence.nucleotidesIntervals = nucleotidesIntervals;
  }

  public static RegionsSequence createInstance(IntervalList intervals, String nucleotides) throws Exception {
    if (intervals.getLength() != nucleotides.length()) {
      throw new Exception("The length of the parameters is not equal");
    }

    Map<Interval, String> nucleotidesIntervals = new HashMap<>();

    for (int i = 0; i < intervals.getNumberOfIntervals(); i++) {
      Optional<Interval> interval = intervals.getIntervalByIndex(i);
      interval.ifPresent(interval1 -> nucleotidesIntervals.put(interval1, nucleotides.substring(interval1.getBegin(), interval1.getEnd() + 1)));
    }

    return new RegionsSequence(intervals, nucleotidesIntervals);
  }

  public IntervalList getIntervalList() {
    return intervalList;
  }

  public Map<Interval, String> getNucleotidesIntervals() {
    return nucleotidesIntervals;
  }

  public Optional<Nucleotide> getNucleotideAt(int position) {
    final Optional<Interval> interval = intervalList.getIntervalByPosition(position);
    if (interval.isPresent()) {
      final int positionInSubstring = position - interval.get().getBegin();
      return Optional.ofNullable(Nucleotide.fromString(Character.toString(nucleotidesIntervals.get(interval.get()).charAt(positionInSubstring))));
    }
    return Optional.empty();
  }
}
