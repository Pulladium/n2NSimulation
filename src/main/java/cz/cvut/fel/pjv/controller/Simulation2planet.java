//package cz.cvut.fel.pjv.controller;
//
//
//import at.fhooe.mtd.ecs.Engine;
//import at.fhooe.mtd.ecs.Entity;
//import cz.cvut.fel.pjv.model.ecsComponents.MassComponent;
//import cz.cvut.fel.pjv.model.ecsPrepearedObjects.SelestrialBody;
//import cz.cvut.fel.pjv.model.ecsSystems.RK4System;
////import cz.cvut.fel.pjv.view.ecsViewGUI.UserControl;
//import cz.cvut.fel.pjv.view.frames.WindowFrame;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//
//
//public class Simulation2planet {
//
//    private final UserControl gui;
//    private final WindowFrame window;
//    private final Engine engine;
//
//    public Simulation2planet(Engine engine, WindowFrame window, UserControl gui){
//        this.engine = engine;
//        this.window = window;
//        this.gui = gui;
//    }
//
//    public void runRK4() throws Exception {
//
//        // Инициализация GameObj with сущностb
//        SelestrialBody newEarth = new SelestrialBody();
//        SelestrialBody newMoon = new SelestrialBody();
//
//        Entity earth = newEarth.getEntity();
//        Entity moon = newMoon.getEntity();
//
//        PositionComponent moonPos = moon.getComponent(PositionComponent.class);
//
//        moonPos.vector2D.xProperty().set(500);
//        moonPos.vector2D.yProperty().set(500);
//
//
//        System.out.println(newEarth.toString()  );
//        System.out.println(newMoon.toString()  );
//
//        // Добавление сущности в движок
//        engine.addEntity(earth);
//        engine.addEntity(moon);
//
//        ImageView moonImgView = moon.getComponent(ImageComponent.class).getImageView();
//        moonImgView.setFitWidth(20);
//        moonImgView.setFitHeight(20);
//
//        VelocityComponent moonVel = moon.getComponent(VelocityComponent.class);
//
//        moonVel.getVelocity().xProperty().set(-5);
//        moonVel.getVelocity().yProperty().set(-1);
//
//        MassComponent moonMass = moon.getComponent(MassComponent.class);
//        moonMass.mass.set(100000);
//
//
//        //Space Obj Movement System on engine update
//        engine.addSystem(new RK4System());
//
//        // Добавление сущности в окно
////        window.getGameLayoutPane().getChildren().add(earth.getComponent(ImageComponent.class).getImageView());
//        Canvas canvas = window.getGameLayoutCanvas();
//
//        GraphicsContext gc = canvas.getGraphicsContext2D();
//
//        // render сущности в Canvas
//
//        ImageView earthImgView = earth.getComponent(ImageComponent.class).getImageView();
//        Image earthImg = earthImgView.getImage();
//        gc.drawImage(earthImg, 200, 200, 100, 100);
//
////        gc.drawImage();
//
////        window.getGameLayoutPane().getChildren().add(moon.getComponent(ImageComponent.class).getImageView());
//
//    }
//
//
//
//
//}
