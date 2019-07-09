package vcfwriter.variation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;


public class TestVariation {

  @Test
  public void nextFieldList() {
    Variation var = new Variation("20", 1444, "G", "A");
    List<String> fieldList = var.getFieldList();
    Assertions.assertTrue(fieldList.get(0).equals("20"));
    Assertions.assertTrue(fieldList.get(1).equals("1444"));
    Assertions.assertTrue(fieldList.get(2).equals("."));
    Assertions.assertTrue(fieldList.get(3).equals("G"));
    Assertions.assertTrue(fieldList.get(4).equals("A"));
    Assertions.assertTrue(fieldList.get(5).equals("."));
    Assertions.assertTrue(fieldList.get(6).equals("."));
    Assertions.assertTrue(fieldList.get(7).equals("."));
  }

}
