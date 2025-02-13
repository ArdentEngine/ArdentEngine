package io.github.ardentengine.core.temp;

import io.github.ardentengine.core.EngineSystem;
import io.github.ardentengine.core.math.Matrix2x3;
import io.github.ardentengine.core.math.Rect2;
import io.github.ardentengine.core.rendering.RenderingApi;
import io.github.ardentengine.core.rendering.RenderingSystem2D;
import io.github.ardentengine.core.rendering.TextureData;

import java.nio.ByteBuffer;

public class EngineTester extends EngineSystem {

    private final TextureData texture = RenderingApi.getInstance().createTexture();
    private final TextureData anotherTexture = RenderingApi.getInstance().createTexture();

    @Override
    protected void initialize() {
        // TODO: Create texture resources and load textures
        var pixels = ByteBuffer.allocateDirect(4).putInt(0x00ff00ff).flip();
        this.texture.setTexture(pixels, 1, 1);
        var otherPixels = ByteBuffer.allocateDirect(4).putInt(0xff0000ff).flip();
        this.anotherTexture.setTexture(otherPixels, 1, 1);
    }

    @Override
    protected void process() {
        RenderingSystem2D.batchDraw(this.texture, Matrix2x3.transformation(0.0f, 0.0f, 0.0, 1.0f, 1.0f), new Rect2(0.0f, 0.0f, 1.0f, 1.0f));
        RenderingSystem2D.batchDraw(this.texture, Matrix2x3.transformation(0.5f, 0.5f, 0.0, 1.0f, 1.0f), new Rect2(0.0f, 0.0f, 1.0f, 1.0f));
        RenderingSystem2D.batchDraw(this.anotherTexture, Matrix2x3.transformation(-0.25f, 0.25f, 0.0, 1.0f, 1.0f), new Rect2(0.0f, 0.0f, 1.0f, 1.0f));
    }

    @Override
    protected void terminate() {

    }
}
