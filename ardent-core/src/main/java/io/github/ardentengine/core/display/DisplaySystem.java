package io.github.ardentengine.core.display;

import io.github.ardentengine.core.EngineSystem;

public abstract class DisplaySystem extends EngineSystem {

    private static DisplaySystem instance;

    public static DisplaySystem getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Display System has not been created yet");
        }
        return instance;
    }

    protected DisplaySystem() {
        if (instance != null) {
            throw new IllegalStateException("Display System " + instance + " has already been created");
        }
        instance = this;
    }

    public abstract Window getMainWindow();

    // TODO: Add a 'createWindow' method to add support for multiple windows
}
