package vcfwriter.variation;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Variation {

  private final String chrom;
  private final int pos;
  private final String id;
  private final String ref;
  private final String alt;
  private final String qual;
  private final String filter;
  private final String info;

  private int currentField;

  public Variation(String chrom, int pos, String ref, String alt) {
    this(chrom, pos, ".", ref, alt, ".", ".", ".");
  }

  public Variation(
      String chrom,
      int pos,
      String id,
      String ref,
      String alt,
      String qual,
      String filter,
      String info) {
    this.chrom = chrom;
    this.pos = pos;
    this.id = id;
    this.ref = ref;
    this.alt = alt;
    this.qual = qual;
    this.filter = filter;
    this.info = info;
    this.currentField = 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final Variation variation = (Variation) o;
    return pos == variation.pos
        && chrom.equals(variation.chrom)
        && ref.equals(variation.ref)
        && alt.equals(variation.alt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(chrom, pos, ref, alt);
  }

  public List<String> getFieldList() {
    return Arrays.asList(chrom, String.valueOf(pos), id, ref, alt, qual, filter, info);
  }

  public String toVcfLine() {
    return String.format(
        "%s\t%d\t%s\t%s\t%s\t%s\t%s\t%s", chrom, pos, id, ref, alt, qual, filter, info);
  }

  public String getChrom() {
    return chrom;
  }

  public int getPos() {
    return pos;
  }

  public String getId() {
    return id;
  }

  public String getRef() {
    return ref;
  }

  public String getAlt() {
    return alt;
  }

  public String getQual() {
    return qual;
  }

  public String getFilter() {
    return filter;
  }

  public String getInfo() {
    return info;
  }
}
