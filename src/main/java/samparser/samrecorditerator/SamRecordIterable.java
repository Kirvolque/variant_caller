package samparser.samrecorditerator;

import lombok.NonNull;
import sequence.SamRecord;

import java.io.BufferedReader;

public class SamRecordIterable implements Iterable<SamRecord> {
  private BufferedReader reader;

  public SamRecordIterable(@NonNull final BufferedReader reader) {
    this.reader = reader;
  }

  @Override
  public SamRecordIterator iterator() {
    return new SamRecordIterator(reader);
  }
}
