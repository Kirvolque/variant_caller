package fastaparser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sequence.FastaSequence;

import java.io.IOException;
import java.util.Objects;


class FastaParserTest {
  private static final String FILE_NAME = "ex.fa";
  private static final String CHROMOSOME_NAME_1 = "ref";
  private static final String CHROMOSOME_NAME_2 = "ref2";
  private static final String CHROMOSOME_SEQUENCE_1 = "AGCATGTTAGATAAGATAGCTGTGCTAGTAGGCAGTCAGCGCCATE";
  private static final String CHROMOSOME_SEQUENCE_2 = "aggttttataaaacaattaagtctacagagcaactacgcge";

  private static FastaSequence fastaSequence;

  @BeforeAll
  @DisplayName("Init fastaParser")
  public static void initFastaParser() {
    try {
      fastaSequence = FastaParser.parseFasta(Objects.requireNonNull(FastaParserTest.class.getClassLoader().getResource(FILE_NAME)).getPath());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  @DisplayName("Parse unexciting file")
  public void parseUnexcitingFile() {
    Assertions.assertThrows(IOException.class, () -> FastaParser.parseFasta("i_dont_exist.fa"));
  }

  @Test
  @DisplayName("Check correctness of the parsed names of sequences")
  public void checkParsedSequenceNames() {
    Assertions.assertTrue(fastaSequence.getFastaData().keySet().contains(CHROMOSOME_NAME_1));
    Assertions.assertTrue(fastaSequence.getFastaData().keySet().contains(CHROMOSOME_NAME_2));
  }

  @Test
  @DisplayName("Check correctness of the parsed sequences")
  public void checkParsedSequences() {
    Assertions.assertEquals(CHROMOSOME_SEQUENCE_1, fastaSequence.getFastaData().get(CHROMOSOME_NAME_1));
    Assertions.assertEquals(CHROMOSOME_SEQUENCE_2, fastaSequence.getFastaData().get(CHROMOSOME_NAME_2));
  }
}