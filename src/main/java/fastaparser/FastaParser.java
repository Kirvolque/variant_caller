package fastaparser;

import fastaparser.fastasequenceiterator.FastaSequenceIterable;
import fastaparser.fastasequenceiterator.FastaSequenceIterator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import sequence.FastaSequence;
import sequence.ListOfIntervals;

import java.io.*;
import java.nio.file.Path;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FastaParser {
  private FastaSequenceIterator sequenceIterator;

  /**
   * Returns instance of the class.
   *
   * @param path path to the file needed to be parsed
   * @return instance of FastaParser to iterate the file
   */
  public static FastaParser parseFasta(Path path) {
    BufferedReader reader;
    try {
      reader = new BufferedReader(new InputStreamReader(new FileInputStream(path.toFile())));
    } catch (FileNotFoundException e) {
      throw new UncheckedIOException(
          String.format("File %s not found", path.getFileName().toString()), e);
    }
    return new FastaParser(new FastaSequenceIterable(reader).iterator());
  }

  public FastaSequence getNext(ListOfIntervals intervals) {
    if (sequenceIterator.hasNext() && sequenceIterator.hasNext(intervals)) {
      return sequenceIterator.next();
    }
    return null;
  }
}
