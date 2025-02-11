package io.github.ardentengine.core;

public abstract class EngineSystem {

    protected abstract void initialize();

    protected abstract void process();

    protected abstract void terminate();
}
