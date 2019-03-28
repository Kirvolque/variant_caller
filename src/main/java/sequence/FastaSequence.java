package sequence;

import java.util.Map;

public class FastaSequence {
  private final Map<String, String> fastaData;

  public Map<String, String> getFastaData() {
    return fastaData;
  }

  public FastaSequence(Map<String, String> fastaData) {
    this.fastaData = fastaData;
  }

  public Nucleotide getNucleotide(String chromosome, int nucleotidePosition) throws NullPointerException, IndexOutOfBoundsException {
    return Nucleotide.fromString(Character.toString(fastaData.get(chromosome).charAt(nucleotidePosition)));
  }
}
