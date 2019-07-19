package samparser;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import samparser.samrecorditerator.SamRecordIterable;
import samparser.samrecorditerator.SamRecordIterator;
import sequence.ListOfIntervals;
import sequence.SamRecord;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SamParser {
  private SamRecordIterator recordIterator;

  public static SamParser parseSam(Path path) throws IOException {
    BufferedReader reader =
        new BufferedReader(new InputStreamReader(new FileInputStream(path.toFile())));
    return new SamParser(new SamRecordIterable(reader).iterator());
  }

  public List<SamRecord> getReadsForRegion(String chromosomeName, ListOfIntervals intervals)
      throws IOException {
    List<SamRecord> suitableSamRecords = new ArrayList<>();
    while (recordIterator.hasNextForIntervals(chromosomeName, intervals)) {
      suitableSamRecords.add(recordIterator.nextForIntervals());
    }
    return suitableSamRecords;
  }
}
