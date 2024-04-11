package cz.cvut.fel.pjv.model.ecsComponents;

import at.fhooe.mtd.ecs.Component;
import cz.cvut.fel.pjv.model.ecsComponents.myUtils.Vector2D;

public class PositionComponent extends Component {
    public Vector2D vector2D = new Vector2D();

    public PositionComponent(double x, double y) throws Exception{

        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("PositionComponent: x and y must be non-negative");
        }


        this.vector2D.xProperty().set(x);
        this.vector2D.yProperty().set(y);

    }

    @Override
    public String toString() {
        return "PositionComponent{" +
                "position2D=" + vector2D.toString() +
                '}';
    }
}
