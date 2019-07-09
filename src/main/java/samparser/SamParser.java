package samparser;

import sequence.SamRecord;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class SamParser {
  public static Stream<SamRecord> parseSam(Path filePath) throws IOException {
    return Files.lines(filePath).map(SamRecord::init);
  }
}
