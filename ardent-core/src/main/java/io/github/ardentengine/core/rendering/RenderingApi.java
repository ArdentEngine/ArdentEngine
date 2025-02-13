package io.github.ardentengine.core.rendering;

import io.github.ardentengine.core.math.Matrix3;
import io.github.ardentengine.core.math.Matrix4;

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

    // TODO: Methods related to the camera should probably be in a Viewport class

    public abstract void setCamera2D(Matrix3 view, Matrix4 projection);
}
