package sequence;

import java.util.ArrayList;
import java.util.List;

public enum Nucleotide {
  A,
  C,
  G,
  T,
  UNDEFINED;

  public static Nucleotide fromCharacter(Character character) {
    try {
      return Nucleotide.valueOf(String.valueOf(character).toUpperCase());
    } catch (IllegalArgumentException | NullPointerException ex) {
    }
    return Nucleotide.UNDEFINED;
  }

  public static List<Nucleotide> fromString(String string) {
    List<Nucleotide> result = new ArrayList<>();
    for (Character c : string.trim().toUpperCase().toCharArray()) {
      result.add(Nucleotide.fromCharacter(c));
    }
    return result;
  }
}
