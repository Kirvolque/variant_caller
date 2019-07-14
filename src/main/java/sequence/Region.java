package sequence;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Region {
  private final List<Nucleotide> nucleotideList;
  private final int startPosition;

  public Region(List<Nucleotide> nucleotideList, int startPosition) {
    this.nucleotideList = nucleotideList;
    this.startPosition = startPosition;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Region region1 = (Region) o;
    return Objects.equals(nucleotideList, region1.nucleotideList)
        && Objects.equals(startPosition, region1.startPosition);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nucleotideList, startPosition);
  }

  public List<Nucleotide> getNucleotideList() {
    return nucleotideList;
  }

  public int getStartPosition() {
    return startPosition;
  }

  public Nucleotide getNucleotideAt(int position) {
    try {
      return nucleotideList.get(position);
    } catch (IndexOutOfBoundsException ex) {
      throw new NoSuchElementException("Current position is not covered by this nucleotideList");
    }
  }
}
