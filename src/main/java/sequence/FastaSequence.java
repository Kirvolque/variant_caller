package sequence;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FastaSequence {
  private Map<String, String> fastaData;

  public FastaSequence(List<String> sequenceNames, List<String> sequences) {
    Iterator<String> i1 = sequenceNames.iterator();
    Iterator<String> i2 = sequences.iterator();
    this.fastaData = new HashMap<>();
    while (i1.hasNext() || i2.hasNext()) fastaData.put(i1.next(), i2.next());
  }

  private static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) {
    if (c != null && string != null) {
      try {
        return Enum.valueOf(c, string.trim().toUpperCase());
      } catch (IllegalArgumentException ex) {
      }
    }
    return (T) Nucleotide.UNDEFINED;
  }

  public Nucleotide getNucleotide(String chromosome, int nucleotidePosition) {
    return getEnumFromString(Nucleotide.class, Character.toString(fastaData.get(chromosome).charAt(nucleotidePosition)));
  }
}
