import org.junit.jupiter.api.Test;

import java.lang.annotation.Target;

import static org.junit.jupiter.api.Assertions.*;

public class CheckSonarOptionalSupportTest {
    @Test
    public void testMe() {
        int i = CheckSonarOptionalSupport.checkMe();
        assertEquals(6, i);
    }
}