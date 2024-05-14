//package cz.cvut.fel.pjv.view.ecsViewGUI;
//
//import at.fhooe.mtd.ecs.Component;
//import at.fhooe.mtd.ecs.Entity;
//import cz.cvut.fel.pjv.view.frames.WindowFrame;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.VBox;
//
//import java.util.List;
//
//public class UserControl {
//
//    private WindowFrame windowFrame;
//
//    private AnchorPane appPane;
//    private VBox sliderPane;
//    private int controlWidth;
//    private int controlHeight;
//
//    private static UserControl instance;
//    private UserControl() {
//        sliderPane = new VBox();
//
////        sliderPane.setAlignment(javafx.geometry.Pos.CENTER);
//
//        sliderPane.setViewOrder(0.5);
//
//        sliderPane.setStyle("-fx-background-color: #ffff; -fx-border-color: #16be78; -fx-border-width: 1px;");
//
//
//        AnchorPane.setTopAnchor(sliderPane, 0.0);
//        AnchorPane.setRightAnchor(sliderPane, 0.0);
//        AnchorPane.setBottomAnchor(sliderPane, 0.0);
////        AnchorPane.setLeftAnchor(sliderPane, 50.0);
//    }
//    public static UserControl get(){
//        if(UserControl.instance == null){
//            UserControl.instance = new UserControl();
//        }
//        return UserControl.instance;
//    }
//
//
//    public void setWindow(WindowFrame windowFrame) {
//        this.windowFrame = windowFrame;
//        this.appPane = windowFrame.getAppPane();
//        this.appPane.getChildren().add(sliderPane);
//
////
//        controlWidth = windowFrame.getWidth() / 5;
//        controlHeight = windowFrame.getHeight();
//        sliderPane.setMaxWidth(controlWidth);
//        sliderPane.setMinWidth(controlWidth);
//
//        windowFrame.stopLoop();
////        showCompControl
//
//    }
//
//    public void showCompControl(Entity entity){
//        List<Component> compsToShow = entity.getAllComponents(Component.class);
//
//        //for now testing
//        for (Component comp : compsToShow) {
////            if(comp instanceof RotationComponent){
////                System.out.println("RotationComponent found" + comp.toString());
////                UserCntlRotationComp userCntlRotationComp = new UserCntlRotationComp("Rotation", (RotationComponent) comp, sliderPane);
////                userCntlRotationComp.buildUI();
////            }
////            if(comp instanceof VelocityComponent){
////                System.out.println("VelocityComponent found" + comp.toString());
////                UserCtrlVelocityComp userCtrlVelocityComp = new UserCtrlVelocityComp("Velocity", (VelocityComponent) comp, sliderPane);
////                userCtrlVelocityComp.buildUI();
////            }
//
//            if(comp instanceof PositionComponent){
//                System.out.println("PositionComponent found" + comp.toString());
//                UserCtrlPositionComp userCtrlPositionComp = new UserCtrlPositionComp("Position", (PositionComponent) comp, sliderPane);
//                userCtrlPositionComp.buildUI();
//            }
//        }
//
//    }
//
//
//
//}
