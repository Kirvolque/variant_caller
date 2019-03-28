package sequence;

import fastaparser.FastaParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;

import static java.lang.Integer.MAX_VALUE;

public class FastaSequenceTest {
  private static final String FILE_NAME = "ex.fa";
  private static final String CHROMOSOME_NAME_1 = "ref";
  private static final String CHROMOSOME_NAME_2 = "ref2";
  private static final String CHROMOSOME_NAME_UNDEFINED = "ref_that_dont_exist";

  private static FastaSequence fastaSequence;

  @BeforeAll
  @DisplayName("Init fastaParser")
  public static void initFastaParser() {
    try {
      fastaSequence = FastaParser.parseFasta(Objects.requireNonNull(FastaSequenceTest.class.getClassLoader().getResource(FILE_NAME)).getPath());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  @DisplayName("Bad attempts to get nucleotide")
  public void getNonexistentNucleotide() {
    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> fastaSequence.getNucleotide(CHROMOSOME_NAME_1, MAX_VALUE));
    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> fastaSequence.getNucleotide(CHROMOSOME_NAME_1, -1));
    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> fastaSequence.getNucleotide(CHROMOSOME_NAME_2, MAX_VALUE));
    Assertions.assertThrows(IndexOutOfBoundsException.class, () -> fastaSequence.getNucleotide(CHROMOSOME_NAME_2, -1));
    Assertions.assertThrows(NullPointerException.class, () -> fastaSequence.getNucleotide(CHROMOSOME_NAME_UNDEFINED, 0));
  }

  @Test
  @DisplayName("Successful attempts to get nucleotide")
  public void goodGetNucleotide() {
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