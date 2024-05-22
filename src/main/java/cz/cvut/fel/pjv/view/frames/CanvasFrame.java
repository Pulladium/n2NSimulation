package cz.cvut.fel.pjv.view.frames;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * Class for the canvas frame
 * @hidden
 @deprecated

 */
public class CanvasFrame extends Canvas {

    private double screenX = 0;
    private double screenY = 0;


    private double worldX = 100;
    private double worldY = 100;

    public CanvasFrame(double width, double height) {
        super(width, height);
        screenX = width;
        screenY = height;
    }
    public void clear() {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.clearRect(0, 0, this.getWidth(), this.getHeight());
    }


    // Convert world coordinates to screen coordinates
    public Point2D worldToScreen(double wx, double wy) {
        //TODO defualt value
        if(wx - worldX < 0 || wy - worldY < 0){
            return new Point2D(10, 10);
        }
        return  new Point2D(wx / worldX, wy / worldY);
//        return new double[]{wx - worldX, wy - worldY};
    }

    // Method to update the view position based on user input or other criteria
    public void updateViewPosition(double deltaX, double deltaY) {
        this.worldX += deltaX;
        this.worldY += deltaY;
    }




}
