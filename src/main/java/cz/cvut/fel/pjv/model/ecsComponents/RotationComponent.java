package cz.cvut.fel.pjv.model.ecsComponents;

import at.fhooe.mtd.ecs.Component;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class RotationComponent extends Component {

    public DoubleProperty angle = new SimpleDoubleProperty();

        public RotationComponent(double angle) {
            this.angle.set(angle);
        }

        public double getAngle() {
            return angle.getValue();
        }

        public void setAngle(double angle) {
            if(angle > 360){
                angle = angle - 360;
            }

            this.angle.set(angle);
        }

        //TODO create Interface for Ratationable, obj that have ratationProperty
        public void BindBidirectional(Object obj){
            this.angle.bindBidirectional((DoubleProperty) obj);
        }


    @Override
    public String toString() {
        return "RotationComponent{" +
                "angle=" + angle.get() +
                '}';
    }



}
