package sequence;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BedData {
  private Map<String, ListOfIntervals> data;

  public static BedData init(Map<String, List<Interval>> data) {
    Map<String, ListOfIntervals> result =
        data.entrySet().stream()
            .collect(
                Collectors.toMap(
                    Map.Entry::getKey,
                    item -> new ListOfIntervals(item.getValue()),
                    (e1, e2) -> e1,
                    LinkedHashMap::new));
    return new BedData(result);
  }

  public Set<String> getChromosomes() {
    return data.keySet();
  }

  public ListOfIntervals getIntervals(String chromosomeName) {
    return data.get(chromosomeName);
  }
}
