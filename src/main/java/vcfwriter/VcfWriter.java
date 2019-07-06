package vcfwriter;

import org.apache.commons.lang3.SystemUtils;
import vcfwriter.variation.Variation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class VcfWriter implements AutoCloseable {

  private static final String HEADER = "#CHROM\tPOS\tID\tREF\tALT\tQUAL\tFILTER\tINFO\t";
  private FileWriter fileWriter;

  public VcfWriter(String path) throws IOException {
    fileWriter = new FileWriter(path);
  }

  public void close() throws IOException {
    fileWriter.close();
  }

  public void writeData(List<Variation> data) throws IOException {
    for (Variation var : data) {
      fileWriter.write(var.toVcfLine());
      if (SystemUtils.IS_OS_WINDOWS) {
        fileWriter.write("\r\n");
      } else {
        fileWriter.write("\n");
      }
    }
  }

  public void writeHeadersOfData() throws IOException {
    fileWriter.write(HEADER);
    if (SystemUtils.IS_OS_WINDOWS) {
      fileWriter.write("\r\n");
    } else {
      fileWriter.write("\n");
    }
  }
}