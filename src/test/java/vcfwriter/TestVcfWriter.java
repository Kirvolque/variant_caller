package vcfwriter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import temporarytools.TemporaryTools;
import vcfwriter.variation.Variation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestVcfWriter {

  @Test
  public void fileCreate() {
    try {
      Path path = TemporaryTools.tempFile("result", ".vcf");
      VcfWriter vcfWriter = new VcfWriter(path.toString());
      Assertions.assertTrue(Files.exists(path));
      vcfWriter.close();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  public void сontainData() {
    try {
      Path path = TemporaryTools.tempFile("result", ".vcf");
      VcfWriter vcfWriter = new VcfWriter(path.toString());
      Variation var1 = new Variation("20", 1444, "G", "A");
      Variation var2 = new Variation("20", 1444, "microsati", "G", "A", "47", "PASS", "NS=3", 0);
      List<Variation> vars = new ArrayList<>();
      vars.add(var1);
      vars.add(var2);
      vcfWriter.writeData(vars);
      vcfWriter.close();
      Scanner scanner = new Scanner(path.toFile());
      Assertions.assertTrue(scanner.next().equals("20"));
      Assertions.assertTrue(scanner.nextInt() == 1444);
      Assertions.assertTrue(scanner.next().equals("."));
      Assertions.assertTrue(scanner.next().equals("G"));
      Assertions.assertTrue(scanner.next().equals("A"));
      Assertions.assertTrue(scanner.next().equals("."));
      Assertions.assertTrue(scanner.next().equals("."));
      Assertions.assertTrue(scanner.next().equals("."));

      Assertions.assertTrue(scanner.next().equals("20"));
      Assertions.assertTrue(scanner.nextInt() == 1444);
      Assertions.assertTrue(scanner.next().equals("microsati"));
      scanner.close();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  public void containHeaders() {
    try {
      Path path = TemporaryTools.tempFile("result", ".vcf");
      VcfWriter vcfWriter = new VcfWriter(path.toString());
      vcfWriter.writeHeadersOfData();
      vcfWriter.close();
      Scanner scanner = new Scanner(path.toFile());
      Assertions.assertTrue(scanner.next().equals("#CHROM"));
      Assertions.assertTrue(scanner.next().equals("POS"));
      Assertions.assertTrue(scanner.next().equals("ID"));
      Assertions.assertTrue(scanner.next().equals("REF"));
      Assertions.assertTrue(scanner.next().equals("ALT"));
      Assertions.assertTrue(scanner.next().equals("QUAL"));
      Assertions.assertTrue(scanner.next().equals("FILTER"));
      Assertions.assertTrue(scanner.next().equals("INFO"));
      Assertions.assertTrue(scanner.hasNextLine());
      Assertions.assertFalse(scanner.hasNext());
      scanner.close();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}
