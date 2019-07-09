package cmdparser;

import fastaparser.FastaParser;
import org.apache.commons.cli.*;
import samparser.SamParser;
import sequence.FastaSequence;
import variantcaller.VariantCaller;
import vcfwriter.VcfWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CmdParser {

  private static String fastaFilePath;
  private static Path samFilePath;
  private static Path bedFilePath;
  private static String vcfFilePath;
  private static Double minAlleleFrequency;

  private static void setFastaFilePath(String fastaFilePath) {
    CmdParser.fastaFilePath = fastaFilePath;
  }

  private static void setSamFilePath(Path samFilePath) {
    CmdParser.samFilePath = samFilePath;
  }

  private static void setBedFilePath(Path bedFilePath) {
    CmdParser.bedFilePath = bedFilePath;
  }

  private static void setVcfFilePath(String vcfFilePath) {
    CmdParser.vcfFilePath = vcfFilePath;
  }

  private static void setMinAlleleFrequency(Double minAlleleFrequency) {
    CmdParser.minAlleleFrequency = minAlleleFrequency;
  }

  private static void printHelp(
      final Options options,
      final int printedRowWidth,
      final String header,
      final String footer,
      final int spacesBeforeOption,
      final int spacesBeforeOptionDescription,
      final boolean displayUsage,
      final OutputStream out) {
    final String commandLineSyntax = "java variant_caller.jar";
    final PrintWriter writer = new PrintWriter(out);
    final HelpFormatter helpFormatter = new HelpFormatter();
    helpFormatter.printHelp(
        writer,
        printedRowWidth,
        commandLineSyntax,
        header,
        options,
        spacesBeforeOption,
        spacesBeforeOptionDescription,
        footer,
        displayUsage);
    writer.flush();
  }

  public static void parse(String[] args) throws ParseException, IOException {

    Options options = new Options();
    options.addOption("h", "help", false, "Help");
    options.addOption("s", "sam", true, "Path to SAM file");
    options.addOption("f", "fasta", true, "Path to fasta file");
    options.addOption("b", "bed", true, "Path to BED file");
    options.addOption(
        "af", "allele-frequency", true, "Minimal allele frequency needed to be printed");
    options.addOption("o", "output", true, "Path to output VCF file");

    CommandLineParser parser = new DefaultParser();
    CommandLine line = parser.parse(options, args);

    if (line.hasOption("help")) {
      printHelp(options, 80, "Options", "-- HELP --", 3, 5, true, System.out);
      return;
    }
    if (line.hasOption("sam")
        && line.hasOption("fasta")
        && line.hasOption("bed")
        && line.hasOption("af")
        && line.hasOption("output")) {
      String fastaFilePath = line.getOptionValue("fasta", null);
      String samFilePath = line.getOptionValue("sam", null);
      String bedFilePath = line.getOptionValue("bed", null);
      String vcfFilePath = line.getOptionValue("output", null);
      Double minAlleleFrequency = Double.parseDouble(line.getOptionValue("af", null));

      if (fastaFilePath == null
          || samFilePath == null
          || bedFilePath == null
          || vcfFilePath == null
          || minAlleleFrequency == null) {
        System.out.println("Wrong param for filepath option");
      } else {
        setFastaFilePath(fastaFilePath);
        setSamFilePath(Paths.get(samFilePath));
        setBedFilePath(Paths.get(bedFilePath));
        setVcfFilePath(vcfFilePath);
        setMinAlleleFrequency(minAlleleFrequency);
        work();
      }
    }
  }

  private static void work() throws IOException {
    FastaSequence fastaSequence = FastaParser.parseFasta(fastaFilePath, bedFilePath);
    VariantCaller variantCaller = new VariantCaller();
    variantCaller.processSamRecords(fastaSequence, SamParser.parseSam(samFilePath));
    VcfWriter vcfWriter = new VcfWriter(vcfFilePath);
    vcfWriter.writeHeadersOfData();
    vcfWriter.writeData(variantCaller.filterVariations(minAlleleFrequency));
    vcfWriter.close();
  }

  public static void main(final String... args) {
    try {
      parse(args);
    } catch (ParseException exp) {
      System.out.println("Unexpected exception:" + exp.getMessage());
    } catch (IOException ioExp) {
      System.out.println("File doesn't exist");
    }
  }
}
