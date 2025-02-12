package cz.cvut.fel.pjv.model.ecsComponents;

import at.fhooe.mtd.ecs.Component;
import com.google.gson.annotations.Expose;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Represents a mass {@link Component} in the ECS (Entity Component System) framework.
 * <p>
 * Contains a mass property.
 */
public class MassComponent extends Component {
    @Expose
    public DoubleProperty mass = new SimpleDoubleProperty();

    public MassComponent(double mass) {
        this.mass.set(mass);
    }


    @Override
    public String toString() {
        return "MassComponent{" +
                "mass=" + mass.get() +
                '}';
    }
}
