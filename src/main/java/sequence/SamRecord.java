package sequence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SamRecord {
  private static final List<String> HEADER_TAGS =
      new ArrayList<>(Arrays.asList("@HD", "@SQ", "@RG", "@PG", "@CO"));
  private String rname; // Reference sequence NAME
  private int pos; // 	1- based leftmost mapping POSition
  private int mapq; // MAPping Quality
  private String cigar; // CIGAR String
  private String seq; // segment SEQuence
  private String qual; // ASCII of Phred-scaled base QUALity+33

  private SamRecord(String rname, int pos, int mapq, String cigar, String seq, String qual) {
    this.rname = rname;
    this.pos = pos;
    this.mapq = mapq;
    this.cigar = cigar;
    this.seq = seq;
    this.qual = qual;
  }

  private SamRecord(String[] recordData) {
    this(
        recordData[2],
        Integer.parseInt(recordData[3]),
        Integer.parseInt(recordData[4]),
        recordData[5],
        recordData[9],
        recordData[10]);
  }

  public static SamRecord init(String recordData) {
    String[] subStr;
    // replace 2 spaces with 1 to avoid wrong line splitting
    recordData = recordData.replace("  ", " ");
    subStr = recordData.split(" ");
    if (!HEADER_TAGS.contains(subStr[0])) {
      return new SamRecord(subStr);
    }
    return null;
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
