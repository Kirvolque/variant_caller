package sequence;

import java.util.Map;

public class FastaSequence {
  private Map<String, String> fastaData;

  public FastaSequence(Map<String, String> fastaData) {
    this.fastaData = fastaData;
  }

  public Nucleotide getNucleotide(String chromosome, int nucleotidePosition) {
    return Nucleotide.fromString(Character.toString(fastaData.get(chromosome).charAt(nucleotidePosition)));
  }
}
