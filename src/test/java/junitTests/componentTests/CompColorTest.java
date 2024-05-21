package junitTests.componentTests;

import cz.cvut.fel.pjv.model.ecsComponents.CompColor;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CompColorTest {

    @Test
    public void testColorInitialization() {
        CompColor color = new CompColor(Color.RED);
        assertEquals(Color.RED, color.color);
    }

    @Test
    public void testSetColor() {
        CompColor color = new CompColor(Color.RED);
        color.color = Color.BLUE;
        assertEquals(Color.BLUE, color.color);
    }
}