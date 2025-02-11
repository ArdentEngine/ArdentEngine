package io.github.ardentengine.core;

import java.util.ArrayList;
import java.util.ServiceLoader;

public class Application implements Runnable {

    private static boolean running = true;

    public static void quit() {
        running = false;
    }

    @Override
    public void run() {
        var engineSystems = new ArrayList<EngineSystem>();
        // Initialization of engine systems
        for (var engineSystem : ServiceLoader.load(EngineSystem.class)) {
            engineSystem.initialize();
            engineSystems.add(engineSystem);
        }
        // Main loop
        while (running) {
            for (var engineSystem : engineSystems) {
                engineSystem.process();
            }
        }
        // Termination of engine systems in reverse order
        for (var i = engineSystems.size() - 1; i >= 0; i--) {
            engineSystems.get(i).terminate();
        }
    }

    public static void main(String[] args) {
        new Application().run();
    }
}
