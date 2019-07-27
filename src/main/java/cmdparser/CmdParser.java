package cmdparser;

import bedparser.BedParser;
import fastaparser.FastaParser;
import lombok.AccessLevel;
import lombok.Setter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import samparser.SamParser;
import sequence.BedData;
import variantcaller.VariantCaller;
import vcfwriter.VcfWriter;
import vcfwriter.variation.Variation;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Setter(AccessLevel.PRIVATE)
public class CmdParser {
  private Path fastaFilePath;
  private Path samFilePath;
  private Path bedFilePath;
  private String vcfFilePath;
  private Double minAlleleFrequency;

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

  public static void main(final String... args) {
    try {
      CmdParser cmdParser = new CmdParser();
      cmdParser.parse(args);
    } catch (ParseException exp) {
      System.out.println("Unexpected exception:" + exp.getMessage());
    }
  }

  private void parse(String[] args) throws ParseException {
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
        setFastaFilePath(Paths.get(fastaFilePath));
        setSamFilePath(Paths.get(samFilePath));
        setBedFilePath(Paths.get(bedFilePath));
        setVcfFilePath(vcfFilePath);
        setMinAlleleFrequency(minAlleleFrequency);
        executeVariantCalling();
      }
    }
  }

  private void executeVariantCalling() {
    BedData bedData = BedParser.collectIntervals(bedFilePath);

    try (VcfWriter vcfWriter = VcfWriter.init(vcfFilePath);
        SamParser samParser = SamParser.init(samFilePath);
        FastaParser fastaParser = FastaParser.init(fastaFilePath)) {

      VariantCaller variantCaller = new VariantCaller(samParser, fastaParser);

      List<Variation> variationList =
          variantCaller
              .callVariationsForIntervals(bedData)
              .filter(variation -> variantCaller.filterVariation(variation, minAlleleFrequency))
              .collect(Collectors.toList());

      vcfWriter.writeVcf(variationList);
    }
  }
}
