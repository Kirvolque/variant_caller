package fastaparser;

import sequence.FastaSequence;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FastaParser {
  private static final Set<Character> separatorCharacters = new HashSet<Character>() {{
    add('>');
    add(';');
  }};

  public static FastaSequence parseFasta(String fileName) throws IOException {
    List<String> sequenceNames = new ArrayList<>();
    List<String> sequences = new ArrayList<>();

    BufferedReader reader = new BufferedReader(new InputStreamReader(
        new FileInputStream(fileName), StandardCharsets.UTF_8));

    StringBuilder currentSequence = new StringBuilder();

    String line;
    int currentChromosomeIndex = 0;

    while ((line = reader.readLine()) != null) {
      if (!separatorCharacters.contains(line.charAt(0))) {
        currentSequence.append(line);
      } else {
        sequenceNames.add(line.substring(1));
        if (currentChromosomeIndex != 0) {
          sequences.add(currentSequence.toString());
          currentSequence = new StringBuilder();
        } else {
          currentChromosomeIndex++;
        }
      }
    }
    sequences.add(currentSequence.toString());

    reader.close();
    return new FastaSequence(sequenceNames, sequences);
  }
}
