package sequence;

import bedparser.BedParser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class FastaSequence {
  private final Map<String, RegionsSequence> fastaData;

  /**
   * Class constructor.
   *
   * @param fastaData map that contains name of the chromosome as a key
   *                  and RegionsSequence of nucleotides as a value
   */
  private FastaSequence(Map<String, RegionsSequence> fastaData) {
    this.fastaData = fastaData;
  }

  public static FastaSequence init(Map<String, String> data, Path pathToBedFile) throws IOException {
    Map<String, ListOfIntervals> bedData = BedParser.collectIntervals(pathToBedFile);

    Map<String, RegionsSequence> result = new HashMap<>();

    for (Map.Entry item : bedData.entrySet()) {
      result.put((String) item.getKey(), RegionsSequence.createInstance((ListOfIntervals) item.getValue(), data.get(item.getKey())));
    }

    return new FastaSequence(result);
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
   * @return RegionsSequence of nucleotides with this chromosome name
   * @throws NoSuchElementException if there is no chromosome with such name
   */
  public RegionsSequence getSequence(String chromosome) {
    if (fastaData.get(chromosome) == null) {
      throw new NoSuchElementException("No such chromosome");
    }
    return fastaData.get(chromosome);
  }

  /**
   * Gets nucleotide in the chromosome with such name by it`s position.
   *
   * @param chromosome         name of the chromosome
   * @param nucleotidePosition position of the nucleotide in this chromosome
   * @return nucleotide in this position
   * @throws IndexOutOfBoundsException if there is no such position in this chromosome
   */
  public Nucleotide getNucleotide(String chromosome, int nucleotidePosition) {
    return getSequence(chromosome).getNucleotideAt(nucleotidePosition);
  }
}
