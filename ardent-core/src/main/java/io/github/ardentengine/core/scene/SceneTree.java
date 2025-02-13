package io.github.ardentengine.core.scene;

import io.github.ardentengine.core.EngineSystem;
import io.github.ardentengine.core.resources.ResourceManager;
import io.github.ardentengine.core.resources.Texture;

public class SceneTree extends EngineSystem {

    private Node root = null;

    private long previousTime = 0;

    // TODO: Add methods to change the scene here

    @Override
    protected void initialize() {
        this.previousTime = System.nanoTime();
        // TODO: Load the first scene here
        this.root = new Node();
        var sprite = new Sprite2D();
        sprite.setTexture((Texture) ResourceManager.getOrLoad("tile_0010.png"));
        var sprite2 = new Sprite2D();
        sprite2.setPosition(5.0f, 5.0f);
        sprite2.setTexture(sprite.texture());
        this.root.addChild(sprite);
        this.root.addChild(sprite2);
        var camera = new Camera2D();
        camera.setZoom(5.0f, 5.0f);
        this.root.addChild(camera);
        if (this.root != null) {
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
