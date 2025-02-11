package io.github.ardentengine.core;

import java.util.ServiceLoader;

public class Application implements Runnable {

    private static boolean running = true;

    public static void quit() {
        running = false;
    }

    @Override
    public void run() {
        var engineSystems = ServiceLoader.load(EngineSystem.class);
        for (var engineSystem : engineSystems) {
            engineSystem.initialize();
        }
        while (running) {
            for (var engineSystem : engineSystems) {
                engineSystem.process();
            }
        }
        // TODO: Terminate should happen in reverse order
        for (var engineSystem : engineSystems) {
            engineSystem.terminate();
        }
    }

    public static void main(String[] args) {
        new Application().run();
    }
}
