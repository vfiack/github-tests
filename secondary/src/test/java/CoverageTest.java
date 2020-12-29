import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CoverageTest {
    @Test
    public void test() {
        String s = new Coverage().shouldBeTested("you");
        assertEquals("hello you", s);
    }
}