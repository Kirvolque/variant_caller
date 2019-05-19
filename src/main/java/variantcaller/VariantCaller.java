package variantcaller;

import sequence.FastaSequence;
import sequence.SamRecord;
import vcfwriter.variation.Variation;

import java.util.Map;
import java.util.stream.Stream;

public class VariantCaller {
  private static int samIndex = 0;
  private static int fastaIndex = 0;

  private static void cigarPairChecker(Map.Entry<Integer, Character> cigarPair) {
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

  public static Stream<Stream<Variation>> getVariationStream(
      FastaSequence fastaSequence, Stream<SamRecord> samRecordStream) {
    return samRecordStream.flatMap(
        samRecord ->
            Stream.of(
                samRecord
                    .getCigarStream()
                    .peek(VariantCaller::cigarPairChecker)
                    .flatMap(
                        o ->
                            Stream.of(
                                new Variation(
                                    samRecord.getRname(),
                                    fastaIndex,
                                    fastaSequence
                                        .getNucleotide(samRecord.getRname(), fastaIndex)
                                        .toString(),
                                    samRecord.getSeq().substring(samIndex, samIndex + 1))))));
  }
}
