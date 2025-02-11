package io.github.ardentengine.core.display;

public abstract class Window {

    // TODO: Add constructors with size and title

    public abstract void show();

    public abstract boolean isCloseRequested();

    public abstract void update();

    public abstract void destroy();
}
