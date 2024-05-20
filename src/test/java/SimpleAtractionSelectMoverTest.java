import at.fhooe.mtd.ecs.Engine;
import cz.cvut.fel.pjv.jsPORT.SimpleAtraction;
import cz.cvut.fel.pjv.model.SimulationState;
import cz.cvut.fel.pjv.jsPORT.Mover;
import cz.cvut.fel.pjv.model.ecsComponents.*;
import cz.cvut.fel.pjv.view.frames.WindowFrame;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(PowerMockExtension.class)
@PrepareForTest({Engine.class})
public class SimpleAtractionSelectMoverTest {

    @Mock
    private WindowFrame window;

    @Mock
    private Engine engine;

    @Mock
    private SimulationState simulationState;

    @Mock
    private Mover mover;

    @InjectMocks
    private SimpleAtraction simpleAtraction;

    @BeforeEach
    public void setUp() {
        PowerMockito.mockStatic(Engine.class);
        MockitoAnnotations.openMocks(this);
        when(simulationState.getMovers()).thenReturn(new ArrayList<>());
        simpleAtraction.simulationState = simulationState;
    }

    @Test
    public void testSelectMover() {
        when(mover.getPosComp()).thenReturn(new CompPosition(100, 100));
        when(mover.getSize()).thenReturn(new CompSize(10));
        when(mover.getColor()).thenReturn(new CompColor(Color.RED));

        simulationState.getMovers().add(mover);

        Point2D pointInside = new Point2D(105, 105);
        Point2D pointOutside = new Point2D(200, 200);

        Mover selectedMoverInside = simpleAtraction.selectMover(pointInside);
        Mover selectedMoverOutside = simpleAtraction.selectMover(pointOutside);

        assertEquals(mover, selectedMoverInside);
        assertNull(selectedMoverOutside);
    }
}