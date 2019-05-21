package bedparser;

import sequence.BedRecord;
import sequence.Interval;
import sequence.ListOfIntervals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class BedParser {
  private static Map<String, List<Interval>> parseBed(Path filePath) throws IOException {
    Map<String, List<Interval>> collectedMap = new HashMap<>();
    try (Stream<String> s = Files.lines(filePath)) {
      s.map(BedRecord::init)
          .forEach(
              person ->
                  collectedMap
                      .computeIfAbsent(person.getChrom(), key -> new ArrayList<>())
                      .add(person.getInterval()));
    }
    return collectedMap;
  }

  public static Map<String, ListOfIntervals> collectIntervals(Path filePath) throws IOException {
    Map<String, List<Interval>> collectedMap = parseBed(filePath);

    Map<String, ListOfIntervals> result = new HashMap<>();
    for (Map.Entry<String, List<Interval>> item : collectedMap.entrySet()) {
      result.put(item.getKey(), new ListOfIntervals(item.getValue()));
    }
    return result;
  }
}
