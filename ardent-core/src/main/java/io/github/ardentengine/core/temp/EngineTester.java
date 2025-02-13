package io.github.ardentengine.core.temp;

import io.github.ardentengine.core.EngineSystem;
import io.github.ardentengine.core.math.Matrix2x3;
import io.github.ardentengine.core.resources.ResourceManager;
import io.github.ardentengine.core.resources.Texture;

public class EngineTester extends EngineSystem {

    private final Texture texture = (Texture) ResourceManager.getOrLoad("tile_0010.png");

    @Override
    protected void initialize() {

    }

    @Override
    protected void process() {
        // TODO: Use an orthographic camera for 2D
        this.texture.draw(Matrix2x3.transformation(0.0f, 0.0f, 0.0, 0.1f, 0.1f));
    }

    @Override
    protected void terminate() {

    }
}
