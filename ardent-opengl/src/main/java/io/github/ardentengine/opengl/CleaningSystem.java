package io.github.ardentengine.opengl;

import io.github.ardentengine.core.EngineSystem;

import java.lang.ref.Cleaner;
import java.util.LinkedList;
import java.util.Queue;

public class CleaningSystem extends EngineSystem {

    private static final Cleaner CLEANER = Cleaner.create();
    private static final Queue<Runnable> QUEUED_CLEANING_ACTIONS = new LinkedList<>();
    private static final Queue<Runnable> CLEANING_ACTIONS = new LinkedList<>();

    public static void registerAction(Object object, Runnable action) {
        // The actual cleaning action adds the given action to the queue to ensure it is called on the main thread
        CLEANER.register(object, () -> {
            // Queue the cleaning action so that it will be called by 'process'
            QUEUED_CLEANING_ACTIONS.add(action);
            // Remove the action so that it won't be called by 'terminate'
            CLEANING_ACTIONS.remove(action);
        });
        // Add the action to the queue so that it will be called by 'terminate'
        CLEANING_ACTIONS.add(action);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void process() {
        // Use a synchronized block to prevent modification to the queue from the cleaner thread
        synchronized (QUEUED_CLEANING_ACTIONS) {
            // Run queued cleaning actions
            var cleaningAction = QUEUED_CLEANING_ACTIONS.poll();
            while (cleaningAction != null) {
                cleaningAction.run();
                cleaningAction = QUEUED_CLEANING_ACTIONS.poll();
            }
        }
    }

    @Override
    protected void terminate() {
        // Use a synchronized block to prevent modification to the queue from the cleaner thread
        synchronized (CLEANING_ACTIONS) {
            // Run all remaining cleaning actions before terminating
            var cleaningAction = CLEANING_ACTIONS.poll();
            while (cleaningAction != null) {
                cleaningAction.run();
                cleaningAction = CLEANING_ACTIONS.poll();
            }
        }
    }
}
