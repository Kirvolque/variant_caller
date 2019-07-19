package variantcaller;

import sequence.FastaSequence;
import sequence.Interval;
import sequence.ListOfIntervals;
import sequence.SamRecord;
import vcfwriter.variation.Variation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class VariantCaller {
  private int prevSamIndex = 0;
  private int prevFastaIndex = 0;
  private int samIndex = 0;
  private int fastaIndex = 0;

  private Map<Variation, Integer> alleleDepth = new HashMap<>();
  private Map<String, Map<Integer, Integer>> totalDepth = new HashMap<>();

  private void incrementPositions(Map.Entry<Integer, Character> cigarPair) {
    switch (cigarPair.getValue()) {
      case 'D':
        prevFastaIndex = fastaIndex;
        fastaIndex += cigarPair.getKey();
        break;
      case 'M':
        prevFastaIndex = fastaIndex;
        prevSamIndex = samIndex;
        samIndex += cigarPair.getKey();
        fastaIndex += cigarPair.getKey();
        break;
      case 'I':
        prevSamIndex = samIndex;
        samIndex += cigarPair.getKey();
        break;
      default:
        break;
    }
  }

  private Double countAF(Map.Entry<Variation, Integer> alleleDepthPair) {
    String chromName = alleleDepthPair.getKey().getChrom();
    int chromPos = alleleDepthPair.getKey().getPos();
    Double ad = alleleDepthPair.getValue().doubleValue();
    Double td = Double.valueOf(totalDepth.get(chromName).get(chromPos));
    return ad / td;
  }

  public List<Variation> filterVariations(Double minAlleleFrequency) {
    return alleleDepth.entrySet().stream()
        .filter(x -> countAF(x) >= minAlleleFrequency)
        .map(Map.Entry::getKey)
        .collect(Collectors.toList());
  }

  private void incrementAlleleDepth(
      SamRecord samRecord, FastaSequence fastaSequence, Interval interval) {
    StringBuilder refBuilder = new StringBuilder();
    try {
      for (int i = prevFastaIndex; i < fastaIndex; i++) {
        refBuilder.append(fastaSequence.getNucleotide(interval, i));
      }
    } catch (NoSuchElementException ex) {

    }

    String ref = refBuilder.toString();
    String alt =
        samRecord
            .getSeq()
            .substring(prevSamIndex - samRecord.getPos(), samIndex - samRecord.getPos());

    if (!ref.equals(alt)) {
      alleleDepth.merge(
          new Variation(samRecord.getRname(), prevFastaIndex, ref, alt), 1, Integer::sum);
    }
  }

  private void incrementTotalDepth(SamRecord samRecord, Character cigarLetter) {
    if (cigarLetter.equals('M')) {
      for (int i = prevFastaIndex; i < fastaIndex; i++) {
        totalDepth
            .computeIfAbsent(samRecord.getRname(), key -> new HashMap<>())
            .merge(i, 1, Integer::sum);
      }
    }
  }

  private void processSamRecord(
      SamRecord samRecord, FastaSequence fastaSequence, Interval interval) {
    samRecord
        .getCigarStream()
        .peek(this::incrementPositions)
        .forEach(
            cigarPair -> {
              incrementAlleleDepth(samRecord, fastaSequence, interval);
              incrementTotalDepth(samRecord, cigarPair.getValue());
            });
  }

  private void processSamRecords(
      FastaSequence fastaSequence, List<SamRecord> samRecordList, Interval interval) {
    samRecordList.forEach(
        samRecord -> {
          prevSamIndex = samRecord.getPos();
          prevFastaIndex = samRecord.getPos() - 1;
          samIndex = samRecord.getPos();
          fastaIndex = samRecord.getPos() - 1;
          processSamRecord(samRecord, fastaSequence, interval);
        });
  }

  public void processIntervals(
      FastaSequence fastaSequence,
      List<SamRecord> samRecordStream,
      ListOfIntervals listOfIntervals) {
    listOfIntervals
        .asList()
        .forEach(interval -> processSamRecords(fastaSequence, samRecordStream, interval));
  }
}
