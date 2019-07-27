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

class TestVcfWriter {

  @Test
  void fileCreate() {
    try {
      Path path = TemporaryTools.tempFile("result", ".vcf");
      VcfWriter vcfWriter = VcfWriter.init(path.toString());
      Assertions.assertTrue(Files.exists(path));
      vcfWriter.close();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  void containData() {
    try {
      Path path = TemporaryTools.tempFile("result", ".vcf");
      VcfWriter vcfWriter = VcfWriter.init(path.toString());
      Variation var1 = new Variation("20", 1444, "G", "A");
      Variation var2 = new Variation("20", 1444, "microsati", "G", "A", "47", "PASS", "NS=3", 0);
      List<Variation> vars = new ArrayList<>();
      vars.add(var1);
      vars.add(var2);
      vcfWriter.writeVcf(vars);
      vcfWriter.close();
      Scanner scanner = new Scanner(path.toFile());
      scanner.nextLine(); // skip headers
      Assertions.assertEquals("20", scanner.next());
      Assertions.assertEquals(1444, scanner.nextInt());
      Assertions.assertEquals(".", scanner.next());
      Assertions.assertEquals("G", scanner.next());
      Assertions.assertEquals("A", scanner.next());
      Assertions.assertEquals(".", scanner.next());
      Assertions.assertEquals(".", scanner.next());
      Assertions.assertEquals(".", scanner.next());

      Assertions.assertEquals("20", scanner.next());
      Assertions.assertEquals(1444, scanner.nextInt());
      Assertions.assertEquals("microsati", scanner.next());
      scanner.close();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  void containHeaders() {
    try {
      Path path = TemporaryTools.tempFile("result", ".vcf");
      VcfWriter vcfWriter = VcfWriter.init(path.toString());
      vcfWriter.writeVcf(new ArrayList<>());
      vcfWriter.close();
      Scanner scanner = new Scanner(path.toFile());
      Assertions.assertEquals("#CHROM", scanner.next());
      Assertions.assertEquals("POS", scanner.next());
      Assertions.assertEquals("ID", scanner.next());
      Assertions.assertEquals("REF", scanner.next());
      Assertions.assertEquals("ALT", scanner.next());
      Assertions.assertEquals("QUAL", scanner.next());
      Assertions.assertEquals("FILTER", scanner.next());
      Assertions.assertEquals("INFO", scanner.next());
      Assertions.assertTrue(scanner.hasNextLine());
      Assertions.assertFalse(scanner.hasNext());
      scanner.close();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}
