package cz.cvut.fel.pjv.model.utils;

import com.google.gson.annotations.Expose;
import cz.cvut.fel.pjv.model.ecsPrepearedObjects.Mover;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

import static cz.cvut.fel.pjv.model.utils.Point2DExt.random2D;
import static cz.cvut.fel.pjv.model.utils.Point2DExt.rotate;
/**
 * Manages the state of the simulation, including the list of movers and the sun.
 * <p>
 * Uses {@link ArrayList} to store {@link Mover} objects and provides methods to create default states and custom states with or without an attractor.
 */
public class SimulationState {
    @Expose
    private ArrayList<Mover> movers;
    @Expose
    private Mover sun;

    /**
     * Generates a random color using {@link Random}.
     *
     * @return a random {@link Color}.
     */
    private Color randomColor() {
        Random random = new Random();
        return Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble());
    }

    /**
     * Constructs a new {@link SimulationState} with the specified movers and sun.
     *
     * @param movers the list of {@link Mover} objects.
     * @param sun the sun {@link Mover} object.
     */

    public SimulationState(ArrayList<Mover> movers, Mover sun) {
        this.movers = movers;
        this.sun = sun;
    }
    /**
     * Constructs a new {@link SimulationState} with the specified movers.
     *
     * @param movers the list of {@link Mover} objects.
     */
    public SimulationState( ArrayList<Mover> movers) {
        this.movers = movers;
    }
    public SimulationState() {
        this.movers = new ArrayList<>();
        this.sun = null;
    }



    public Mover getSun() {
        if (sun != null) {
            return sun;
        }
//        System.out.println("Sun is null");
        return null;
    }
    /**
     * Creates a default simulation state with 100 movers and a sun.
     * <p>
     * Uses {@link Point2DExt#random2D()} and {@link Point2DExt#rotate(Point2D, double)} for position and velocity calculations.
     */
    public void createDefaultState(){
        //
        if(movers == null){
            movers = new ArrayList<>();
        }
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            Point2D pos = random2D().multiply(random.nextDouble() * 50 + 150);
            Point2D vel = pos.normalize().multiply(random.nextDouble() * 5 + 10);
            vel = rotate(vel, Math.PI / 2);
            double m = random.nextDouble() * 5 + 10;
            double size = random.nextDouble() * 10 + 5;



            movers.add(new Mover(pos.getX(), pos.getY(), vel.getX(), vel.getY(), m, size, randomColor()));
        }
        sun = new Mover(0, 0, 0, 0, 500, 20, Color.YELLOW);

    }
    /**
     * Creates a simulation state with the specified number of movers without an attractor.
     * <p>
     * Uses {@link Point2DExt#random2D()} and {@link Point2DExt#rotate(Point2D, double)} for position and velocity calculations.
     *
     * @param n the number of {@link Mover} movers to create.
     */
    public void createNwithoutAtractor(int n){
        if(movers == null){
            movers = new ArrayList<>();
        }
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            Point2D pos = random2D().multiply(random.nextDouble() * 50 + 150);
            Point2D vel = pos.normalize().multiply(random.nextDouble() * 5 + 10);
            vel = rotate(vel, Math.PI / 2);
            double m = random.nextDouble() * 5 + 10;
            double size = random.nextDouble() * 10 + 5;

            movers.add(new Mover(pos.getX(), pos.getY(), vel.getX(), vel.getY(), m, size, randomColor()));
        }
    }
    /**
     * Creates a simulation state with the specified number of movers and an attractor (sun).
     * <p>
     * Uses {@link Point2DExt#random2D()} and {@link Point2DExt#rotate(Point2D, double)} for position and velocity calculations.
     *
     * @param n the number of {@link Mover} movers to create.
     * @param sun the sun {@link Mover} object to act as an attractor.
     */
    public void createNwithAtractor(int n, Mover sun){
        if(movers == null){
            movers = new ArrayList<>();
        }

        Random random = new Random();
        for (int i = 0; i < n; i++) {
            Point2D pos = random2D().multiply(random.nextDouble() * 50 + 150);
            Point2D vel = pos.normalize().multiply(random.nextDouble() * 5 + 10);
            vel = rotate(vel, Math.PI / 2);
            double m = random.nextDouble() * 5 + 10;
            double size = random.nextDouble() * 10 + 5;

            movers.add(new Mover(pos.getX(), pos.getY(), vel.getX(), vel.getY(), m, size, randomColor()));
        }
        this.sun = sun;
    }

    public ArrayList<Mover> getMovers() {
        return movers;
    }

}
