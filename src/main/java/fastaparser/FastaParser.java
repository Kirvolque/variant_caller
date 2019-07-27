package fastaparser;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import sequence.FastaSequence;
import sequence.ListOfIntervals;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.NoSuchElementException;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FastaParser implements AutoCloseable {
  private static final String HEADER_START_REGEX = "[>;].*";
  private static final String HEADER_NAME_REGEX = "[>;](%s)";
  private final FileInputStream fInput;
  private BufferedReader reader;

  /**
   * Returns instance of the class.
   *
   * @param path path to the file needed to be parsed
   * @return instance of FastaParser to iterate the file
   */
  public static FastaParser init(Path path) {
    try {
      FileInputStream fIn = new FileInputStream(path.toFile());
      return new FastaParser(fIn, new BufferedReader(new InputStreamReader(fIn)));
    } catch (FileNotFoundException e) {
      throw new NoSuchElementException(
          String.format("File %s not found", path.getFileName().toString()));
    }
  }

  public FastaSequence getRegionsForChromosome(String chromosomeName, ListOfIntervals intervals) {
    String line;
    StringBuilder currentSequence = new StringBuilder();
    try {
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
      fInput.getChannel().position(0);
      reader = new BufferedReader(new InputStreamReader(fInput));
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }

    if (currentSequence.toString().equals("")) {
      throw new NoSuchElementException(
          "Chromosome is not present or bed/fasta files are not sorted");
    }

    return FastaSequence.init(chromosomeName, currentSequence.toString(), intervals);
  }

  @Override
  public void close() {
    try {
      fInput.close();
      reader.close();
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
