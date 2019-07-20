package bedparser;

import sequence.BedRecord;
import sequence.Interval;
import sequence.ListOfIntervals;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class BedParser {
  private static Map<String, List<Interval>> parseBed(Path filePath) {
    Map<String, List<Interval>> collectedMap = new LinkedHashMap<>();
    try (Stream<String> s = Files.lines(filePath)) {
      s.map(BedRecord::init)
          .forEach(
              bedRecord ->
                  collectedMap
                      .computeIfAbsent(bedRecord.getChrom(), key -> new ArrayList<>())
                      .add(bedRecord.getInterval()));
    } catch (IOException e) {
      throw new UncheckedIOException(
          String.format("File %s not found", filePath.getFileName().toString()), e);
    }
    return collectedMap;
  }

  public static Map<String, ListOfIntervals> collectIntervals(Path filePath) {
    Map<String, List<Interval>> collectedMap = parseBed(filePath);

    Map<String, ListOfIntervals> result = new LinkedHashMap<>();
    for (Map.Entry<String, List<Interval>> item : collectedMap.entrySet()) {
      result.put(item.getKey(), new ListOfIntervals(item.getValue()));
    }
    return result;
  }
}
