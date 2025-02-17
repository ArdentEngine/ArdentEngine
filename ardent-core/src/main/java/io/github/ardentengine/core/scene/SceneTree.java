package io.github.ardentengine.core.scene;

import io.github.ardentengine.core.ApplicationProperties;
import io.github.ardentengine.core.EngineSystem;
import io.github.ardentengine.core.resources.ResourceManager;
import io.github.ardentengine.core.resources.SceneData;

public class SceneTree extends EngineSystem {

    private Node root = null;

    private long previousTime = 0;

    // TODO: Add methods to change the scene here

    @Override
    protected void initialize() {
        this.previousTime = System.nanoTime();
        var firstScene = (String) ApplicationProperties.get("application.run.firstScene");
        if (firstScene != null) {
            var scene = (SceneData) ResourceManager.getOrLoad(firstScene);
            this.root = scene.instantiate();
            this.root.enterScene(this);
        }
    }

    public final Node root() {
        return this.root;
    }

    @Override
    protected void process() {
        var time = System.nanoTime();
        if (this.root != null) {
            this.root.update((time - this.previousTime) / 1_000_000_000.0f);
        }
        this.previousTime = time;
    }

    @Override
    protected void terminate() {
        if (this.root != null) {
            this.root.exitScene();
        }
    }
}
