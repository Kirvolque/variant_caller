package sequence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

class RegionSequenceTest {
  private static final Interval INTERVAL_1 = new Interval(0, 13);
  private static final Interval INTERVAL_2 = new Interval(14, 30);
  private static final Interval INTERVAL_3 = new Interval(31, 45);
  private static final String CHROMOSOME_SEQUENCE =
      "AGCATGTTAGATAAGATAGCTGTGCTAGTAGGCAGTCAGCGCCATE";
  private static final String CHROMOSOME_SEQUENCE_SUBSTRING_1 = "AGCATGTTAGATAA";
  private static final String CHROMOSOME_SEQUENCE_SUBSTRING_2 = "GATAGCTGTGCTAGTAG";
  private static final String CHROMOSOME_SEQUENCE_SUBSTRING_3 = "GCAGTCAGCGCCATE";
  private static final String TOO_SHORT_CHROMOSOME_SEQUENCE = "AGCATGTTAGATAAGATAGCTG";
  private static RegionSequence regionSequence;
  private static ListOfIntervals listOfIntervals;
  private static Region REGION_1;
  private static Region REGION_2;
  private static Region REGION_3;

  @BeforeAll
  @DisplayName("Init listOfIntervals")
  static void init() {
    List<Interval> intervals = new ArrayList<>();
    intervals.add(INTERVAL_1);
    intervals.add(INTERVAL_2);
    intervals.add(INTERVAL_3);
    listOfIntervals = new ListOfIntervals(intervals);
    regionSequence = RegionSequence.createInstance(listOfIntervals, CHROMOSOME_SEQUENCE);
    List<Nucleotide> NUCLEOTIDES_INTERVAL_1 =
        CHROMOSOME_SEQUENCE_SUBSTRING_1
            .codePoints()
            .mapToObj(c -> Nucleotide.fromCharacter((char) c))
            .collect(Collectors.toList());
    List<Nucleotide> NUCLEOTIDES_INTERVAL_2 =
        CHROMOSOME_SEQUENCE_SUBSTRING_2
            .codePoints()
            .mapToObj(c -> Nucleotide.fromCharacter((char) c))
            .collect(Collectors.toList());
    List<Nucleotide> NUCLEOTIDES_INTERVAL_3 =
        CHROMOSOME_SEQUENCE_SUBSTRING_3
            .codePoints()
            .mapToObj(c -> Nucleotide.fromCharacter((char) c))
            .collect(Collectors.toList());

    REGION_1 = new Region(NUCLEOTIDES_INTERVAL_1, INTERVAL_1);
    REGION_2 = new Region(NUCLEOTIDES_INTERVAL_2, INTERVAL_2);
    REGION_3 = new Region(NUCLEOTIDES_INTERVAL_3, INTERVAL_3);
  }

  @Test
  @DisplayName("Test for listOfIntervals in createInstance() method")
  void testForIntervalList() {
    Assertions.assertEquals(listOfIntervals, regionSequence.getIntervalList());
  }

  @Test
  @DisplayName("Test for nucleotidesIntervals in createInstance() method")
  void testForNucleotidesIntervals() {
    regionSequence = RegionSequence.createInstance(listOfIntervals, CHROMOSOME_SEQUENCE);
    List<Region> regionListExpected = new ArrayList<>();
    regionListExpected.add(REGION_1);
    regionListExpected.add(REGION_2);
    regionListExpected.add(REGION_3);

    Assertions.assertTrue(regionSequence.getIntervalList().intervalIsPresent(INTERVAL_1));
    Assertions.assertTrue(regionSequence.getIntervalList().intervalIsPresent(INTERVAL_2));
    Assertions.assertTrue(regionSequence.getIntervalList().intervalIsPresent(INTERVAL_3));

    Assertions.assertEquals(regionListExpected, regionSequence.getRegionList());
  }

  @Test
  void createInstanceWithInvalidParams() {
    Assertions.assertThrows(
        Exception.class,
        () -> RegionSequence.createInstance(listOfIntervals, TOO_SHORT_CHROMOSOME_SEQUENCE));
  }

  @Test
  @DisplayName("Test for getNucleotideAt() method for position")
  void getNucleotideAtPosition() {
    Assertions.assertEquals(Nucleotide.A, regionSequence.getNucleotideAt(0));
    Assertions.assertEquals(Nucleotide.C, regionSequence.getNucleotideAt(32));
    Assertions.assertEquals(Nucleotide.G, regionSequence.getNucleotideAt(14));
    Assertions.assertEquals(Nucleotide.T, regionSequence.getNucleotideAt(25));
    Assertions.assertEquals(Nucleotide.UNDEFINED, regionSequence.getNucleotideAt(45));
  }

  @Test
  @DisplayName("Test for getNucleotideAt() method for interval")
  void getNucleotideAtInterval() {
    Assertions.assertEquals(Nucleotide.A, regionSequence.getNucleotideAt(INTERVAL_1, 0));
    Assertions.assertEquals(Nucleotide.G, regionSequence.getNucleotideAt(INTERVAL_2, 0));
    Assertions.assertEquals(Nucleotide.C, regionSequence.getNucleotideAt(INTERVAL_3, 1));
  }

  @Test
  @DisplayName("Test for getNucleotideList() method for interval")
  void getRegion() {
    Assertions.assertEquals(REGION_1, regionSequence.getRegion(INTERVAL_1));
    Assertions.assertEquals(REGION_2, regionSequence.getRegion(INTERVAL_2));
    Assertions.assertEquals(REGION_3, regionSequence.getRegion(INTERVAL_3));
  }

  @Test
  void getNonexistentNucleotideAt() {
    Assertions.assertThrows(NoSuchElementException.class, () -> regionSequence.getNucleotideAt(-1));
    Assertions.assertThrows(NoSuchElementException.class, () -> regionSequence.getNucleotideAt(100));
    Assertions.assertThrows(NoSuchElementException.class, () -> regionSequence.getRegion(new Interval(-1, 0)));
  }
}
