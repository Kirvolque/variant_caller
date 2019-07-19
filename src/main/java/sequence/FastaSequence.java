package sequence;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FastaSequence {
  @Getter
  private final String chromosomeName;
  @Getter
  private final RegionSequence regionSequence;

  public static FastaSequence init(String chromosomeName, String seq, ListOfIntervals intervals) {
    return new FastaSequence(chromosomeName, RegionSequence.createInstance(intervals, seq));
  }

  /**
   * Gets nucleotide in the chromosome with such name by it`s position.
   *
   * @param interval position of the nucleotide in this chromosome
   * @return nucleotide in this position
   * @throws RuntimeException if there is no such position in this chromosome
   */
  public Nucleotide getNucleotide(Interval interval, int position) {
    return regionSequence.getRegion(interval).getNucleotideAt(position);
  }
}
