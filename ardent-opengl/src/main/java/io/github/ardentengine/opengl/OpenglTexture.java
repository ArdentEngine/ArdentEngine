package io.github.ardentengine.opengl;

import io.github.ardentengine.core.rendering.TextureData;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import java.nio.ByteBuffer;

public class OpenglTexture extends TextureData {

    private final int texture;

    public OpenglTexture() {
        var texture = GL11.glGenTextures();
        ResourceCleaner.registerAction(this, () -> GL11.glDeleteTextures(texture));
        this.texture = texture;
    }

    @Override
    public void setTexture(ByteBuffer pixels, int width, int height) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.texture);
        // TODO: Texture properties
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        // TODO: Add support for more image formats
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }

    @Override
    public void bind(int binding) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.texture);
        GL13.glActiveTexture(GL13.GL_TEXTURE0 + binding);
    }
}
