package sequence;

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

  public int getEnd() {
    return end;
  }

  public boolean contains(int position) {
    return (position >= begin) && (position <= end);
  }
}
