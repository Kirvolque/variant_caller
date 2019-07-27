package sequence;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BedData {
  private List<ChromosomeListOfIntervalsTuple> chromosomeListOfIntervalsTuples;

  public static BedData init(Map<String, List<Interval>> data) {
    List<ChromosomeListOfIntervalsTuple> result =
        data.entrySet().stream()
            .map(
                item ->
                    new ChromosomeListOfIntervalsTuple(
                        item.getKey(), new ListOfIntervals(item.getValue())))
            .collect(Collectors.toList());

    return new BedData(result);
  }

  public Set<String> getChromosomes() {
    return chromosomeListOfIntervalsTuples.stream()
        .map(ChromosomeListOfIntervalsTuple::getChromosome)
        .collect(Collectors.toSet());
  }

  public ListOfIntervals getIntervals(String chromosomeName) {
    return chromosomeListOfIntervalsTuples.stream()
        .filter(item -> item.getChromosome().equals(chromosomeName))
        .findFirst()
        .get()
        .getListOfIntervals();
  }
}
