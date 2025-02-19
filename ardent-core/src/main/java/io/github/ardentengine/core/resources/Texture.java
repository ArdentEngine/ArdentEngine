package io.github.ardentengine.core.resources;

import io.github.ardentengine.core.math.Matrix2x3;
import io.github.ardentengine.core.math.Rect2;
import io.github.ardentengine.core.math.Vector2i;
import io.github.ardentengine.core.rendering.RenderingApi;
import io.github.ardentengine.core.rendering.RenderingSystem2D;
import io.github.ardentengine.core.rendering.TextureData;

public abstract class Texture {

    protected final TextureData textureData = RenderingApi.getInstance().createTexture();

    // TODO: Find a way to only update some textures when textureData.bind is called

    public final int width() {
        return this.textureData.width();
    }

    public final int height() {
        return this.textureData.height();
    }

    public final Vector2i size() {
        return new Vector2i(this.width(), this.height());
    }

    public final void draw(Matrix2x3 transform, Rect2 region) {
        RenderingSystem2D.batchDraw(this.textureData, transform, region);
    }

    public final void draw(Matrix2x3 transform) {
        this.draw(transform, new Rect2(0.0f, 0.0f, 1.0f, 1.0f));
    }
}
