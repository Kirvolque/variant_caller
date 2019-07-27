package sequence;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChromosomeListOfIntervalsTuple {
  private final String chromosome;
  private final ListOfIntervals listOfIntervals;
}
