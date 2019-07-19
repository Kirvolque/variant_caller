package fastaparser;

import fastaparser.fastasequenceiterator.FastaSequenceIterable;
import fastaparser.fastasequenceiterator.FastaSequenceIterator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import sequence.FastaSequence;
import sequence.ListOfIntervals;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FastaParser {
  private FastaSequenceIterator sequenceIterator;

  /**
   * Returns instance of the class.
   *
   * @param path path to the file needed to be parsed
   * @return instance of FastaParser to iterate the file
   * @throws IOException if there is no file with such path
   */
  public static FastaParser parseFasta(Path path) throws IOException {
    BufferedReader reader =
        new BufferedReader(new InputStreamReader(new FileInputStream(path.toFile())));
    return new FastaParser(new FastaSequenceIterable(reader).iterator());
  }

  public FastaSequence getNext(ListOfIntervals intervals) throws IOException {
    if (sequenceIterator.hasNext() && sequenceIterator.hasNext(intervals)) {
      return sequenceIterator.next();
    }
    return null;
  }
}
