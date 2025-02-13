package io.github.ardentengine.core.resources;

import java.nio.ByteBuffer;

public class ImageTexture extends Texture {

    public final void setImage(ByteBuffer pixels, int width, int height) {
        this.textureData.setTexture(pixels, width, height);
    }
}
