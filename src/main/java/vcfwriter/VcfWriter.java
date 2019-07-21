package vcfwriter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.SystemUtils;
import vcfwriter.variation.Variation;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VcfWriter implements AutoCloseable {

  private static final String HEADER = "#CHROM\tPOS\tID\tREF\tALT\tQUAL\tFILTER\tINFO\t";
  private final FileWriter fileWriter;

  public static VcfWriter init(String path) {
    FileWriter fileWriter;
    try {
      fileWriter = new FileWriter(path);
    } catch (IOException e) {
      throw new UncheckedIOException(String.format("File %s not found", path), e);
    }
    return new VcfWriter(fileWriter);
  }

  public void writeVcf(List<Variation> data) {
    try {
      writeHeaders();
      writeData(data);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public void close() {
    try {
      fileWriter.close();
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  private void writeData(List<Variation> data) throws IOException {
    for (Variation var : data) {
      fileWriter.write(var.toVcfLine());
      if (SystemUtils.IS_OS_WINDOWS) {
        fileWriter.write("\r\n");
      } else {
        fileWriter.write("\n");
      }
    }
  }

  private void writeHeaders() throws IOException {
    fileWriter.write(HEADER);
    if (SystemUtils.IS_OS_WINDOWS) {
      fileWriter.write("\r\n");
    } else {
      fileWriter.write("\n");
    }
  }
}
