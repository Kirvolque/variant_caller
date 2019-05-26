package sequence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static java.lang.Integer.MAX_VALUE;

class FastaSequenceTest {
  private static final String CHROMOSOME_NAME_1 = "ref";
  private static final String CHROMOSOME_NAME_2 = "ref2";
  private static final String CHROMOSOME_SEQUENCE_1 = "AGCATGTTAGATAAGATAGCTGTGCTAGTAGGCAGTCAGCGCCATE";
  private static final String CHROMOSOME_SEQUENCE_2 = "aggttttataaaacaattaagtctacagagcaactacgcge";
  private static final String CHROMOSOME_NAME_UNDEFINED = "ref_that_dont_exist";

  private static FastaSequence fastaSequence;

  @BeforeAll
  @DisplayName("Init fastaSequence")
  static void initFastaParser() {
    Map<String, String> fastaData = new HashMap<>();
    fastaData.put(CHROMOSOME_NAME_1, CHROMOSOME_SEQUENCE_1);
    fastaData.put(CHROMOSOME_NAME_2, CHROMOSOME_SEQUENCE_2);
    fastaSequence = new FastaSequence(fastaData);
  }

  @Test
  @DisplayName("Bad attempts to get nucleotide")
  void getNonexistentNucleotide() {
    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> fastaSequence.getNucleotide(CHROMOSOME_NAME_1, MAX_VALUE));
    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> fastaSequence.getNucleotide(CHROMOSOME_NAME_1, -1));
    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> fastaSequence.getNucleotide(CHROMOSOME_NAME_2, MAX_VALUE));
    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> fastaSequence.getNucleotide(CHROMOSOME_NAME_2, -1));
    Assertions.assertThrows(NoSuchElementException.class, () -> fastaSequence.getNucleotide(CHROMOSOME_NAME_UNDEFINED, 0));
  }

  @Test
  @DisplayName("Successful attempts to get nucleotide")
  void goodGetNucleotide() {
    Assertions.assertEquals(Nucleotide.A, fastaSequence.getNucleotide(CHROMOSOME_NAME_1, 0));
    Assertions.assertEquals(Nucleotide.C, fastaSequence.getNucleotide(CHROMOSOME_NAME_1, 2));
    Assertions.assertEquals(Nucleotide.G, fastaSequence.getNucleotide(CHROMOSOME_NAME_1, 5));
    Assertions.assertEquals(Nucleotide.T, fastaSequence.getNucleotide(CHROMOSOME_NAME_1, 20));
    Assertions.assertEquals(Nucleotide.UNDEFINED, fastaSequence.getNucleotide(CHROMOSOME_NAME_1, 45));
    Assertions.assertEquals(Nucleotide.A, fastaSequence.getNucleotide(CHROMOSOME_NAME_2, 0));
    Assertions.assertEquals(Nucleotide.T, fastaSequence.getNucleotide(CHROMOSOME_NAME_2, 5));
    Assertions.assertEquals(Nucleotide.G, fastaSequence.getNucleotide(CHROMOSOME_NAME_2, 20));
    Assertions.assertEquals(Nucleotide.C, fastaSequence.getNucleotide(CHROMOSOME_NAME_2, 38));
    Assertions.assertEquals(Nucleotide.UNDEFINED, fastaSequence.getNucleotide(CHROMOSOME_NAME_2, 40));
  }
}
