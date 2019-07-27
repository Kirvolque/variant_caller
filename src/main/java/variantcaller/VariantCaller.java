package variantcaller;

import fastaparser.FastaParser;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import samparser.SamParser;
import sequence.BedData;
import sequence.FastaSequence;
import sequence.Interval;
import sequence.ListOfIntervals;
import sequence.SamRecord;
import vcfwriter.variation.Variation;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class VariantCaller {
  @NonNull private final SamParser samParser;
  @NonNull private final FastaParser fastaParser;
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

  private Double countAF(Variation variation) {
    String chromName = variation.getChrom();
    int chromPos = variation.getPos();
    Double ad = alleleDepth.get(variation).doubleValue();
    Double td = Double.valueOf(totalDepth.get(chromName).get(chromPos));
    return ad / td;
  }

  public boolean filterVariation(Variation variation, Double minAlleleFrequency) {
    return countAF(variation) >= minAlleleFrequency;
  }

  private void incrementAlleleDepth(
      SamRecord samRecord, FastaSequence fastaSequence, Interval interval) {
    StringBuilder refBuilder = new StringBuilder();
    for (int i = prevFastaIndex - interval.getBegin(); i < fastaIndex - interval.getBegin(); i++) {
      refBuilder.append(fastaSequence.getNucleotide(interval, i));
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
      FastaSequence fastaSequence, Stream<SamRecord> samRecordStream, Interval interval) {
    samRecordStream
        .filter(samRecord -> samRecord.fitInterval(interval))
        .forEach(
            samRecord -> {
              prevSamIndex = samRecord.getPos();
              prevFastaIndex = samRecord.getPos() - 1;
              samIndex = samRecord.getPos();
              fastaIndex = samRecord.getPos() - 1;
              processSamRecord(samRecord, fastaSequence, interval);
            });
  }

  private void processIntervals(String chromosomeName, ListOfIntervals listOfIntervals) {
    listOfIntervals
        .asList()
        .forEach(
            interval ->
                processSamRecords(
                    fastaParser.getRegionsForChromosome(chromosomeName, listOfIntervals),
                    samParser.getReadsForRegion(chromosomeName, interval),
                    interval));
  }

  public Stream<Variation> callVariationsForIntervals(BedData bedData) {
    bedData.getData().forEach(this::processIntervals);
    return alleleDepth.keySet().stream();
  }
}
