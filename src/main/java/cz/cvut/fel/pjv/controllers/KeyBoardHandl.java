package cz.cvut.fel.pjv.controllers;

import javafx.scene.input.KeyCode;

import java.util.Objects;
import java.util.logging.Level;

import static cz.cvut.fel.pjv.model.GLOBALS.log;
import static cz.cvut.fel.pjv.model.GLOBALS.windowFrame;

public class KeyBoardHandl {

    public void AddHandler(SimpleAtraction simpleAtraction){
        windowFrame.getCurrentScene().setOnKeyPressed(keyEvent ->
        {
            log("Key pressed: " + keyEvent.getCode(), Level.INFO);

            if (Objects.requireNonNull(keyEvent.getCode()) == KeyCode.P) {
                if (windowFrame.isRunning())
                    windowFrame.pause();
                else
                    windowFrame.resume();
            }
            else if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.S) {
                if(!windowFrame.isRunning())
                    simpleAtraction.saveSimState();
            }
            else if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.L) {
                if(!windowFrame.isRunning())
                    simpleAtraction.loadSimState();
            }
            else if ( keyEvent.isControlDown()) {
                if ( keyEvent.getCode() == KeyCode.PLUS ||  keyEvent.getCode() == KeyCode.EQUALS) {
                    windowFrame.setCanvasScale(windowFrame.getCanvasScale()* 1.1);
                    simpleAtraction.draw(windowFrame.getGameLayoutCanvas().getGraphicsContext2D());
                } else if ( keyEvent.getCode() == KeyCode.MINUS) {
//                    scale /= 1.1;
                    windowFrame.setCanvasScale(windowFrame.getCanvasScale() / 1.1);
                    simpleAtraction.draw(windowFrame.getGameLayoutCanvas().getGraphicsContext2D());
                }
            }
            else {
                switch (keyEvent.getCode()) {
                    case W:
                        windowFrame.offsetY -= 5;
                        simpleAtraction.redraw(windowFrame.getGameLayoutCanvas().getGraphicsContext2D());
                        break;
                    case S:
                        windowFrame.offsetY += 5;
                        simpleAtraction.redraw(windowFrame.getGameLayoutCanvas().getGraphicsContext2D());
                        break;
                    case A:
                        windowFrame.offsetX -= 5;
                        simpleAtraction.redraw(windowFrame.getGameLayoutCanvas().getGraphicsContext2D());
                        break;
                    case D:
                        windowFrame.offsetX += 5;
                        simpleAtraction.redraw(windowFrame.getGameLayoutCanvas().getGraphicsContext2D());
                        break;

                }

            }
        });
    }
}
