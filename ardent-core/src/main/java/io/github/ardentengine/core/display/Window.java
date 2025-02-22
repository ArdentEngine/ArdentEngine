package io.github.ardentengine.core.display;

public abstract class Window {

    // TODO: Add constructors with size and title

    public abstract void show();

    public abstract void setCursorMode(CursorMode cursorMode);

    public abstract CursorMode getCursorMode();

    public abstract boolean isCloseRequested();

    // TODO: Should this class implement AutoCloseable?
    public abstract void destroy();
}
