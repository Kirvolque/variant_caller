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
  private SamRecord previousSamRecord = null;
  private boolean previousUsed = false;

  SamRecordIterator(@NonNull final BufferedReader reader) {
    this.reader = reader;
  }

  public boolean hasNextForIntervals(String chromosomeName, ListOfIntervals intervals)
      throws IOException {

    if (previousSamRecord != null) {
      currentSamRecord = previousSamRecord;
      if (previousUsed) {
        return false;
      } else {
        previousUsed = true;
      }
    } else {
      String line;
      if ((line = reader.readLine()) != null) {
        currentSamRecord = SamRecord.init(line);
      } else {
        return false;
      }
    }
    if (!currentSamRecord.getRname().equals(chromosomeName)) {
      previousSamRecord = currentSamRecord;
      return false;
    }
    return intervals.asList().stream().anyMatch(interval -> currentSamRecord.fitInterval(interval));
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
