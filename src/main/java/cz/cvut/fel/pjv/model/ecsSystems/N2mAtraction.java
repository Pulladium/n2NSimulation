package cz.cvut.fel.pjv.model.ecsSystems;

import at.fhooe.mtd.ecs.EngineSystem;
import cz.cvut.fel.pjv.model.ecsPrepearedObjects.Mover;
import cz.cvut.fel.pjv.controllers.ecsHandlerComp.MoveableHandl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;

import static cz.cvut.fel.pjv.model.GLOBALS.log;
/**
 * Manages the N-body attraction simulation system.
 * <p>
 * Extends {@link EngineSystem} @see <a href="https://github.com/divotkey/ecs/tree/master/Entity%20Component%20System/src/at/fhooe/mtd/ecs">ECS</a>
 * <br>
 * and handles the movement and attraction of {@link Mover} objects.
 * Uses multithreading to update the positions of movers efficiently.
 */
public class N2mAtraction extends EngineSystem {


    private ArrayList<Mover> movers = new ArrayList<>();
    private Mover sun;


    /**
     * Constructs the system with movers and a central attractor (sun).
     *
     * @param movers the list of {@link Mover} objects.
     * @param sun the central attractor {@link Mover} object.
     */
    public N2mAtraction(ArrayList<Mover> movers, Mover sun) {
        this.movers = movers;
        this.sun = sun;
    }

    /**
     * Constructs the system with movers without a central attractor.
     *
     * @param movers the list of {@link Mover} objects.
     */
    public N2mAtraction(ArrayList<Mover> movers) {
        this.movers = movers;
    }


/**
     * Updates the simulation state by moving and attracting movers.
     * <p>
     * This method overrides the {@link EngineSystem#update(double)} method.
     * Uses {@link ExecutorService} to perform calculations in parallel.
     * Logs the movement of each {@link Mover} using {@link cz.cvut.fel.pjv.model.GLOBALS#log}.
            *
            * @param dT the time delta for the update.
            */
    @Override
    public void update(double dT) {


        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<Void>> futures = new ArrayList<>();


        for (Mover mover : movers) {

            Callable<Void> task = new Callable<Void>() {

                @Override
                public Void call() {
                    log("Thread: " + Thread.currentThread().getName()
                            + "thread id: " + Thread.currentThread().getId() +
                            "Mover_pos: " + mover.getPosComp().toString() + " is moving", Level.OFF);


                    MoveableHandl moveableHandl = mover.currentEntity.getComponent(MoveableHandl.class);
                    if (sun != null) {
                        MoveableHandl sunMoveableHandl = sun.currentEntity.getComponent(MoveableHandl.class);
                        sunMoveableHandl.attract(moveableHandl.getEntity());
                    }
                    for (
                            Mover other : movers) {
                        if (mover != other) {
                            MoveableHandl otherMoveableHandl = other.currentEntity.getComponent(MoveableHandl.class);
                            moveableHandl.attract(otherMoveableHandl.getEntity());
                        }
                    }
                    return null;
                }
            };
            futures.add(executor.submit(task));
        }


        // Ожидаем завершения всех задач
        for (Future<Void> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                log("actualy an error" + e, Level.WARNING );

            }
        }

        executor.shutdown();
    }

    public void setMovers(ArrayList<Mover> movers, Mover sun) {
        this.movers = movers;
        this.sun = sun;
    }
}
