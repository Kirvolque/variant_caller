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

  /**
   * Gets the length of the interval including the end position.
   *
   * @return a length of the interval
   */
  public int length() {
    return end - begin + 1;
  }


  public boolean contains(int position) {
    return (position >= begin) && (position <= end);
  }
}
