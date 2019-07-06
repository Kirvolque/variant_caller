package fastaparser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sequence.FastaSequence;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Objects;

class FastaParserTest {
  private static final String FASTA_FILE_NAME = "ex.fa";
  private static final String BED_FILE_NAME = "ex.bed";
  private static final String CHROMOSOME_NAME_1 = "chr1";
  private static final String CHROMOSOME_NAME_2 = "chr2";

  private static FastaSequence fastaSequence;

  @BeforeAll
  @DisplayName("Init fastaParser")
  static void initFastaParser() throws IOException, URISyntaxException {
    fastaSequence =
        FastaParser.parseFasta(
            Objects.requireNonNull(
                FastaParserTest.class.getClassLoader().getResource(FASTA_FILE_NAME))
                .getPath(),
            Paths.get(
                Objects.requireNonNull(
                    FastaParserTest.class.getClassLoader().getResource(BED_FILE_NAME))
                    .toURI()));
  }

  @Test
  @DisplayName("Parse unexciting fasta file")
  void parseUnexcitingFastaFile() {
    Assertions.assertThrows(
        IOException.class,
        () ->
            FastaParser.parseFasta(
                "i_dont_exist.fa",
                Paths.get(
                    Objects.requireNonNull(
                        FastaParserTest.class.getClassLoader().getResource(BED_FILE_NAME))
                        .toURI())));
  }

  @Test
  @DisplayName("Check correctness of the parsed names of sequences")
  void checkParsedSequenceNames() {
    Assertions.assertTrue(fastaSequence.getChromosomes().contains(CHROMOSOME_NAME_1));
    Assertions.assertTrue(fastaSequence.getChromosomes().contains(CHROMOSOME_NAME_2));
  }
}
