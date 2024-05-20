package cz.cvut.fel.pjv.model;

import com.google.gson.annotations.Expose;
import cz.cvut.fel.pjv.jsPORT.Mover;
import cz.cvut.fel.pjv.model.ecsSystems.N2mAtraction;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

import static cz.cvut.fel.pjv.model.ecsComponents.myUtils.Point2DExt.random2D;
import static cz.cvut.fel.pjv.model.ecsComponents.myUtils.Point2DExt.rotate;

public class SimulationState {
    @Expose
    private ArrayList<Mover> movers;
    @Expose
    private Mover sun;

    private Color randomColor() {
        Random random = new Random();
        return Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble());
    }

    public SimulationState(ArrayList<Mover> movers, Mover sun) {
        this.movers = movers;
        this.sun = sun;
    }
    public SimulationState( ArrayList<Mover> movers) {
        this.movers = movers;
    }
    public SimulationState() {
        this.movers = new ArrayList<>();
        this.sun = null;
    }


    public ArrayList<Mover> getMovers() {
        return movers;
    }
    public Mover getSun() {
        if (sun != null) {
            return sun;
        }
//        System.out.println("Sun is null");
        return null;
    }

    public void createDefaultState(){
        //
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

    public void createNwithoutAtractor(int n){
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
    public void createNwithAtractor(int n, Mover sun){
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

}
