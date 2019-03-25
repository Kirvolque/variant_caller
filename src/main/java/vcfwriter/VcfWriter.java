package vcfwriter;


import com.sun.javafx.PlatformUtil;
import vcfwriter.variation.Variation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class VcfWriter implements AutoCloseable{

  private FileWriter fileWriter;

  public VcfWriter(String path) throws IOException {
   fileWriter = new FileWriter(path);
  }

  public void close() throws IOException {
    fileWriter.close();
  }

  public void writeData(List<Variation> data) throws IOException {
    String currentField;
    for (Variation var: data) {
      currentField = var.getNextField();
      while (!currentField.equals("")) {
        fileWriter.write(currentField);
        fileWriter.write("\t");
        currentField = var.getNextField();
      }
      if (PlatformUtil.isWindows()) {
        fileWriter.write("\r\n");
      }
      else {
        fileWriter.write("\n");
      }
    }
  }

  public void writeHeadersOfData() throws IOException {
    fileWriter.write("#CHROM\t");
    fileWriter.write("POS\t");
    fileWriter.write("ID\t");
    fileWriter.write("REF\t");
    fileWriter.write("ALT\t");
    fileWriter.write("QUAL\t");
    fileWriter.write("FILTER\t");
    fileWriter.write("INFO");
    if (PlatformUtil.isWindows()) {
      fileWriter.write("\r\n");
    } else {
      fileWriter.write("\n");
    }
  }

}
