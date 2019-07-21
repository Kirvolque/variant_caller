package fastaparser;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import sequence.FastaSequence;
import sequence.ListOfIntervals;

import java.io.*;
import java.nio.file.Path;
import java.util.NoSuchElementException;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FastaParser {
  private static final String HEADER_START_REGEX = "[>;].*";
  private static final String HEADER_NAME_REGEX = "[>;](%s)";
  private Path path;

  /**
   * Returns instance of the class.
   *
   * @param path path to the file needed to be parsed
   * @return instance of FastaParser to iterate the file
   */
  public static FastaParser init(Path path) {
    if (!path.toFile().exists()) {
      throw new NoSuchElementException(
          String.format("File %s not found", path.getFileName().toString()));
    }
    return new FastaParser(path);
  }

  public FastaSequence getRegionsForChromosome(String chromosomeName, ListOfIntervals intervals) {

    String line;
    StringBuilder currentSequence = new StringBuilder();

    try (BufferedReader reader =
             new BufferedReader(new InputStreamReader(new FileInputStream(path.toFile())))) {
      while ((line = reader.readLine()) != null) {
        if (line.matches(String.format(HEADER_NAME_REGEX, chromosomeName))) {
          while ((line = reader.readLine()) != null) {
            if (line.matches(HEADER_START_REGEX)) {
              break;
            }
            currentSequence.append(line);
          }
          break;
        }
      }
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }

    if (currentSequence.toString().equals("")) {
      throw new NoSuchElementException(
          "Chromosome is not present or bed/fasta files are not sorted");
    }

    return FastaSequence.init(chromosomeName, currentSequence.toString(), intervals);
  }
}
