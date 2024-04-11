package cz.cvut.fel.pjv.model.ecsComponents.myUtils;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Vector2D {
    private DoubleProperty x = new SimpleDoubleProperty();
    private DoubleProperty y = new SimpleDoubleProperty();

    public Vector2D(double x, double y) {
        this.x.set(x);
        this.y.set(y);
    }
    public Vector2D() {
        this.x.set(0);
        this.y.set(0);
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x.get() + other.x.get(), this.y.get() + other.y.get());
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x.get() - other.x.get(), this.y.get()
                - other.y.get());
    }

    public Vector2D multiply(double scalar) {
        return new Vector2D(this.x.get() * scalar, this.y.get() * scalar);
    }

    public double magnitude() {
//        return Math.sqrt(x * x + y * y);
        return Math.sqrt(x.get() * x.get() * y.get()* y.get());
    }

    public Vector2D normalize() {
        double mag = magnitude();
        return new Vector2D(x.get() / mag, y.get() / mag);
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public DoubleProperty yProperty() {
        return y;
    }


    @Override
    public String toString() {
        return "Vector2D{" +
                "x=" + x.get() +
                ", y=" + y.get() +
                '}';
    }
}
