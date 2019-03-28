package vcfwriter.variation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

  public Variation(String chrom, int pos, String id, String ref, String alt, String qual, String filter, String info) {
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

  public List<String> getFieldList() {
    List<String> fieldList = Arrays.asList(chrom, String.valueOf(pos), id, ref, alt, qual, filter, info);
    return fieldList;
  }

  public String toVcfLine() {
    return String.format("%s\t%d\t%s\t%s\t%s\t%s\t%s\t%s", chrom, pos, id, ref, alt, qual, filter, info);
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
