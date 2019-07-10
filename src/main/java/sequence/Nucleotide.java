package sequence;

import java.util.List;
import java.util.stream.Collectors;

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
    return string
        .trim()
        .toUpperCase()
        .codePoints()
        .mapToObj(c -> fromCharacter((char) c))
        .collect(Collectors.toList());
  }
}
