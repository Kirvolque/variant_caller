package fastaparser.fastasequenceiterator;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import sequence.FastaSequence;
import sequence.ListOfIntervals;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;

@RequiredArgsConstructor
public class FastaSequenceIterator implements Iterator<FastaSequence> {
  private static final String HEADER_START_REGEX = "[>;].*";
  @NonNull
  private BufferedReader reader;
  private FastaSequence currentFastaSequence;
  private String currentChromosomeName = "";
  private boolean firstLine = true;

  @Override
  public boolean hasNext() {
    try {
      return reader.ready();
    } catch (IOException e) {
      return Boolean.FALSE;
    }
  }

  public boolean hasNext(ListOfIntervals intervals) {
    String line;
    StringBuilder currentSequence = new StringBuilder();
    String chromosomeName = "";

    try {
      if (firstLine) {
        if ((line = reader.readLine()) != null) {
          chromosomeName = line.trim().substring(1);
          firstLine = false;
        }
      } else {
        chromosomeName = currentChromosomeName;
      }

      while ((line = reader.readLine()) != null) {
        if (!line.matches(HEADER_START_REGEX)) {
          currentSequence.append(line);
        } else {
          currentChromosomeName = line.trim().substring(1);
          break;
        }
      }
    } catch (IOException ex) {
      return false;
    }

    currentFastaSequence =
        FastaSequence.init(chromosomeName, currentSequence.toString(), intervals);
    return !currentFastaSequence.getChromosomeName().equals("");
  }

  @Override
  public FastaSequence next() {
    return currentFastaSequence;
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException("Remove not supported");
  }
}
