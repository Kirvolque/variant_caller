package sequence;

import java.util.List;
import java.util.Objects;

public class Region {
  private final List<Nucleotide> region;
  private final int startingPosition;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Region region1 = (Region) o;
    return startingPosition == region1.startingPosition &&
        Objects.equals(region, region1.region);
  }

  @Override
  public int hashCode() {
    return Objects.hash(region, startingPosition);
  }

  public Region(List<Nucleotide> region, int startingPosition) {
    this.region = region;
    this.startingPosition = startingPosition;
  }

  public List<Nucleotide> getRegion() {
    return region;
  }

  public int getStartingPosition() {
    return startingPosition;
  }

  public Nucleotide getNucleotideAt(int position) {
    try {
      return region.get(position);
    } catch (IndexOutOfBoundsException ex) {
      throw new RuntimeException("Current position is not covered by this region");
    }
  }
}
