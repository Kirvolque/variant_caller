package bedparser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sequence.Interval;
import sequence.ListOfIntervals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

class BedParserTest {
  private static final String BED_FILE_NAME = "ex.bed";
  private static final String CHROMOSOME_NAME_1 = "chr1";
  private static final String CHROMOSOME_NAME_2 = "chr2";
  private static Map<String, ListOfIntervals> bedData;

  @BeforeAll
  static void init() throws URISyntaxException, IOException {
    bedData =
        BedParser.collectIntervals(
            Paths.get(
                Objects.requireNonNull(
                    BedParserTest.class.getClassLoader().getResource(BED_FILE_NAME))
                    .toURI()));
  }

  @Test
  @DisplayName("Check correctness of the parsed names of sequences")
  void checkParsedSequenceNames() {
    Assertions.assertTrue(bedData.keySet().contains(CHROMOSOME_NAME_1));
    Assertions.assertTrue(bedData.keySet().contains(CHROMOSOME_NAME_2));
  }

  @Test
  @DisplayName("Check correctness of the parsed intervals")
  void checkParsedIntervals() {
    Assertions.assertTrue(bedData.get(CHROMOSOME_NAME_1).asList().contains(new Interval(0, 25)));
    Assertions.assertTrue(bedData.get(CHROMOSOME_NAME_1).asList().contains(new Interval(26, 45)));
    Assertions.assertTrue(bedData.get(CHROMOSOME_NAME_2).asList().contains(new Interval(0, 3)));
    Assertions.assertTrue(bedData.get(CHROMOSOME_NAME_2).asList().contains(new Interval(4, 40)));
  }
}
