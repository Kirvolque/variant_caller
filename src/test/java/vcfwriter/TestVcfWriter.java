package vcfwriter;

import org.junit.Assert;
import org.junit.Test;
import vcfwriter.variation.Variation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestVcfWriter {
  @Test
  public void fileCreate() {
    try (VcfWriter vcfWriter = new VcfWriter("result.vcf")) {
      File file = new File("result.vcf");
      Assert.assertTrue(Files.exists(file.toPath()));
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  public void сontainData() {
    try {
      VcfWriter vcfWriter = new VcfWriter("C:\\Education\\BioScience\\variant_caller\\src\\test\\resources\\result.vcf");
      Variation var1 = new Variation("20", 1444, "G", "A");
      Variation var2 = new Variation("20", 1444, "microsati", "G", "A", "47", "PASS", "NS=3");
      List<Variation> vars = new ArrayList<>();
      vars.add(var1);
      vars.add(var2);
      vcfWriter.writeData(vars);
      vcfWriter.close();
      File file = new File("C:\\Education\\BioScience\\variant_caller\\src\\test\\resources\\result.vcf");
      Scanner scanner = new Scanner(file);
      Assert.assertTrue(scanner.next().equals("20"));
      Assert.assertTrue(scanner.nextInt() == 1444);
      Assert.assertTrue(scanner.next().equals("."));
      Assert.assertTrue(scanner.next().equals("G"));
      Assert.assertTrue(scanner.next().equals("A"));
      Assert.assertTrue(scanner.next().equals("."));
      Assert.assertTrue(scanner.next().equals("."));
      Assert.assertTrue(scanner.next().equals("."));

      Assert.assertTrue(scanner.next().equals("20"));
      Assert.assertTrue(scanner.nextInt() == 1444);
      Assert.assertTrue(scanner.next().equals("microsati"));
      scanner.close();
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
    }

  }

  @Test
  public void continHeaders() {
    try {
      VcfWriter vcfWriter = new VcfWriter("C:\\Education\\BioScience\\variant_caller\\src\\test\\resources\\result.vcf");
      vcfWriter.writeHeadersOfData();
      vcfWriter.close();
      File file = new File("C:\\Education\\BioScience\\variant_caller\\src\\test\\resources\\result.vcf");
      Scanner scanner = new Scanner(file);
      Assert.assertTrue(scanner.next().equals("#CHROM"));
      Assert.assertTrue(scanner.next().equals("POS"));
      Assert.assertTrue(scanner.next().equals("ID"));
      Assert.assertTrue(scanner.next().equals("REF"));
      Assert.assertTrue(scanner.next().equals("ALT"));
      Assert.assertTrue(scanner.next().equals("QUAL"));
      Assert.assertTrue(scanner.next().equals("FILTER"));
      Assert.assertTrue(scanner.next().equals("INFO"));
      Assert.assertTrue(scanner.hasNextLine());
      Assert.assertFalse(scanner.hasNext());
      scanner.close();
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
    }

  }


}
