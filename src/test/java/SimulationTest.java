import at.fhooe.mtd.ecs.Engine;
import at.fhooe.mtd.ecs.Entity;
import at.fhooe.mtd.ecs.EntityFamily;
import cz.cvut.fel.pjv.controllers.SimpleAtraction;
import cz.cvut.fel.pjv.controllers.ecsHandlerComp.MoveableHandl;
import cz.cvut.fel.pjv.model.ecsPrepearedObjects.Mover;
import cz.cvut.fel.pjv.model.ecsSystems.N2mAtraction;
import cz.cvut.fel.pjv.model.utils.SimulationState;
import cz.cvut.fel.pjv.view.frames.WindowFrame;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SimulationTest {

    private Engine engine;
    private WindowFrame windowFrame;
    private SimulationState simulationState;
    private Canvas mockCanvas;
    private CountDownLatch latch;

    @BeforeEach
    public void setUp() {
        engine = new Engine();
        windowFrame = Mockito.mock(WindowFrame.class);
        mockCanvas = Mockito.mock(Canvas.class);
        latch = new CountDownLatch(1);

        when(windowFrame.getGameLayoutCanvas()).thenReturn(mockCanvas);

        // Mock the loop method to avoid starting an infinite loop
        doAnswer(invocation -> {
            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
            executor.scheduleAtFixedRate(() -> {
//                engine.getNumOfEntities();

                List<Entity> entities = engine.getEntities(EntityFamily.create(MoveableHandl.class));
                for (Entity entity : entities) {
                    MoveableHandl moveableHandl = entity.getComponent(MoveableHandl.class);
                    moveableHandl.update();
                }
                engine.update(1.0 / 60); // Call update at 60 FPS

            }, 0, 1000 / 60, TimeUnit.MILLISECONDS);

            executor.schedule(() -> {
                windowFrame.stopLoop();
                latch.countDown();
                executor.shutdown();
            }, 5, TimeUnit.SECONDS); // Reduced to 5 seconds for testing purposes
            return null;
        }).when(windowFrame).loop(any(Engine.class), any(SimpleAtraction.class));
    }

    @Test
    public void testSingleMoverSimulation() throws InterruptedException {
        simulationState = new SimulationState();
        simulationState.createNwithoutAtractor(1);
        Mover mover = simulationState.getMovers().get(0);
        Point2D initialPosition = mover.getPosComp().getPosition();
        Point2D velocity = mover.getVelComp().getVelocity();

        System.out.println("Mover Initial Position: " + initialPosition);
        System.out.println("Mover Velocity: " + velocity);

        SimpleAtraction simpleAtraction = new SimpleAtraction(windowFrame, engine);
        simpleAtraction.setSimulationState(simulationState);


        System.out.println("Engine Entities: " + engine.getNumOfEntities());

        // Start the loop after adding the system
        windowFrame.loop(engine, simpleAtraction);

        // Wait for the simulation to complete
        latch.await();

        // Assert the expected conditions
        Mover updatedMover = simpleAtraction.getSimulationState().getMovers().get(0);

        System.out.println("Updated Mover Position: " + updatedMover.getPosComp().getPosition());
        assertEquals(initialPosition.getX() + velocity.getX() * 5, updatedMover.getPosComp().getPosition().getX(), 1.0); // Adjusted for 5 seconds
        assertEquals(initialPosition.getY() + velocity.getY() * 5, updatedMover.getPosComp().getPosition().getY(), 1.0); // Adjusted for 5 seconds
    }

    @Test
    public void testThreeMoversSimulation() throws InterruptedException {
        simulationState.createNwithoutAtractor(3);

        SimpleAtraction simpleAtraction = new SimpleAtraction(windowFrame, engine);
        simpleAtraction.setSimulationState(simulationState);

        // Start the loop after adding the system
        windowFrame.loop(engine, simpleAtraction);

        // Wait for the simulation to complete
        latch.await();

        // Assert the expected conditions for each mover
        for (Mover mover : simulationState.getMovers()) {
            Point2D initialPosition = mover.getPosComp().getPosition();
            Point2D velocity = mover.getVelComp().getVelocity();
            double expectedX = initialPosition.getX() + velocity.getX() * 5; // Adjusted for 5 seconds
            double expectedY = initialPosition.getY() + velocity.getY() * 5; // Adjusted for 5 seconds

            System.out.println("Mover Initial Position: " + initialPosition);
            System.out.println("Mover Velocity: " + velocity);
            System.out.println("Mover Updated Position: " + mover.getPosComp().getPosition());

            assertEquals(expectedX, mover.getPosComp().getPosition().getX(), 1.0);
            assertEquals(expectedY, mover.getPosComp().getPosition().getY(), 1.0);
        }
    }
}