import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CoverageTest {
    @Test
    public void test() {
        String s = new Coverage().shouldBeTested("you");
        assertEquals("hello you", s);
    }
}
