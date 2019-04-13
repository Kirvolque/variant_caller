package sequence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class RegionsSequenceTest {
  private static final Interval INTERVAL_1 = new Interval(0, 13);
  private static final Interval INTERVAL_2 = new Interval(14, 30);
  private static final Interval INTERVAL_3 = new Interval(31, 45);
  private static final String CHROMOSOME_SEQUENCE = "AGCATGTTAGATAAGATAGCTGTGCTAGTAGGCAGTCAGCGCCATE";
  private static final String CHROMOSOME_SEQUENCE_SUBSTRING_1 = "AGCATGTTAGATAA";
  private static final String CHROMOSOME_SEQUENCE_SUBSTRING_2 = "GATAGCTGTGCTAGTAG";
  private static final String CHROMOSOME_SEQUENCE_SUBSTRING_3 = "GCAGTCAGCGCCATE";
  private static final String TOO_SHORT_CHROMOSOME_SEQUENCE = "AGCATGTTAGATAAGATAGCTG";
  private static RegionsSequence regionsSequence;
  private static IntervalList intervalList;

  @BeforeAll
  @DisplayName("Init intervalList")
  static void init() {
    List<Interval> intervals = new ArrayList<>();
    intervals.add(INTERVAL_1);
    intervals.add(INTERVAL_2);
    intervals.add(INTERVAL_3);
    intervalList = new IntervalList(intervals);
  }

  @Test
  @DisplayName("Test for intervalList in createInstance() method")
  void testForIntervalList() throws Exception {
    regionsSequence = RegionsSequence.createInstance(intervalList, CHROMOSOME_SEQUENCE);
    Assertions.assertEquals(intervalList, regionsSequence.getIntervalList());
  }

  @Test
  @DisplayName("Test for nucleotidesIntervals in createInstance() method")
  void testForNucleotidesIntervals() throws Exception {
    regionsSequence = RegionsSequence.createInstance(intervalList, CHROMOSOME_SEQUENCE);

    Assertions.assertTrue(regionsSequence.getNucleotidesIntervals().containsKey(INTERVAL_1));
    Assertions.assertTrue(regionsSequence.getNucleotidesIntervals().containsKey(INTERVAL_2));
    Assertions.assertTrue(regionsSequence.getNucleotidesIntervals().containsKey(INTERVAL_3));

    Assertions.assertEquals(CHROMOSOME_SEQUENCE_SUBSTRING_1, regionsSequence.getNucleotidesIntervals().get(INTERVAL_1));
    Assertions.assertEquals(CHROMOSOME_SEQUENCE_SUBSTRING_2, regionsSequence.getNucleotidesIntervals().get(INTERVAL_2));
    Assertions.assertEquals(CHROMOSOME_SEQUENCE_SUBSTRING_3, regionsSequence.getNucleotidesIntervals().get(INTERVAL_3));
  }

  @Test
  void createInstanceWithInvalidParams() {
    Assertions.assertThrows(Exception.class, () -> RegionsSequence.createInstance(intervalList, TOO_SHORT_CHROMOSOME_SEQUENCE));
  }

  @Test
  void getNucleotideAt() {
    Assertions.assertEquals(Nucleotide.A, regionsSequence.getNucleotideAt(0).get());
    Assertions.assertEquals(Nucleotide.C, regionsSequence.getNucleotideAt(32).get());
    Assertions.assertEquals(Nucleotide.G, regionsSequence.getNucleotideAt(14).get());
    Assertions.assertEquals(Nucleotide.T, regionsSequence.getNucleotideAt(25).get());
    Assertions.assertEquals(Nucleotide.UNDEFINED, regionsSequence.getNucleotideAt(45).get());
  }
}
