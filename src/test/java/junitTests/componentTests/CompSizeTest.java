package junitTests.componentTests;

import cz.cvut.fel.pjv.model.ecsComponents.CompSize;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CompSizeTest {

    @Test
    public void testSizeInitialization() {
        CompSize size = new CompSize(50);
        assertEquals(50, size.size);
    }

    @Test
    public void testSetSize() {
        CompSize size = new CompSize(50);
        size.size = 100;
        assertEquals(100, size.size);
    }
}