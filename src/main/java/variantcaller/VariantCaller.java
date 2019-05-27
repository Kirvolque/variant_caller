package variantcaller;

import sequence.FastaSequence;
import sequence.SamRecord;
import vcfwriter.variation.Variation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class VariantCaller {
  private static int prevSamIndex = 0;
  private static int prevFastaIndex = 0;
  private static int samIndex = 0;
  private static int fastaIndex = 0;

  private Map<Variation, Integer> alleleDepth = new HashMap<>();
  private Map<String, Map<Integer, Integer>> totalDepth = new HashMap<>();

  private static void incrementPositions(Map.Entry<Integer, Character> cigarPair) {
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

  public List<Variation> getAlleleFrequency(Double minAlleleFrequency) {
    List<Variation> filteredVariations = new ArrayList<>();
    for (Map.Entry alleleDepthPair : alleleDepth.entrySet()) {
      String chromName = ((Variation) alleleDepthPair.getKey()).getChrom();
      String chromPos = ((Variation) alleleDepthPair.getKey()).getAlt();
      Double af = ((Double) alleleDepthPair.getValue() / totalDepth.get(chromName).get(chromPos));
      if (!af.isNaN() && af >= minAlleleFrequency) {
        filteredVariations.add((Variation) alleleDepthPair.getValue());
      }
    }
    return filteredVariations;
  }

  private void incrementAlleleDepth(SamRecord samRecord, FastaSequence fastaSequence) {
    StringBuilder refBuilder = new StringBuilder();
    for (int i = prevFastaIndex; i < fastaIndex; i++) {
      refBuilder.append(fastaSequence.getNucleotide(samRecord.getRname(), i));
    }

    String ref = refBuilder.toString();
    String alt = samRecord.getSeq().substring(prevSamIndex, samIndex);

    if (!ref.equals(alt)) {
      alleleDepth.merge(
          new Variation(samRecord.getRname(), prevFastaIndex, ref, alt), 1, Integer::sum);
    }
  }

  private void incrementTotalDepth(SamRecord samRecord, Character cigarLetter) {
    if (cigarLetter.equals('M')) {
      for (int i = prevSamIndex; i < samIndex; i++) {
        totalDepth
            .computeIfAbsent(samRecord.getRname(), key -> new HashMap<>())
            .merge(i, 1, Integer::sum);
      }
    }
  }

  private void processSamRecord(SamRecord samRecord, FastaSequence fastaSequence) {
    samRecord
        .getCigarStream()
        .peek(VariantCaller::incrementPositions)
        .forEach(
            cigarPair -> {
              incrementAlleleDepth(samRecord, fastaSequence);
              incrementTotalDepth(samRecord, cigarPair.getValue());
            });
  }

  public void processSamRecords(FastaSequence fastaSequence, Stream<SamRecord> samRecordStream) {
    samRecordStream.forEach(
        samRecord -> {
          prevSamIndex = samRecord.getPos() - 1;
          prevFastaIndex = samRecord.getPos() - 1;
          samIndex = samRecord.getPos() - 1;
          fastaIndex = samRecord.getPos() - 1;
          processSamRecord(samRecord, fastaSequence);
        });
  }
}
