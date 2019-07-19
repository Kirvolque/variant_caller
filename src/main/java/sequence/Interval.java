package sequence;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@AllArgsConstructor
public class Interval {
  @Getter
  private int begin;
  @Getter
  private int end;

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
