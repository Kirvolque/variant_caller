package sequence;

import lombok.Getter;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class SamRecord {
  private static final String DOUBLE_SPACE = "  ";
  private static final String SINGLE_SPACE = " ";
  private static final List<String> HEADER_TAGS =
      new ArrayList<>(Arrays.asList("@HD", "@SQ", "@RG", "@PG", "@CO"));

  private static final Pattern cigarPattern =
      Pattern.compile("[0-9]*[D|M|I|X]", Pattern.CASE_INSENSITIVE);
  private static final Pattern cigarLetterPattern =
      Pattern.compile("[D|M|I|X]", Pattern.CASE_INSENSITIVE);

  @Getter
  private boolean isHeader;
  @Getter
  private String rname; // Reference sequence NAME
  @Getter
  private int pos; // 1- based leftmost mapping POSition
  @Getter
  private int mapq; // MAPping Quality
  @Getter
  private String cigar; // CIGAR String
  @Getter
  private String seq; // segment SEQuence
  @Getter
  private String qual; // ASCII of Phred-scaled base QUALity+33
  private int cigarLength = -1;

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

  public Stream<Map.Entry<Integer, Character>> getCigarStream() {
    Matcher matcher = cigarPattern.matcher(cigar);
    final Stream.Builder<Map.Entry<Integer, Character>> sb = Stream.builder();

    while (matcher.find()) {
      Matcher letterMatch = cigarLetterPattern.matcher(matcher.group());
      if (letterMatch.find()) {
        Character character = letterMatch.group().charAt(0);
        Integer number = Integer.valueOf(matcher.group().substring(0, letterMatch.start()));
        sb.add(new AbstractMap.SimpleEntry<>(number, character));
      }
    }
    return sb.build();
  }

  private int countCigarLength() {
    return getCigarStream()
        .filter(
            integerCharacterEntry ->
                integerCharacterEntry.getValue().equals('M')
                    || integerCharacterEntry.getValue().equals('I')
                    || integerCharacterEntry.getValue().equals('X')
                    || integerCharacterEntry.getValue().equals('=')
                    || integerCharacterEntry.getValue().equals('S'))
        .mapToInt(Map.Entry::getKey)
        .sum();
  }

  public int getCigarLength() {
    if (cigarLength != -1) {
      return cigarLength;
    } else {
      cigarLength = countCigarLength();
      return cigarLength;
    }
  }

  public boolean fitInterval(Interval interval) {
    return (interval.getBegin() <= getPos() - 1) && (interval.length() >= getCigarLength() - 1);
  }
}
