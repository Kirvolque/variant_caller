package vcfwriter.variation;

import org.junit.Assert;
import org.junit.Test;

public class TestVariation {

  @Test
  public void nextField() {
    Variation var = new Variation("20", 1444, "G", "A");
    Assert.assertTrue(var.getNextField().equals("20"));
    Assert.assertTrue(var.getNextField().equals("1444"));
    Assert.assertTrue(var.getNextField().equals("."));
    Assert.assertTrue(var.getNextField().equals("G"));
    Assert.assertTrue(var.getNextField().equals("A"));
    Assert.assertTrue(var.getNextField().equals("."));
    Assert.assertTrue(var.getNextField().equals("."));
    Assert.assertTrue(var.getNextField().equals("."));
  }

}
