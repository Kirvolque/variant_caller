package sequence;

public class BedRecord {
  private static final String DOUBLE_SPACE = "  ";
  private static final String SINGLE_SPACE = " ";

  private String chrom;
  private Interval interval;

  private BedRecord(String chrom, Interval interval) {
    this.chrom = chrom;
    this.interval = interval;
  }

  public static BedRecord init(String line) {
    // replace 2 spaces with 1 to avoid wrong line splitting
    line = line.replace(DOUBLE_SPACE, SINGLE_SPACE);
    String[] subStr = line.split(SINGLE_SPACE);
    if (subStr.length > 2 || !subStr[0].equals("track")) {
      return new BedRecord(subStr[0], new Interval(Integer.valueOf(subStr[1]), Integer.valueOf(subStr[2])));
    } else {
      throw new IllegalArgumentException("The string does not satisfy the BED format");
    }
  }

  public String getChrom() {
    return chrom;
  }

  public Interval getInterval() {
    return interval;
  }
}
