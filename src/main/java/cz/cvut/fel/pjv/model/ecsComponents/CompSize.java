package cz.cvut.fel.pjv.model.ecsComponents;

import at.fhooe.mtd.ecs.Component;
import com.google.gson.annotations.Expose;

/**
 * Represents a size {@link Component} in the ECS (Entity Component System) framework.
 * <p>
 * Contains a size property.
 */
public class CompSize extends Component {
    @Expose
    public double size;

    public CompSize(double size) {
        this.size = size;
    }
}
