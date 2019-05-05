package sequence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SamRecord {
  private static final String DOUBLE_SPACE = "  ";
  private static final String SINGLE_SPACE = " ";
  private static final List<String> HEADER_TAGS =
      new ArrayList<>(Arrays.asList("@HD", "@SQ", "@RG", "@PG", "@CO"));

  private boolean isHeader;
  private String rname; // Reference sequence NAME
  private int pos; // 	1- based leftmost mapping POSition
  private int mapq; // MAPping Quality
  private String cigar; // CIGAR String
  private String seq; // segment SEQuence
  private String qual; // ASCII of Phred-scaled base QUALity+33

  private SamRecord(
      String rname, int pos, int mapq, String cigar, String seq, String qual, boolean isHeader) {
    this.rname = rname;
    this.pos = pos;
    this.mapq = mapq;
    this.cigar = cigar;
    this.seq = seq;
    this.qual = qual;
    this.isHeader = isHeader;
  }

  private static SamRecord initFromStringArray(String[] recordData, boolean header) {
    if (!header) {
      return new SamRecord(
          recordData[2],
          Integer.parseInt(recordData[3]),
          Integer.parseInt(recordData[4]),
          recordData[5],
          recordData[9],
          recordData[10],
          header);
    }
    return new SamRecord("", -1, -1, "", "", "", header);
  }

  public static SamRecord init(String recordData) {
    // replace 2 spaces with 1 to avoid wrong line splitting
    recordData = recordData.replace(DOUBLE_SPACE, SINGLE_SPACE);
    String[] subStr = recordData.split(" ");

    return SamRecord.initFromStringArray(subStr, HEADER_TAGS.contains(subStr[0]));
  }

  public boolean isHeader() {
    return isHeader;
  }

  public String getRname() {
    return rname;
  }

  public int getPos() {
    return pos;
  }

  public int getMapq() {
    return mapq;
  }

  public String getCigar() {
    return cigar;
  }

  public String getSeq() {
    return seq;
  }

  public String getQual() {
    return qual;
  }
}
