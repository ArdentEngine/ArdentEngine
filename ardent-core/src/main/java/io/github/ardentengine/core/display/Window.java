package io.github.ardentengine.core.display;

public abstract class Window {

    public abstract void show();

    public abstract boolean isCloseRequested();

    public abstract void update();

    public abstract void destroy();
}
