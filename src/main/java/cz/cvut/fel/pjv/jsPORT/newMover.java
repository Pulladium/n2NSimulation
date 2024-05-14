package cz.cvut.fel.pjv.jsPORT;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Circle;

public class newMover {
    Point2DExt position;
    Point2DExt velocity;
    Point2DExt acceleration;
    double mass;
    double radius;


    public newMover(double posX, double posY, double velX, double velY, double mass) {
        this.position = new Point2DExt(posX, posY);
        this.velocity = new Point2DExt(velX, velY);
        this.acceleration = new Point2DExt(0, 0);
        this.mass = mass;
        this.radius = Math.sqrt(mass) * 2;
    }
    public newMover(Point2D position, Point2D velocity, Point2D acceleration, double mass, double radius) {
        this.position = new Point2DExt(position);
        this.velocity = new Point2DExt(velocity);
        this.acceleration = new Point2DExt(acceleration);
        this.mass = mass;
        this.radius = radius;
    }


    public void applyForce(Point2D force) {
        Point2D f = force.multiply(1 / mass);
        acceleration = new Point2DExt(acceleration.add(f));
    }

    public void attract(newMover other){

        Point2D force = position.subtract(other.position);
        double distanceSq =Point2DExt.constrain(force.magnitude(), 100, 1000);

        double G= 5;
        double strength = (G * (mass * other.mass)) / distanceSq;

        //force.setMag(strength);
        force = force.normalize().multiply(strength);
        other.applyForce(force);
    }

    public void update() {
        velocity = new Point2DExt(velocity.add(acceleration));
        position = new Point2DExt(position.add(velocity));
        acceleration = new Point2DExt(0, 0);
    }

    //rundom javafx.scene.paint.Color
    private javafx.scene.paint.Color getRandomColor(){
        return javafx.scene.paint.Color.rgb((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
    }
    public void show(GraphicsContext gc){


        gc.setFill(getRandomColor());
        gc.fillOval(position.getX(), position.getY(), radius, radius);

    }

}

