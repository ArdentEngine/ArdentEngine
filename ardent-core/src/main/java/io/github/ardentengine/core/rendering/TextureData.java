package io.github.ardentengine.core.rendering;

import java.nio.ByteBuffer;

public abstract class TextureData {

    public abstract void setTexture(ByteBuffer pixels, int width, int height);

    public abstract void bind(int binding);
}
