package cz.cvut.fel.pjv.model.ecsSystems;

import at.fhooe.mtd.ecs.EngineSystem;
import at.fhooe.mtd.ecs.Entity;
import at.fhooe.mtd.ecs.EntityFamily;
import cz.cvut.fel.pjv.model.ecsComponents.MassComponent;
import cz.cvut.fel.pjv.model.ecsComponents.PositionComponent;
import cz.cvut.fel.pjv.model.ecsComponents.RotationComponent;
import cz.cvut.fel.pjv.model.ecsComponents.VelocityComponent;
import cz.cvut.fel.pjv.model.ecsComponents.myUtils.Vector2D;
import cz.cvut.fel.pjv.view.frames.WindowFrame;

import java.util.List;

/**
 * System for Runge-Kutta 4th order method.
 */
public class RK4System extends EngineSystem {


    private final WindowFrame window = WindowFrame.get();
    private EntityFamily movableEntitiesFamily;


    public RK4System() {
        //  определить, какие сущности будет обрабатывать  система
        // EntityFamily для сущностей, имеющих компоненты Position и Velocity
        //TODO Acceleration component for now is not used
        movableEntitiesFamily = EntityFamily.create(PositionComponent.class, VelocityComponent.class, RotationComponent.class, MassComponent.class);
    }
    @Override
    public void update(double dt) {
        List<Entity> entities = getEngine().getEntities(movableEntitiesFamily);
        for (Entity entity : entities) {
            PositionComponent position = entity.getComponent(PositionComponent.class);
            VelocityComponent velocity = entity.getComponent(VelocityComponent.class);
            RotationComponent rotation = entity.getComponent(RotationComponent.class);
            MassComponent mass = entity.getComponent(MassComponent.class);


//            System.out.println("Old Position: " + position.position2D.toString() + "ImgPos: " + imageView.getX() + " " + imageView.getY());
//            TODO Treads (((((
            CelestrialBodyRK4(entity, entities, dt);

//            System.out.println("New Position: " + position.position2D.toString() + "ImgPos: " + imageView.getX() + " " + imageView.getY());

            // Обновление угла поворота на основе скорости вращения и прошедшего времени
            //TODO add noraml rotation speed change
            rotation.setAngle(rotation.getAngle() + velocity.getVelocity().xProperty().get() * dt);

            VelocityComponent velocityComponent = entity.getComponent(VelocityComponent.class);
            Vector2D vel = velocityComponent.getVelocity() ;
            System.out.println("VEL X: " + vel.xProperty() + "\n Y " + vel.yProperty());
        }
    }
    /**
     * Calculates the acceleration of the entity.
     *
     * @param entity the entity for which the acceleration is calculated
     * @param entities the list of all entities in the world
     * @return the acceleration of the entity
     */
    public Vector2D calculateAcceleration(Entity entity, List<Entity> entities, Vector2D position, Vector2D velocity){

//        final double G = 6.67430e-11; // gravitational constant
        final double G = 1;
        Vector2D totalAcceleration = new Vector2D(0, 0);

        MassComponent massComponent = entity.getComponent(MassComponent.class);
//        PositionComponent positionComponent = entity.getComponent(PositionComponent.class);

        double force = 1.0;
        for(Entity e: entities){
            if(e != entity){
                MassComponent itersMassComponent = e.getComponent(MassComponent.class);
                PositionComponent itersPositionComponent = e.getComponent(PositionComponent.class);

                Vector2D direction = itersPositionComponent.vector2D.subtract(position);

//                double distanceSqr = direction.xProperty().get() * direction.xProperty().get() + direction.yProperty().get() * direction.yProperty().get();
            //for now mag2 better opimization
                double distanceSqr = direction.magnitude() * direction.magnitude();

                if(distanceSqr > 0) {
                    //TOD MASS SHOULD REALY AFFECT done
                    force = (G * massComponent.mass.get()  * itersMassComponent.mass.get() / distanceSqr) *1000;
                    Vector2D acceleration = direction.normalize().multiply(force / massComponent.mass.get());
                    totalAcceleration = totalAcceleration.add(acceleration);
                }
                else {
//                    throw new Exception("Division by zero remember to prevent");
                    System.out.println("Division by zero remember to prevent");
                }
//                System.out.println("direction =" + direction.xProperty().get()+ );
                //write direction Vector2d
//                System.out.println("direct X: " + direction.xProperty().get() + " Y: " + direction.yProperty().get());


            }
        }
        return totalAcceleration;

    }




    //vrode choroso
    public void CelestrialBodyRK4(Entity currEntity, List<Entity> entitiesAll, double dt) {
        VelocityComponent velComp = currEntity.getComponent(VelocityComponent.class);
        PositionComponent posComp = currEntity.getComponent(PositionComponent.class);

        Vector2D initialPos = posComp.vector2D;
        Vector2D initialVel = velComp.getVelocity();

        // k1 Шаг
        Vector2D k1acc = calculateAcceleration(currEntity, entitiesAll, initialPos, initialVel);
        Vector2D k1vel = initialVel;

        // k2 Шаг: используем положение и скорость, вычисленные с использованием k1
        Vector2D k2pos = initialPos.add(k1vel.multiply(dt / 2.0));
        Vector2D k2vel = initialVel.add(k1acc.multiply(dt / 2.0));
        Vector2D k2acc = calculateAcceleration(currEntity, entitiesAll, k2pos, k2vel);

        // k3 Шаг
        Vector2D k3pos = initialPos.add(k2vel.multiply(dt / 2.0));
        Vector2D k3vel = initialVel.add(k2acc.multiply(dt / 2.0));
        Vector2D k3acc = calculateAcceleration(currEntity, entitiesAll, k3pos, k3vel);

        // k4 Шаг
        Vector2D k4pos = initialPos.add(k3vel.multiply(dt));
        Vector2D k4vel = initialVel.add(k3acc.multiply(dt));
        Vector2D k4acc = calculateAcceleration(currEntity,  entitiesAll, k4pos, k4vel);

        // Интеграция
        Vector2D finalVel = initialVel.add(k1acc.add(k2acc.multiply(2)).add(k3acc.multiply(2)).add(k4acc).multiply(dt / 6.0));
        Vector2D finalPos = initialPos.add(k1vel.add(k2vel.multiply(2)).add(k3vel.multiply(2)).add(k4vel).multiply(dt / 6.0));

        velComp.setVelocity(finalVel);
        posComp.vector2D = finalPos;

        //обн имдж
//        ImageComponent img = currEntity.getComponent(ImageComponent.class);
//        ImageView imageView = img.getImageView();
//        Image image = imageView.getImage();
//
//        Canvas gameCanvas = window.getGameLayoutCanvas();


//        return PositionComponent

//        img.getImageView().setX(posComp.vector2D.xProperty().get());
//        img.getImageView().setY(posComp.vector2D.yProperty().get());

        //uzass
//        pos.position2D = pos.position2D.add(k1vel.add(k2vel.multiply(2)).add(k3vel.multiply(2)).add(k4vel).multiply(dt / 6));
//        ImageComponent img = currEntity.getComponent(ImageComponent.class);
//        img.getImageView().setX(pos.position2D.xProperty().get());
//        img.getImageView().setY(pos.position2D.yProperty().get());



    }
}
