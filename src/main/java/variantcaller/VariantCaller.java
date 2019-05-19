package variantcaller;

import sequence.FastaSequence;
import sequence.SamRecord;
import vcfwriter.variation.Variation;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class VariantCaller {
  private static int samIndex = 0;
  private static int fastaIndex = 0;

  private Map<Variation, Integer> alleleDepth = new HashMap<>();

  private static void incrementPositions(Map.Entry<Integer, Character> cigarPair) {
    switch (cigarPair.getValue()) {
      case 'D':
        fastaIndex += cigarPair.getKey();
        break;
      case 'M':
        samIndex += cigarPair.getKey();
        fastaIndex += cigarPair.getKey();
        break;
      case 'I':
        samIndex += cigarPair.getKey();
        break;
      default:
        break;
    }
  }

  private void incrementAlleleDepth(SamRecord samRecord, FastaSequence fastaSequence) {
    alleleDepth.merge(
        new Variation(
            samRecord.getRname(),
            fastaIndex,
            fastaSequence.getNucleotide(samRecord.getRname(), fastaIndex).toString(),
            samRecord.getSeq().substring(samIndex, samIndex + 1)),
        1,
        Integer::sum);
  }

  private void processSamRecord(SamRecord samRecord, FastaSequence fastaSequence) {
    samRecord
        .getCigarStream()
        .peek(VariantCaller::incrementPositions)
        .forEach(cigarPair -> incrementAlleleDepth(samRecord, fastaSequence));
  }

  public void processSamRecords(FastaSequence fastaSequence, Stream<SamRecord> samRecordStream) {
    samRecordStream.forEach(
        samRecord -> {
          samIndex = 0;
          fastaIndex = 0;
          processSamRecord(samRecord, fastaSequence);
        });
  }
}
