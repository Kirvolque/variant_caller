package fastaparser;

import sequence.FastaSequence;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class FastaParser {
  private static final char HEADER_START_SYMBOL_1 = '>';
  private static final char HEADER_START_SYMBOL_2 = ';';

  public static FastaSequence parseFasta(String fileName) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(
        new FileInputStream(fileName), StandardCharsets.UTF_8));

    StringBuilder currentSequence = new StringBuilder();

    String line;

    Map<String, String> fastaData = new HashMap<>();

    String currentSequenceName = "";

    if ((line = reader.readLine()) != null) {
      currentSequenceName = line.substring(1);
    }

    while ((line = reader.readLine()) != null) {
      if (!(line.charAt(0) == HEADER_START_SYMBOL_1 || line.charAt(0) == HEADER_START_SYMBOL_2)) {
        currentSequence.append(line);
      } else {
        fastaData.put(currentSequenceName, currentSequence.toString());
        currentSequenceName = line.substring(1);

        currentSequence = new StringBuilder();
      }
    }
    fastaData.put(currentSequenceName, currentSequence.toString());

    reader.close();

    return new FastaSequence(fastaData);
  }
}
