package cz.cvut.fel.pjv.model.ecsComponents;

import at.fhooe.mtd.ecs.Component;
import at.fhooe.mtd.ecs.Entity;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageComponent extends Component {
    private Entity parent;

    public String path;
    private double viewWidth;//должна быть как у колайдера
    private double viewHeight;


    private Image image;
    private ImageView imageView;

    public DoubleProperty rotationAngle = new SimpleDoubleProperty();
    /**
     * Path should be in format "file:src/main/resources/planet.png"
     * **/
    public ImageComponent(String path, Entity parent) throws Exception{
        this.parent = parent;
        this.path = path;
        if(path == null){
            throw new IllegalArgumentException("ImageComponent: path cannot be null");
        }
        if(!path.contains("file:")){
            throw new IllegalArgumentException("ImageComponent: path should be in format \"file:src/main/resources/planet.png\"");
        }

        this.image = new Image(path);

        this.imageView = new ImageView(image);

        //TODO: now it's default properties
        this.viewWidth = 100;
        this.viewHeight = 100;
        this.imageView.setFitHeight(viewHeight);
        this.imageView.setFitWidth(viewWidth);


        Tooltip imageViewTooltip = new Tooltip("For now this is earth");

        imageViewTooltip.setStyle("-fx-show-delay: 0ms; -fx-hide-delay: 2ms;");

        Tooltip.install(this.imageView, imageViewTooltip);

        this.imageView.rotateProperty().bindBidirectional(this.rotationAngle);






    }

    public void setViewWidth(double viewWidth) {
        this.viewWidth = viewWidth;
        this.imageView.setFitWidth(viewWidth);
    }
    public void setViewHeight(double viewHeight) {
        this.viewHeight = viewHeight;
        this.imageView.setFitHeight(viewHeight);
    }


    public void bindPosition(PositionComponent positionComponent){

        if(this.imageView.fitHeightProperty().get() == 0 || this.imageView.fitWidthProperty().get() == 0){
            throw new IllegalArgumentException("ImageComponent: fitHeight and fitWidth should be set before binding position");
        }

        System.out.println("Binding position");
        System.out.println("ImageComponent: Height" + this.imageView.fitHeightProperty().get() + " Width" + this.imageView.fitWidthProperty().get());
        System.out.println("ImageComponent x: " + this.imageView.xProperty().get() + " " + this.imageView.yProperty().get());

        System.out.println("PositionComponent: " + positionComponent.vector2D.xProperty().get() + " " + positionComponent.vector2D.yProperty().get());

        this.imageView.xProperty().bind(positionComponent.vector2D.xProperty().subtract(this.imageView.fitWidthProperty().divide(2)));
        this.imageView.yProperty().bind(positionComponent.vector2D.yProperty().subtract(this.imageView.fitHeightProperty().divide(2)));

    }
    public void unbindPosition(){
        this.imageView.xProperty().unbind();
        this.imageView.yProperty().unbind();
    }

    public void bindRotation(RotationComponent rotationComponent){
        this.imageView.rotateProperty().bind(rotationComponent.angle);
    }
    public void unbindRotation(){
        this.imageView.rotateProperty().unbind();
    }




    public ImageView getImageView() {
        return imageView;
    }



}
