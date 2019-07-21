package samparser.samrecorditerator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import sequence.Interval;
import sequence.SamRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Iterator;

@RequiredArgsConstructor
public class SamRecordIterator implements Iterator<SamRecord> {
  @NonNull
  private BufferedReader reader;
  private SamRecord currentSamRecord;

  public boolean hasNextForIntervals(String chromosomeName, Interval interval) {
    if (currentSamRecord == null) {
      String line;
      try {
        line = reader.readLine();
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }
      if (line != null) {
        currentSamRecord = SamRecord.init(line);
      } else {
        return false;
      }
    }

    return currentSamRecord.getRname().equals(chromosomeName)
        && currentSamRecord.fitInterval(interval);
  }

  public SamRecord nextForIntervals() {
    SamRecord result = currentSamRecord;
    currentSamRecord = null;
    return result;
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
