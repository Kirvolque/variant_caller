package variantcaller;

import bedparser.BedParser;
import fastaparser.FastaParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samparser.SamParser;
import sequence.BedData;
import vcfwriter.variation.Variation;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class VariantCallerTest {
  private static final Double minAlleleFrequency = 0.0;
  private static final String CHROMOSOME_NAME_1 = "chr1";
  private static final String CHROMOSOME_NAME_2 = "chr2";
  private static final String FASTA_FILE_NAME = "ex.fa";
  private static final String BED_FILE_NAME = "ex.bed";
  private static List<Variation> variationList;

  @BeforeAll
  static void init() throws URISyntaxException {
    BedData bedData =
        BedParser.collectIntervals(
            Paths.get(
                Objects.requireNonNull(
                        VariantCallerTest.class.getClassLoader().getResource(BED_FILE_NAME))
                    .toURI()));
    try (SamParser samParser =
            SamParser.init(
                Paths.get(
                    Objects.requireNonNull(
                            VariantCallerTest.class.getClassLoader().getResource("ex.sam"))
                        .toURI()));
        FastaParser fastaParser =
            FastaParser.init(
                Paths.get(
                    Objects.requireNonNull(
                            VariantCallerTest.class.getClassLoader().getResource(FASTA_FILE_NAME))
                        .toURI()))) {

      VariantCaller variantCaller = new VariantCaller(samParser, fastaParser);
      variationList =
          variantCaller
              .callVariationsForIntervals(bedData)
              .filter(variation -> variantCaller.filterVariation(variation, minAlleleFrequency))
              .collect(Collectors.toList());
    }
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
