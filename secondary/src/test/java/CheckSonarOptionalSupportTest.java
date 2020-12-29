import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CheckSonarOptionalSupportTest {
    @Test
    public void testMe() {
        int i = CheckSonarOptionalSupport.checkMe();
        assertEquals(6, i);
    }
}
