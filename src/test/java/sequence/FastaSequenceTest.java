package sequence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

import static java.lang.Integer.MAX_VALUE;

class FastaSequenceTest {
  private static final String CHROMOSOME_NAME_1 = "chr1";
  private static final String CHROMOSOME_NAME_2 = "chr2";
  private static final String BED_FILE_NAME = "ex.bed";
  private static final String CHROMOSOME_SEQUENCE_1 =
      "AGCATGTTAGATAAGATAGCTGTGCTAGTAGGCAGTCAGCGCCATE";
  private static final String CHROMOSOME_SEQUENCE_2 = "aggttttataaaacaattaagtctacagagcaactacgcge";
  private static final String CHROMOSOME_NAME_UNDEFINED = "ref_that_dont_exist";

  private static FastaSequence fastaSequence;

  @BeforeAll
  @DisplayName("Init fastaSequence")
  static void initFastaParser() throws IOException, URISyntaxException {
    Map<String, String> fastaData = new HashMap<>();
    fastaData.put(CHROMOSOME_NAME_1, CHROMOSOME_SEQUENCE_1);
    fastaData.put(CHROMOSOME_NAME_2, CHROMOSOME_SEQUENCE_2);
    fastaSequence =
        FastaSequence.init(
            fastaData,
            Paths.get(
                Objects.requireNonNull(
                    FastaSequenceTest.class.getClassLoader().getResource(BED_FILE_NAME))
                    .toURI()));
  }

  @Test
  @DisplayName("Bad attempts to get nucleotide")
  void getNonexistentNucleotide() {
    Assertions.assertThrows(
        RuntimeException.class,
        () -> fastaSequence.getNucleotide(CHROMOSOME_NAME_1, MAX_VALUE));
    Assertions.assertThrows(
        RuntimeException.class, () -> fastaSequence.getNucleotide(CHROMOSOME_NAME_1, -1));
    Assertions.assertThrows(
        RuntimeException.class,
        () -> fastaSequence.getNucleotide(CHROMOSOME_NAME_2, MAX_VALUE));
    Assertions.assertThrows(
        RuntimeException.class, () -> fastaSequence.getNucleotide(CHROMOSOME_NAME_2, -1));
    Assertions.assertThrows(
        NoSuchElementException.class,
        () -> fastaSequence.getNucleotide(CHROMOSOME_NAME_UNDEFINED, 0));
  }

  @Test
  @DisplayName("Successful attempts to get nucleotide")
  void goodGetNucleotide() {
    Assertions.assertEquals(Nucleotide.A, fastaSequence.getNucleotide(CHROMOSOME_NAME_1, 0));
    Assertions.assertEquals(Nucleotide.C, fastaSequence.getNucleotide(CHROMOSOME_NAME_1, 2));
    Assertions.assertEquals(Nucleotide.G, fastaSequence.getNucleotide(CHROMOSOME_NAME_1, 5));
    Assertions.assertEquals(Nucleotide.T, fastaSequence.getNucleotide(CHROMOSOME_NAME_1, 20));
    Assertions.assertEquals(
        Nucleotide.UNDEFINED, fastaSequence.getNucleotide(CHROMOSOME_NAME_1, 45));
    Assertions.assertEquals(Nucleotide.A, fastaSequence.getNucleotide(CHROMOSOME_NAME_2, 0));
    Assertions.assertEquals(Nucleotide.T, fastaSequence.getNucleotide(CHROMOSOME_NAME_2, 5));
    Assertions.assertEquals(Nucleotide.G, fastaSequence.getNucleotide(CHROMOSOME_NAME_2, 20));
    Assertions.assertEquals(Nucleotide.C, fastaSequence.getNucleotide(CHROMOSOME_NAME_2, 38));
    Assertions.assertEquals(
        Nucleotide.UNDEFINED, fastaSequence.getNucleotide(CHROMOSOME_NAME_2, 40));
  }
}
