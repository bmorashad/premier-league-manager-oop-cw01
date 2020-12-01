import static org.junit.Assert.*;

import org.junit.Test;

public class MatchServiceTest {

  @Test
  public void generate() {
    int a = 1 + 1;
    assertEquals(2, a);
  }

  @Test
  public void testString() {
    String str = "Hello world";
    assertFalse(str.isEmpty());
	System.out.println("haha");
  }
}
