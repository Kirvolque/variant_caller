package sequence;

public enum Nucleotide {
  A, C, G, T, UNDEFINED;

  public static Nucleotide fromString(String string) {
    try {
      return Nucleotide.valueOf(string.trim().toUpperCase());
    } catch (IllegalArgumentException | NullPointerException ex) {
    }
    return Nucleotide.UNDEFINED;
  }
}
