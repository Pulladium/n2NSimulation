package cz.cvut.fel.pjv.model.ecsComponents;

import at.fhooe.mtd.ecs.Component;
import com.google.gson.annotations.Expose;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class CompRadius extends Component {
    @Expose
    public DoubleProperty radius = new SimpleDoubleProperty();

    public CompRadius(double radius) {
        this.radius.setValue(radius);
    }
}
