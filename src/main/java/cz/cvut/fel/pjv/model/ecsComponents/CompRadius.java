package cz.cvut.fel.pjv.model.ecsComponents;

import at.fhooe.mtd.ecs.Component;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class CompRadius extends Component {
    public DoubleProperty radius = new SimpleDoubleProperty();

    public CompRadius(double radius) {
        this.radius.setValue(radius);
    }
}
