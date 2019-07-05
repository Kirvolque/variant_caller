package sequence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.Collectors;

class SamRecordTest {
  private static final String HEADER_RECORD = "@HD VN:1.6 SO:coordinate";
  private static final String SAM_RECORD_1 =
      "ref 516 ref 1 0 14M2D31M * 0 0 AGCATGTTAGATAAGATAGCTGTGCTAGTAGGCAGTCAGCGCCAT *";
  private static final String SAM_RECORD_2 = "r001 99 ref 7 30 14M1D3M = 39 41 TTAGATAAAGGATACTG *";
  private static final String SAM_RECORD_3 = "r001 147 ref 39 30 9M = 7 -41 CAGCGGCAT *";
  private static final String REFERENCE_NAME = "ref";
  private static final Integer POSITION_1 = 1;
  private static final Integer POSITION_2 = 7;
  private static final Integer POSITION_3 = 39;
  private static final Integer MAPQ_1 = 0;
  private static final Integer MAPQ_2 = 30;
  private static final Integer MAPQ_3 = 30;
  private static final String CIGAR_1 = "14M2D31M";
  private static final String CIGAR_2 = "14M1D3M";
  private static final String CIGAR_3 = "9M";
  private static final String SEQ_1 = "AGCATGTTAGATAAGATAGCTGTGCTAGTAGGCAGTCAGCGCCAT";
  private static final String SEQ_2 = "TTAGATAAAGGATACTG";
  private static final String SEQ_3 = "CAGCGGCAT";
  private static final String QUALITY_1 = "*";
  private static final String QUALITY_2 = "*";
  private static final String QUALITY_3 = "*";
  private static final Integer CIGAR_LENGTH_1 = 3;
  private static final Integer CIGAR_LENGTH_2 = 3;
  private static final Integer CIGAR_LENGTH_3 = 1;
  private static final Character CIGAR_LETTER_M = 'M';
  private static final Character CIGAR_LETTER_D = 'D';
  private static final Integer CIGAR_1_LETTER_1_NUMBER = 14;
  private static final Integer CIGAR_1_LETTER_2_NUMBER = 2;
  private static final Integer CIGAR_1_LETTER_3_NUMBER = 31;
  private static final Integer CIGAR_2_LETTER_1_NUMBER = 14;
  private static final Integer CIGAR_2_LETTER_2_NUMBER = 1;
  private static final Integer CIGAR_2_LETTER_3_NUMBER = 3;
  private static final Integer CIGAR_3_LETTER_1_NUMBER = 9;

  private static SamRecord headerRecord;
  private static SamRecord samRecord1;
  private static SamRecord samRecord2;
  private static SamRecord samRecord3;
  private static Map<Integer, Character> samRecordCigarMap1;
  private static Map<Integer, Character> samRecordCigarMap2;
  private static Map<Integer, Character> samRecordCigarMap3;

  @BeforeAll
  static void init() {
    headerRecord = SamRecord.init(HEADER_RECORD);
    samRecord1 = SamRecord.init(SAM_RECORD_1);
    samRecord2 = SamRecord.init(SAM_RECORD_2);
    samRecord3 = SamRecord.init(SAM_RECORD_3);
    samRecordCigarMap1 =
        samRecord1
            .getCigarStream()
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    samRecordCigarMap2 =
        samRecord2
            .getCigarStream()
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    samRecordCigarMap3 =
        samRecord3
            .getCigarStream()
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  @Test
  @DisplayName("Check header record")
  void checkHeaderRecord() {
    Assertions.assertTrue(headerRecord.isHeader());
  }

  @Test
  @DisplayName("Check correctness of reference sequence name")
  void checkReferenceSequenceNames() {
    Assertions.assertEquals(REFERENCE_NAME, samRecord1.getRname());
    Assertions.assertEquals(REFERENCE_NAME, samRecord2.getRname());
    Assertions.assertEquals(REFERENCE_NAME, samRecord3.getRname());
  }

  @Test
  @DisplayName("Check correctness of position")
  void checkPosition() {
    Assertions.assertEquals(POSITION_1, samRecord1.getPos());
    Assertions.assertEquals(POSITION_2, samRecord2.getPos());
    Assertions.assertEquals(POSITION_3, samRecord3.getPos());
  }

  @Test
  @DisplayName("Check correctness of mapping quality")
  void checkMappingQuality() {
    Assertions.assertEquals(MAPQ_1, samRecord1.getMapq());
    Assertions.assertEquals(MAPQ_2, samRecord2.getMapq());
    Assertions.assertEquals(MAPQ_3, samRecord3.getMapq());
  }

  @Test
  @DisplayName("Check correctness of cigar")
  void checkCigar() {
    Assertions.assertEquals(CIGAR_1, samRecord1.getCigar());
    Assertions.assertEquals(CIGAR_2, samRecord2.getCigar());
    Assertions.assertEquals(CIGAR_3, samRecord3.getCigar());
  }

  @Test
  @DisplayName("Check correctness of segment sequence")
  void checkSeq() {
    Assertions.assertEquals(SEQ_1, samRecord1.getSeq());
    Assertions.assertEquals(SEQ_2, samRecord2.getSeq());
    Assertions.assertEquals(SEQ_3, samRecord3.getSeq());
  }

  @Test
  @DisplayName("Check correctness of quality")
  void checkQuality() {
    Assertions.assertEquals(QUALITY_1, samRecord1.getQual());
    Assertions.assertEquals(QUALITY_2, samRecord2.getQual());
    Assertions.assertEquals(QUALITY_3, samRecord3.getQual());
  }

  @Test
  @DisplayName("Check correctness of cigar stream's length")
  void checkCigarStreamLength() {
    Assertions.assertEquals(CIGAR_LENGTH_1, samRecordCigarMap1.size());
    Assertions.assertEquals(CIGAR_LENGTH_2, samRecordCigarMap2.size());
    Assertions.assertEquals(CIGAR_LENGTH_3, samRecordCigarMap3.size());
  }

  @Test
  @DisplayName("Check correctness of cigar stream's letters")
  void checkCigarStreamLetters() {
    Assertions.assertTrue(samRecordCigarMap1.containsKey(CIGAR_1_LETTER_1_NUMBER));
    Assertions.assertTrue(samRecordCigarMap1.containsKey(CIGAR_1_LETTER_2_NUMBER));
    Assertions.assertTrue(samRecordCigarMap1.containsKey(CIGAR_1_LETTER_3_NUMBER));

    Assertions.assertEquals(CIGAR_LETTER_M, samRecordCigarMap1.get(CIGAR_1_LETTER_1_NUMBER));
    Assertions.assertEquals(CIGAR_LETTER_D, samRecordCigarMap1.get(CIGAR_1_LETTER_2_NUMBER));
    Assertions.assertEquals(CIGAR_LETTER_M, samRecordCigarMap1.get(CIGAR_1_LETTER_3_NUMBER));

    Assertions.assertTrue(samRecordCigarMap2.containsKey(CIGAR_2_LETTER_1_NUMBER));
    Assertions.assertTrue(samRecordCigarMap2.containsKey(CIGAR_2_LETTER_2_NUMBER));
    Assertions.assertTrue(samRecordCigarMap2.containsKey(CIGAR_2_LETTER_3_NUMBER));

    Assertions.assertEquals(CIGAR_LETTER_M, samRecordCigarMap2.get(CIGAR_2_LETTER_1_NUMBER));
    Assertions.assertEquals(CIGAR_LETTER_D, samRecordCigarMap2.get(CIGAR_2_LETTER_2_NUMBER));
    Assertions.assertEquals(CIGAR_LETTER_M, samRecordCigarMap2.get(CIGAR_2_LETTER_3_NUMBER));

    Assertions.assertTrue(samRecordCigarMap3.containsKey(CIGAR_3_LETTER_1_NUMBER));

    Assertions.assertEquals(CIGAR_LETTER_M, samRecordCigarMap3.get(CIGAR_3_LETTER_1_NUMBER));
  }
}
