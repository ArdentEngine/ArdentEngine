package io.github.ardentengine.opengl;

import java.lang.ref.Cleaner;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class ResourceCleaner {

    private static final Cleaner CLEANER = Cleaner.create();
    private static final Set<Runnable> QUEUED_CLEANING_ACTIONS = Collections.synchronizedSet(new HashSet<>());
    private static final Set<Runnable> CLEANING_ACTIONS = Collections.synchronizedSet(new HashSet<>());

    public static synchronized void runCleaner() {
        QUEUED_CLEANING_ACTIONS.forEach(Runnable::run);
        QUEUED_CLEANING_ACTIONS.clear();
    }

    public static void registerAction(Object object, Runnable action) {
        // The actual cleaning action adds the given action to the set to ensure it is called on the main thread
        CLEANER.register(object, () -> {
            // Queue the cleaning action so that it will be called by 'runCleaner'
            QUEUED_CLEANING_ACTIONS.add(action);
            // Remove the action so that it won't be called by 'cleanRemaining'
            CLEANING_ACTIONS.remove(action);
        });
        // Add the action to the set so that it will be called by 'cleanRemaining'
        CLEANING_ACTIONS.add(action);
    }

    public static synchronized void cleanRemaining() {
        CLEANING_ACTIONS.forEach(Runnable::run);
        CLEANING_ACTIONS.clear();
    }

    private ResourceCleaner() {
        // Prevent instantiation of static utility class
    }
}
