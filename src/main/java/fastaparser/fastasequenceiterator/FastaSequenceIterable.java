package fastaparser.fastasequenceiterator;

import lombok.NonNull;
import sequence.FastaSequence;

import java.io.BufferedReader;

public class FastaSequenceIterable implements Iterable<FastaSequence> {
  private BufferedReader reader;

  public FastaSequenceIterable(@NonNull final BufferedReader reader) {
    this.reader = reader;
  }

  @Override
  public FastaSequenceIterator iterator() {
    return new FastaSequenceIterator(reader);
  }
}
