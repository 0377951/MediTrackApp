import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SanityTest {

    @BeforeEach
    void setUp() {
        System.out.println("[Before] Setting up test");
    }

    @AfterEach
    void tearDown() {
        System.out.println("[After] Cleaning up");
    }

    @Test
    void junitIsConfiguredCorrectly() {
        assertEquals(4, 2 + 2);
    }

    @Test
    void firstTest() {
        System.out.println("[Test] firstTest");
        assertTrue(true);
    }

    @Test
    void secondTest() {
        System.out.println("[Test] secondTest");
        assertEquals(10, 5 * 2);
    }
}
