package io.github.ardentengine.core.rendering;

import java.util.ServiceLoader;

public abstract class RenderingApi {

    private static RenderingApi instance;

    public static synchronized RenderingApi getInstance() {
        if(instance == null) {
            // TODO: Handle the case in which there are more or none
            for(var api : ServiceLoader.load(RenderingApi.class)) {
                instance = api;
                break;
            }
        }
        return instance;
    }

    public abstract void setClearColor(float red, float green, float blue, float alpha);

    public final void setClearColor(float red, float green, float blue) {
        this.setClearColor(red, green, blue, 1.0f);
    }

    public abstract VertexData createVertexData();

    public abstract ShaderProgram createShader();

    public abstract TextureData createTexture();
}
