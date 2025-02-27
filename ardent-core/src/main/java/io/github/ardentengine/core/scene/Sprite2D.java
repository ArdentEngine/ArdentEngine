package io.github.ardentengine.core.scene;

import io.github.ardentengine.core.resources.Material;
import io.github.ardentengine.core.resources.Texture;

public class Sprite2D extends Node2D {

    private Texture texture = null;

    // TODO: Move this to a VisualInstance2D class
    private Material material = null;

    // TODO: Sprite atlas, flipping, and region

    @Override
    void update(float deltaTime) {
        // TODO: Don't draw the texture if the sprite is not within the camera's view rect
        if (this.texture != null) {
            if (this.material != null) {
                this.texture.draw(this.globalTransform(), this.material);
            } else {
                this.texture.draw(this.globalTransform());
            }
        }
        super.update(deltaTime);
    }

    public final Texture texture() {
        return this.texture;
    }

    public final void setTexture(Texture texture) {
        this.texture = texture;
    }
}
