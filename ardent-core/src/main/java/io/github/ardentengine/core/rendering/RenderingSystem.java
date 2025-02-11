package io.github.ardentengine.core.rendering;

import io.github.ardentengine.core.EngineSystem;

public abstract class RenderingSystem extends EngineSystem {

    private static RenderingSystem instance;

    public static RenderingSystem getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Rendering System has not been created yet");
        }
        return instance;
    }

    protected RenderingSystem() {
        if (instance != null) {
            throw new IllegalStateException("Rendering System " + instance + " has already been created");
        }
        instance = this;
    }

    public abstract void setClearColor(float red, float green, float blue, float alpha);

    public final void setClearColor(float red, float green, float blue) {
        this.setClearColor(red, green, blue, 1.0f);
    }

    public abstract VertexData createVertexData();

    public abstract ShaderProgram createShader();

    public abstract TextureData createTexture();
}
