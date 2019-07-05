package variantcaller;

import fastaparser.FastaParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samparser.SamParser;
import sequence.FastaSequence;
import sequence.SamRecord;
import vcfwriter.variation.Variation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

class VariantCallerTest {
  private static final Double minAlleleFrequency = 0.0;
  private static final String CHROMOSOME_NAME_1 = "chr1";
  private static final String CHROMOSOME_NAME_2 = "chr2";
  private static VariantCaller variantCaller = new VariantCaller();
  private static List<Variation> variationList;

  @BeforeAll
  static void init() throws URISyntaxException, IOException {
    FastaSequence fastaSequence =
        FastaParser.parseFasta(
            Objects.requireNonNull(VariantCallerTest.class.getClassLoader().getResource("ex.fa"))
                .getPath(),
            Paths.get(
                Objects.requireNonNull(
                    VariantCallerTest.class.getClassLoader().getResource("ex.bed"))
                    .toURI()));
    Stream<SamRecord> samRecordStream =
        SamParser.parseSam(
            Paths.get(
                Objects.requireNonNull(
                    VariantCallerTest.class.getClassLoader().getResource("ex.sam"))
                    .toURI()));
    variantCaller.processSamRecords(fastaSequence, samRecordStream);
    variationList = variantCaller.filterVariations(minAlleleFrequency);
  }

  @Test
  @DisplayName("Check variations with matches only")
  void checkVariationsWithOnlyMatches() {
    Assertions.assertEquals(
        new Variation(CHROMOSOME_NAME_1, 9, "GATAAGATAG", "ATAAGATAGC"), variationList.get(0));
    Assertions.assertEquals(
        new Variation(CHROMOSOME_NAME_2, 19, "AGTCTACAGA", "GTCTACAGAG"), variationList.get(1));
  }
}
