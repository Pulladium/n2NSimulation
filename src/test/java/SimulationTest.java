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
    private static final long FRAME_DURATION = 1000 / 60;
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

        simulationState = new SimulationState();
    }

    @Test
    public void testSingleMoverSimulation() throws InterruptedException {
        simulationState.createNwithoutAtractor(1);
        Mover mover = simulationState.getMovers().get(0);
        Point2D initialPosition = mover.getPosComp().getPosition();
        Point2D velocity = mover.getVelComp().getVelocity();

        System.out.println("Mover Initial Position: " + initialPosition);
        System.out.println("Mover Velocity: " + velocity);

        N2mAtraction n2mAtraction = new N2mAtraction(simulationState.getMovers(), simulationState.getSun());
        engine.addSystem(n2mAtraction);
        engine.addEntity(mover.getCurrentEntity());

        System.out.println("Engine Entities: " + engine.getNumOfEntities());

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        final long[] lastUpdate = {System.nanoTime()};

        executor.scheduleAtFixedRate(() -> {
            long now = System.nanoTime();
            double dt = (now - lastUpdate[0]) / 1_000_000_000.0; // Convert to seconds
            lastUpdate[0] = now;

            List<Entity> entities = engine.getEntities(EntityFamily.create(MoveableHandl.class));
            for (Entity entity : entities) {
                MoveableHandl moveableHandl = entity.getComponent(MoveableHandl.class);
                moveableHandl.update(dt); // Pass dt to update method
            }
        }, 0, FRAME_DURATION, TimeUnit.MILLISECONDS);


        executor.schedule(() -> {
            latch.countDown();
            executor.shutdown();
        }, 5, TimeUnit.SECONDS); // Reduced to 5 seconds for testing purposes



        latch.await();

        // Assert the expected conditions
        Mover updatedMover = simulationState.getMovers().get(0);
        System.out.println("Updated Mover Position: " + updatedMover.getPosComp().getPosition());
        System.out.println("Expected X: " + (initialPosition.getX() + velocity.getX() * 5));
        assertEquals(initialPosition.getX() + velocity.getX() * 5, updatedMover.getPosComp().getPosition().getX(), 1.0); // Adjusted for 5 seconds
        assertEquals(initialPosition.getY() + velocity.getY() * 5, updatedMover.getPosComp().getPosition().getY(), 1.0); // Adjusted for 5 seconds
    }



    @Test
    public void testThreeMoversSimulation() throws InterruptedException {
        simulationState.createNwithoutAtractor(3);

        ArrayList<Point2D> initialPositions = new ArrayList<>();
        ArrayList<Point2D> initVelocities = new ArrayList<>();
        for (Mover mover : simulationState.getMovers()) {
            Point2D initialPosition = mover.getPosComp().getPosition();
            Point2D initVelocity = mover.getVelComp().getVelocity();


            initialPositions.add(initialPosition);
            initVelocities.add(initVelocity);

            engine.addEntity(mover.getCurrentEntity());
        }
        N2mAtraction n2mAtraction = new N2mAtraction(simulationState.getMovers(), simulationState.getSun());
        engine.addSystem(n2mAtraction);

        System.out.println("Engine Entities: " + engine.getNumOfEntities());

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        final long[] lastUpdate = {System.nanoTime()};

        executor.scheduleAtFixedRate(() -> {
            long now = System.nanoTime();
            double dt = (now - lastUpdate[0]) / 1_000_000_000.0; // Convert to seconds
            lastUpdate[0] = now;

            List<Entity> entities = engine.getEntities(EntityFamily.create(MoveableHandl.class));
            for (Entity entity : entities) {
                MoveableHandl moveableHandl = entity.getComponent(MoveableHandl.class);
                moveableHandl.update(dt); // Pass dt to update method
            }
        }, 0, FRAME_DURATION, TimeUnit.MILLISECONDS);
        //engine.update(1/60);
        ScheduledExecutorService engineExecutor = Executors.newSingleThreadScheduledExecutor();
        engineExecutor.scheduleAtFixedRate(() -> {
            engine.update(1.0/60);
        }, 0, FRAME_DURATION, TimeUnit.MILLISECONDS);


        executor.schedule(() -> {
            latch.countDown();
            executor.shutdown();
        }, 5, TimeUnit.SECONDS); // Reduced to 5 seconds for testing purposes

        engineExecutor.schedule(() -> {
            latch.countDown();
            engineExecutor.shutdown();
        }, 5, TimeUnit.SECONDS); // Reduced to 5 seconds for testing purposes


        latch.await();

        // Assert the expected conditions for each mover
        int cnt = 0;
        for (Mover mover : simulationState.getMovers()) {
            Point2D initialPosition = initialPositions.get(cnt);
            Point2D initVelocity = initVelocities.get(cnt);

            double expectedX = initialPosition.getX() + initVelocity.getX() * 5; // Adjusted for 5 seconds
            double expectedY = initialPosition.getY() + initVelocity.getY() * 5; // Adjusted for 5 seconds

            System.out.println("Mover Initial Position: " + initialPosition);
            System.out.println("Mover Velocity: " + initVelocity);

            System.out.println("Mover Expected Position: " + new Point2D(expectedX, expectedY));
            System.out.println("Mover Updated Position: " + mover.getPosComp().getPosition());

            cnt++;

            assertEquals(expectedX, mover.getPosComp().getPosition().getX(), 100.0); // Added tolerance of 400
            assertEquals(expectedY, mover.getPosComp().getPosition().getY(), 100.0);
        }
    }

}