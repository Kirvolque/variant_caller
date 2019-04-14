package sequence;

import java.util.Map;
import java.util.Set;

public class FastaSequence {
  private final Map<String, String> fastaData;

  /**
   * Class constructor.
   *
   * @param fastaData map that contains name of the chromosome as a key
   *                  and sting of nucleotides as a value
   */
  public FastaSequence(Map<String, String> fastaData) {
    this.fastaData = fastaData;
  }

  /**
   * Gets set of chromosomes stored in the instance of the class.
   *
   * @return set of strings represents names of the chromosomes
   */
  public Set<String> getChromosomes() {
    return fastaData.keySet();
  }

  /**
   * Gets sting of nucleotides contained in the fastaData.
   *
   * @param chromosome name of the chromosome
   * @return sting of nucleotides with this chromosome name
   * @throws NullPointerException if there is no chromosome with such name
   */
  public String getSequence(String chromosome) throws NullPointerException {
    return fastaData.get(chromosome);
  }

  /**
   * Gets nucleotide in the chromosome with such name by it`s position.
   *
   * @param chromosome         name of the chromosome
   * @param nucleotidePosition position of the nucleotide in this chromosome
   * @return nucleotide in this position
   * @throws NullPointerException      if there is no chromosome with such name
   * @throws IndexOutOfBoundsException if there is no nucleotide in this position
   */
  public Nucleotide getNucleotide(String chromosome, int nucleotidePosition) throws NullPointerException, IndexOutOfBoundsException {
    return Nucleotide.fromString(Character.toString(fastaData.get(chromosome).charAt(nucleotidePosition)));
  }
}
