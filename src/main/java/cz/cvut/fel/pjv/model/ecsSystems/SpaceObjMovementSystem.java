//package cz.cvut.fel.pjv.model.ecsSystems;
//
//import at.fhooe.mtd.ecs.EngineSystem;
//import at.fhooe.mtd.ecs.Entity;
//import at.fhooe.mtd.ecs.EntityFamily;
//
//import java.util.List;
//import java.util.logging.Logger;
//public class SpaceObjMovementSystem extends EngineSystem {
//    private static final Logger LOGGER = Logger.getLogger(SpaceObjMovementSystem.class.getName());
//    private EntityFamily movableEntitiesFamily;
//
//
//    public SpaceObjMovementSystem() {
//        //  определить, какие сущности будет обрабатывать  система
//        // EntityFamily для сущностей, имеющих компоненты Position и Velocity
//
//        movableEntitiesFamily = EntityFamily.create(PositionComponent.class, VelocityComponent.class, RotationComponent.class);
//    }
//
//    @Override
//    public void update(double dt) {
//        List<Entity> entities = getEngine().getEntities(movableEntitiesFamily);
//        for (Entity entity : entities) {
//            PositionComponent position = entity.getComponent(PositionComponent.class);
//            VelocityComponent velocity = entity.getComponent(VelocityComponent.class);
//            RotationComponent rotation = entity.getComponent(RotationComponent.class);
//
//            LOGGER.info("Updating position of entity oldX = "  + position.vector2D.xProperty().get() + " oldY = " + position.vector2D.yProperty().get() + " oldAngle = " + rotation.getAngle());
//
//            // Обновление позиции на основе скорости и прошедшего времени
////            position.setX(position.getX() + velocity.getVelocity().xProperty().get() * dt);
////            position.setY(position.getY() + velocity.getVelocity().yProperty().get() * dt);
//            position.vector2D.xProperty().set(position.vector2D.xProperty().get() + velocity.getVelocity().xProperty().get() * dt);
//            position.vector2D.yProperty().set(position.vector2D.yProperty().get() + velocity.getVelocity().yProperty().get() * dt);
//
//
//            // Обновление угла поворота на основе скорости вращения и прошедшего времени
//            //TODO add noraml rotation speed change
//            rotation.setAngle(rotation.getAngle() + velocity.getVelocity().xProperty().get() * dt);
//
//
//
//
//
//
//            LOGGER.info("Updated position of entity newX = "  + position.vector2D.xProperty().get() + " newY = " + position.vector2D.yProperty().get() + " newAngle = " + rotation.getAngle());
//
//        }
//    }
//
//}
