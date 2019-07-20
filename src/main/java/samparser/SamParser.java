package samparser;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import samparser.samrecorditerator.SamRecordIterable;
import samparser.samrecorditerator.SamRecordIterator;
import sequence.ListOfIntervals;
import sequence.SamRecord;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SamParser {
  private SamRecordIterator recordIterator;

  public static SamParser parseSam(Path path) {
    BufferedReader reader;
    try {
      reader = new BufferedReader(new InputStreamReader(new FileInputStream(path.toFile())));
    } catch (FileNotFoundException e) {
      throw new UncheckedIOException(
          String.format("File %s not found", path.getFileName().toString()), e);
    }
    return new SamParser(new SamRecordIterable(reader).iterator());
  }

  public List<SamRecord> getReadsForRegion(String chromosomeName, ListOfIntervals intervals) {
    List<SamRecord> suitableSamRecords = new ArrayList<>();
    while (recordIterator.hasNextForIntervals(chromosomeName, intervals)) {
      suitableSamRecords.add(recordIterator.nextForIntervals());
    }
    return suitableSamRecords;
  }
}
