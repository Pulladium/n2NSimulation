package cz.cvut.fel.pjv.model.ecsSystems;

import at.fhooe.mtd.ecs.EngineSystem;
import cz.cvut.fel.pjv.jsPORT.Mover;
import cz.cvut.fel.pjv.model.ecsComponents.MoveableHandl;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class N2mAtraction extends EngineSystem {


    private ArrayList<Mover> movers = new ArrayList<>();
    private Mover sun;


    //with atractor in the middle
    public N2mAtraction(ArrayList<Mover> movers, Mover sun) {
        this.movers = movers;
        this.sun = sun;
    }

    //without atractor in the middle
    public N2mAtraction(ArrayList<Mover> movers) {
        this.movers = movers;
    }

    public void setMovers(ArrayList<Mover> movers, Mover sun) {
        this.movers = movers;
        this.sun = sun;
    }

    @Override
    public void update(double dT) {

        // Создаем ExecutorService с фиксированным пулом потоков, размер которого равен количеству доступных процессоров
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<Void>> futures = new ArrayList<>();


        for (Mover mover : movers) {

            // Создаем задачу, которая будет выполняться в отдельном потоке
            Callable<Void> task = new Callable<Void>() {

                @Override
                public Void call() {
/*
                    System.out.println("Thread: " + Thread.currentThread().getName()
                            + "thread id: " + Thread.currentThread().getId() +
                            "Mover_pos: " + mover.getPosComp().toString() + " is moving"  );
                  */
                    MoveableHandl moveableHandl = mover.currentEntity.getComponent(MoveableHandl.class);
                    if (sun != null) {
                        MoveableHandl sunMoveableHandl = sun.currentEntity.getComponent(MoveableHandl.class);
                        sunMoveableHandl.attract(moveableHandl.getEntity());
                    }
                    for (
                            Mover other : movers) {
                        if (mover != other) {
                            MoveableHandl otherMoveableHandl = other.currentEntity.getComponent(MoveableHandl.class);
                            moveableHandl.attract(otherMoveableHandl.getEntity());
                        }
                    }
                    return null;
                }
            };

            // Добавляем задачу в список задач, которые будут выполнены в потоках
            futures.add(executor.submit(task));
        }


        // Ожидаем завершения всех задач
        for (Future<Void> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                System.out.println("Not actualy an error" + e);
//                e.printStackTrace();
            }
        }

        // Завершаем работу ExecutorService
        executor.shutdown();
        //новый цикл update
    }

}
