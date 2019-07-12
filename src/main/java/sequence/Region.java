package sequence;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Region {
  private final List<Nucleotide> nucleotideList;
  private final Interval interval;

  public Region(List<Nucleotide> nucleotideList, Interval interval) {
    this.nucleotideList = nucleotideList;
    this.interval = interval;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Region region1 = (Region) o;
    return Objects.equals(nucleotideList, region1.nucleotideList)
        && Objects.equals(interval, region1.interval);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nucleotideList, interval);
  }

  public List<Nucleotide> getNucleotideList() {
    return nucleotideList;
  }

  public Interval getInterval() {
    return interval;
  }

  public Nucleotide getNucleotideAt(int position) {
    try {
      return nucleotideList.get(position);
    } catch (IndexOutOfBoundsException ex) {
      throw new NoSuchElementException("Current position is not covered by this nucleotideList");
    }
  }
}
