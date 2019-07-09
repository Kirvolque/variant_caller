package sequence;

import java.util.Objects;

public class Interval {
  private int begin;
  private int end;

  public Interval(int begin, int end) {
    this.begin = begin;
    this.end = end;
  }

  public int getBegin() {
    return begin;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Interval interval = (Interval) o;
    return begin == interval.begin &&
        end == interval.end;
  }

  @Override
  public int hashCode() {
    return Objects.hash(begin, end);
  }

  public int getEnd() {
    return end;
  }

  /**
   * Gets the length of the interval including the end position.
   *
   * @return a length of the interval
   */
  public int length() {
    return end - begin + 1;
  }


  public boolean contains(int position) {
    return (begin <= position) && (position <= end);
  }
}
