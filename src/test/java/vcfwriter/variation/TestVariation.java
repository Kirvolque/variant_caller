package vcfwriter.variation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class TestVariation {

  @Test
  public void nextField() {
    Variation var = new Variation("20", 1444, "G", "A");
    Assertions.assertTrue(var.getNextField().equals("20"));
    Assertions.assertTrue(var.getNextField().equals("1444"));
    Assertions.assertTrue(var.getNextField().equals("."));
    Assertions.assertTrue(var.getNextField().equals("G"));
    Assertions.assertTrue(var.getNextField().equals("A"));
    Assertions.assertTrue(var.getNextField().equals("."));
    Assertions.assertTrue(var.getNextField().equals("."));
    Assertions.assertTrue(var.getNextField().equals("."));
  }

}
