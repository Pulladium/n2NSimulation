//package cz.cvut.fel.pjv.model.ecsPrepearedObjects;
//
//import at.fhooe.mtd.ecs.Entity;
//import cz.cvut.fel.pjv.model.ecsComponents.*;
//
//public class SelestrialBody {
//
//    //ili net
//    private final Entity currentEntity;
//
//
//    public SelestrialBody() throws Exception {
//        currentEntity = new Entity();
//
//
//
//        ImageComponent imageComponent  = new ImageComponent("file:src/main/resources/Earth.png", currentEntity);
//        //comp creation
//        currentEntity.addComponent(new PositionComponent(50, 50));
//        currentEntity.addComponent(new MassComponent(9));
//        currentEntity.addComponent(new VelocityComponent(10, 4));
//        currentEntity.addComponent(new RotationComponent(2));
//        currentEntity.addComponent(imageComponent);
//
//
//
//
//
////        imageComponent.bindPosition(currentEntity.getComponent(PositionComponent.class));
//        imageComponent.bindRotation(currentEntity.getComponent(RotationComponent.class));
//
//
//    }
//
//    public Entity getEntity(){
//        return currentEntity;
//    }
//
//
//    @Override
//    public String toString() {
//        PositionComponent pos = currentEntity.getComponent(PositionComponent.class);
//        RotationComponent rot = currentEntity.getComponent(RotationComponent.class);
//        ImageComponent img = currentEntity.getComponent(ImageComponent.class);
//        MassComponent mass = currentEntity.getComponent(MassComponent.class);
//        VelocityComponent vel = currentEntity.getComponent(VelocityComponent.class);
//
//        return "SelestrialBody{" +
//                ", pos=" + pos.toString() +
//                ", rot=" + rot.toString() +
//                ", img=" + img.toString() +
//                ", mass=" + mass.toString() +
//                ", vel=" + vel.toString() +
//                '}';
//    }
//}
