package sequence;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@EqualsAndHashCode
@AllArgsConstructor
public class Region {
  @Getter
  private final List<Nucleotide> nucleotideList;
  @Getter
  private final int startPosition;

  private void checkPositionIsCoveredByNucleotideList(int position) {
    if (position >= nucleotideList.size()) {
      throw new IndexOutOfBoundsException("Given position is not covered by region");
    }
  }

  public Nucleotide getNucleotideAt(int position) {
    checkPositionIsCoveredByNucleotideList(position);
    return nucleotideList.get(position);
  }
}
