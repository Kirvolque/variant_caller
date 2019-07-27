package vcfwriter.variation;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode
@AllArgsConstructor
public class Variation {

  @Getter
  private final String chrom;
  @Getter
  private final int pos;
  @Getter
  private final String id;
  @Getter
  private final String ref;
  @Getter
  private final String alt;
  @Getter
  private final String qual;
  @Getter
  private final String filter;
  @Getter
  private final String info;

  private int currentField;

  public Variation(String chrom, int pos, String ref, String alt) {
    this(chrom, pos, ".", ref, alt, ".", ".", ".", 0);
  }

  public List<String> getFieldList() {
    return Arrays.asList(chrom, String.valueOf(pos), id, ref, alt, qual, filter, info);
  }

  public String toVcfLine() {
    return String.format(
        "%s\t%d\t%s\t%s\t%s\t%s\t%s\t%s", chrom, pos, id, ref, alt, qual, filter, info);
  }
}
