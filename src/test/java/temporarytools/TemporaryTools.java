package temporarytools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;

public class TemporaryTools {

  public static Path tempFile(
      final String name, final String suffix, final FileAttribute... attributes)
      throws IOException {
    final Path filePath = Files.createTempFile(name, suffix, attributes);
    filePath.toFile().deleteOnExit();
    return filePath;
  }

  public static Path tampDir(final String name, final FileAttribute... attributes)
      throws IOException {
    final Path dirPath = Files.createTempDirectory(name, attributes);
    dirPath.toFile().deleteOnExit();
    return dirPath;
  }
}
