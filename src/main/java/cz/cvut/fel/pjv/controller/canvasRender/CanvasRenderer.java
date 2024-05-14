//package cz.cvut.fel.pjv.controller.canvasRender;
//
//import at.fhooe.mtd.ecs.Engine;
//import at.fhooe.mtd.ecs.Entity;
//import at.fhooe.mtd.ecs.EntityFamily;
//import cz.cvut.fel.pjv.model.ecsComponents.*;
//import cz.cvut.fel.pjv.view.frames.CanvasFrame;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//
//import java.util.List;
//
//public class CanvasRenderer {
//
//    CanvasFrame canvasFrame;
//    Engine engine;
//    EntityFamily movableEntitiesFamily;
//
//    public CanvasRenderer(CanvasFrame canvasFrame, Engine engine) {
//        this.canvasFrame = canvasFrame;
//        this.engine = engine;
//
//        movableEntitiesFamily = EntityFamily.create(PositionComponent.class, VelocityComponent.class, RotationComponent.class, MassComponent.class);
//
//    }
//
//
//    //TODO Render only the visible parts of the world
//    public void render() {
//        canvasFrame.clear();
//        List<Entity> entities = engine.getEntities(movableEntitiesFamily);
//
//
//
//        GraphicsContext gc = canvasFrame.getGraphicsContext2D();
//        gc.clearRect(0, 0, canvasFrame.getWidth(), canvasFrame.getHeight());
//        gc.fillRect(0, 0, canvasFrame.getWidth(), canvasFrame.getHeight());
//
//        for(Entity entity: entities) {
//            ImageView imageView = entity.getComponent(ImageComponent.class).getImageView();
//            PositionComponent position = entity.getComponent(PositionComponent.class);
//            //обн имдж
//            Image image = imageView.getImage();
//            drawRotatedImage(gc, image, entity.getComponent(RotationComponent.class).getAngle(), position.vector2D.xProperty().get(), position.vector2D.yProperty().get());
//        }
//        // Example of rendering a world object (you would iterate over all visible objects)
////        double[] screenCoords = canvasFrame.worldToScreen(100, 100); // Example world position
////        gc.fillOval(screenCoords[0], screenCoords[1], 10, 10); // Render at screen position
//    }
//    public void drawRotatedImage(GraphicsContext gc, Image image, double angle, double x, double y) {
//
////        https://stackoverflow.com/questions/18260421/how-to-draw-image-rotated-on-javafx-canvas
//
//        Vector2D imageWHVec2d = new Vector2D();
//
//        imageWHVec2d = canvasFrame.worldToScreen(image.getWidth(), image.getHeight());
////        double imageHeight = image.getHeight();
//
//        // Save the current state of the graphics context.
//        gc.save();
//
//        // Translate to the rotation pivot point (center of the image)
////        gc.translate(x + imageWidth / 2, y + imageHeight / 2);
//        gc.translate(x + imageWHVec2d.xProperty().get() / 2, y + imageWHVec2d.yProperty().get() / 2);
//        // Rotate the context.
//        gc.rotate(angle);
//
//        // Translate back and draw the image
//        // The -imageWidth / 2 and -imageHeight / 2 adjustments are necessary because
//        // the rotation is around (0,0), and we want to rotate around the center of the image.
////        gc.drawImage(image, -imageWidth / 2, -imageHeight / 2, 50, 50);
//        gc.drawImage(image, -imageWHVec2d.xProperty().get() / 2, -imageWHVec2d.yProperty().get() / 2, imageWHVec2d.xProperty().get(), imageWHVec2d.yProperty().get());
//
//
//        // Restore the graphics context to its original state.
//        gc.restore();
//    }
//
//}
