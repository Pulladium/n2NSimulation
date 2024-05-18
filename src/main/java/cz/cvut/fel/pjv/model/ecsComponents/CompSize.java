package cz.cvut.fel.pjv.model.ecsComponents;

import at.fhooe.mtd.ecs.Component;
import com.google.gson.annotations.Expose;

public class CompSize extends Component {
    @Expose
    public double size;

    public CompSize(double size) {
        this.size = size;
    }
}
