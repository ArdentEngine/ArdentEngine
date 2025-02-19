package io.github.ardentengine.core.rendering;

import io.github.ardentengine.core.math.Matrix3;
import io.github.ardentengine.core.math.Matrix4;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
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

    public FloatBuffer createFloatBuffer(int capacity) {
        return ByteBuffer.allocateDirect(capacity * Float.BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
    }

    public IntBuffer createIntBuffer(int capacity) {
        return ByteBuffer.allocateDirect(capacity * Integer.BYTES).order(ByteOrder.nativeOrder()).asIntBuffer();
    }

    public abstract ShaderProgram createShader();

    public abstract TextureData createTexture();

    public abstract void setDepthTest(boolean depthTest);

    // TODO: Cull face

    // TODO: Blending

    // TODO: Methods related to the camera should probably be in a Viewport class

    public abstract void setCamera2D(Matrix3 view, Matrix4 projection);

    public abstract void setCamera3D(Matrix4 view, Matrix4 projection);
}
