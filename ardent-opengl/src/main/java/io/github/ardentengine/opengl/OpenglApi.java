package io.github.ardentengine.opengl;

import io.github.ardentengine.core.math.Matrix3;
import io.github.ardentengine.core.math.Matrix4;
import io.github.ardentengine.core.rendering.RenderingApi;
import io.github.ardentengine.core.rendering.ShaderProgram;
import io.github.ardentengine.core.rendering.TextureData;
import io.github.ardentengine.core.rendering.VertexData;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class OpenglApi extends RenderingApi {

    private final UniformBuffer camera2D;

    public OpenglApi() {
        // TODO: OpenGL needs different capabilities for different windows
        //  Capabilities also need to have been created before any OpenGL function can be called
        //  This however must be called AFTER glfwMakeContextCurrent
        GL.createCapabilities();
        this.camera2D = new UniformBuffer(0, 112);
    }

    @Override
    public void setClearColor(float red, float green, float blue, float alpha) {
        GL11.glClearColor(red, green, blue, alpha);
    }

    @Override
    public VertexData createVertexData() {
        return new OpenglVertexData();
    }

    @Override
    public ShaderProgram createShader() {
        return new OpenglShader();
    }

    @Override
    public TextureData createTexture() {
        return new OpenglTexture();
    }

    @Override
    public void setCamera2D(Matrix3 view, Matrix4 projection) {
        // TODO: The 2D view matrix should be an identity matrix by default
        var buffer = BufferUtils.createFloatBuffer(28);
        buffer.put(view.m00()).put(view.m10()).put(view.m20()).put(0.0f);
        buffer.put(view.m01()).put(view.m11()).put(view.m21()).put(0.0f);
        buffer.put(view.m02()).put(view.m12()).put(view.m22()).put(0.0f);
        buffer.put(projection.m00()).put(projection.m10()).put(projection.m20()).put(projection.m30());
        buffer.put(projection.m01()).put(projection.m11()).put(projection.m21()).put(projection.m31());
        buffer.put(projection.m02()).put(projection.m12()).put(projection.m22()).put(projection.m32());
        buffer.put(projection.m03()).put(projection.m13()).put(projection.m23()).put(projection.m33());
        this.camera2D.put(buffer.flip());
    }
}
