package sequence;

import java.util.List;
import java.util.Optional;

public class IntervalList {
  private List<Interval> intervalList;

  public IntervalList(List<Interval> intervalList) {
    this.intervalList = intervalList;
  }

  public int getNumberOfIntervals() {
    return intervalList.size();
  }

  public int getLength() {
    int length = 0;
    for (Interval i : intervalList) {
      length += i.getEnd() - i.getBegin() + 1;
    }
    return length;
  }

  public Optional<Interval> getIntervalByIndex(int index) {
    try {
      return Optional.ofNullable(intervalList.get(index));
    } catch (Exception ex) {
      return Optional.empty();
    }
  }

  public Optional<Interval> getIntervalByPosition(int position) {
    for (Interval i : intervalList) {
      if (i.contains(position)) {
        return Optional.of(i);
      }
    }
    return Optional.empty();
  }
}
