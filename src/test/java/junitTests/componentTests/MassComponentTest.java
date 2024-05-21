package junitTests.componentTests;

import cz.cvut.fel.pjv.model.ecsComponents.MassComponent;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MassComponentTest {

    @Test
    public void testMassInitialization() {
        MassComponent mass = new MassComponent(100);
        assertEquals(100, mass.mass.get());
    }

    @Test
    public void testSetMass() {
        MassComponent mass = new MassComponent(100);
        mass.mass.set(200);
        assertEquals(200, mass.mass.get());
    }
}