package fastaparser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FastaParser {
  private ArrayList<String> sequenceList = new ArrayList<>();

  public FastaParser(String fileName) throws IOException {
    Set<Character> separatorCharacters = new HashSet<>();
    separatorCharacters.add('>');
    separatorCharacters.add(';');

    BufferedReader  reader = new BufferedReader(new InputStreamReader(
        new FileInputStream(fileName), StandardCharsets.UTF_8));

    StringBuilder currentSequence = new StringBuilder();

    String line;
    int currentChromosomeIndex = 0;

    while ((line = reader.readLine()) != null) {
      if (!separatorCharacters.contains(line.charAt(0))) {
        currentSequence.append(line);
      } else {
        if (currentChromosomeIndex != 0) {
          sequenceList.add(currentSequence.toString());
          currentSequence = new StringBuilder();
        } else {
          currentChromosomeIndex++;
        }
      }
    }
    sequenceList.add(currentSequence.toString());

    reader.close();
  }

  public Nucleotide getNucleotide(int chromosomeIndex, int nucleotideIndex) throws IndexOutOfBoundsException{
    return getEnumFromString(Nucleotide.class, Character.toString(sequenceList.get(chromosomeIndex).charAt(nucleotideIndex)));
  }

  private static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) {
    if (c != null && string != null) {
      try {
        return Enum.valueOf(c, string.trim().toUpperCase());
      } catch (IllegalArgumentException ex) {
      }
    }
    return null;
  }
}