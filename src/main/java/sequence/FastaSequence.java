package sequence;

import java.util.Map;
import java.util.Set;

public class FastaSequence {
  private final Map<String, String> fastaData;

  public FastaSequence(Map<String, String> fastaData) {
    this.fastaData = fastaData;
  }

  public Set<String> getChromosomes() {
    return fastaData.keySet();
  }

  public String getSequence(String chromosome) throws NullPointerException {
    return fastaData.get(chromosome);
  }

  public Nucleotide getNucleotide(String chromosome, int nucleotidePosition) throws NullPointerException, IndexOutOfBoundsException {
    return Nucleotide.fromString(Character.toString(fastaData.get(chromosome).charAt(nucleotidePosition)));
  }
}
