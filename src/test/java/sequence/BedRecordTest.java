package sequence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BedRecordTest {
  private static final String CHROMOSOME_NAME_1 = "chr1";
  private static final String CHROMOSOME_NAME_2 = "chr2";
  private static BedRecord bedRecord1;
  private static BedRecord bedRecord2;
  private static BedRecord bedRecord3;
  private static BedRecord bedRecord4;

  @BeforeAll
  static void init() {
    bedRecord1 = BedRecord.init("chr1 0 25");
    bedRecord2 = BedRecord.init("chr1 26 45");
    bedRecord3 = BedRecord.init("chr2 0 3");
    bedRecord4 = BedRecord.init("chr2 4 40");
  }

  @Test
  @DisplayName("Check correctness of names of sequences")
  void checkSequenceNames() {
    Assertions.assertEquals(CHROMOSOME_NAME_1, bedRecord1.getChrom());
    Assertions.assertEquals(CHROMOSOME_NAME_1, bedRecord2.getChrom());
    Assertions.assertEquals(CHROMOSOME_NAME_2, bedRecord3.getChrom());
    Assertions.assertEquals(CHROMOSOME_NAME_2, bedRecord4.getChrom());
  }

  @Test
  @DisplayName("Check correctness of intervals")
  void checkIntervals() {
    Assertions.assertEquals(new Interval(0, 25), bedRecord1.getInterval());
    Assertions.assertEquals(new Interval(26, 45), bedRecord2.getInterval());
    Assertions.assertEquals(new Interval(0, 3), bedRecord3.getInterval());
    Assertions.assertEquals(new Interval(4, 40), bedRecord4.getInterval());
  }
}
