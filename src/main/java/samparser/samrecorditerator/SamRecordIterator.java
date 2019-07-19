package samparser.samrecorditerator;

import lombok.NonNull;
import sequence.ListOfIntervals;
import sequence.SamRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;

public class SamRecordIterator implements Iterator<SamRecord> {
  private BufferedReader reader;
  private SamRecord currentSamRecord;

  SamRecordIterator(@NonNull final BufferedReader reader) {
    this.reader = reader;
  }

  public boolean hasNextForIntervals(String chromosomeName, ListOfIntervals intervals) throws IOException {
    currentSamRecord = SamRecord.init(reader.readLine());
    if (!currentSamRecord.getRname().equals(chromosomeName)) {
      return false;
    }
    return intervals.asList().stream()
        .anyMatch(
            interval ->
                ((interval.getBegin() <= currentSamRecord.getPos() - 1)
                    && (interval.length() >= currentSamRecord.getCigarLength() - 1)));
  }

  public SamRecord nextForIntervals() {
    return currentSamRecord;
  }

  @Override
  public boolean hasNext() {
    try {
      return reader.ready();
    } catch (IOException e) {
      return Boolean.FALSE;
    }
  }

  @Override
  public SamRecord next() {
    throw new UnsupportedOperationException("Inherited next not supported");
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException("Remove not supported");
  }
}
