package variantcaller;

import bedparser.BedParser;
import fastaparser.FastaParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samparser.SamParser;
import sequence.ListOfIntervals;
import vcfwriter.variation.Variation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class VariantCallerTest {
  private static final Double minAlleleFrequency = 0.0;
  private static final String CHROMOSOME_NAME_1 = "chr1";
  private static final String CHROMOSOME_NAME_2 = "chr2";
  private static final String FASTA_FILE_NAME = "ex.fa";
  private static final String BED_FILE_NAME = "ex.bed";
  private static VariantCaller variantCaller = new VariantCaller();
  private static List<Variation> variationList;

  @BeforeAll
  static void init() throws URISyntaxException, IOException {
    Map<String, ListOfIntervals> bedData =
        BedParser.collectIntervals(
            Paths.get(
                Objects.requireNonNull(
                    VariantCallerTest.class.getClassLoader().getResource(BED_FILE_NAME))
                    .toURI()));
    FastaParser fastaParser =
        FastaParser.parseFasta(
            Paths.get(
                Objects.requireNonNull(
                    VariantCallerTest.class.getClassLoader().getResource(FASTA_FILE_NAME))
                    .toURI()));
    SamParser samParser =
        SamParser.parseSam(
            Paths.get(
                Objects.requireNonNull(
                    VariantCallerTest.class.getClassLoader().getResource("ex.sam"))
                    .toURI()));

    bedData.forEach(
        (chromosomeName, listOfIntervals) -> {
          try {
            variantCaller.processIntervals(
                fastaParser.getNext(listOfIntervals),
                samParser.getReadsForRegion(chromosomeName, listOfIntervals),
                listOfIntervals);
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
    variationList = variantCaller.filterVariations(minAlleleFrequency);
  }

  @Test
  @DisplayName("Check variations with matches only")
  void checkVariationsWithOnlyMatches() {
    Assertions.assertEquals(
        new Variation(CHROMOSOME_NAME_1, 9, "GATAAGATAG", "ATAAGATAGC"), variationList.get(1));
    Assertions.assertEquals(
        new Variation(CHROMOSOME_NAME_1, 9, "TCAGCGCCAT", "ATAAGATAGC"), variationList.get(0));
  }
}
