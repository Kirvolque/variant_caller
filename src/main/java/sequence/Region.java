package sequence;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.NoSuchElementException;

@EqualsAndHashCode
@AllArgsConstructor
public class Region {
  @Getter
  private final List<Nucleotide> nucleotideList;
  @Getter
  private final int startPosition;

  public Nucleotide getNucleotideAt(int position) {
    try {
      return nucleotideList.get(position);
    } catch (IndexOutOfBoundsException ex) {
      throw new NoSuchElementException("Current position is not covered by this nucleotideList");
    }
  }
}
