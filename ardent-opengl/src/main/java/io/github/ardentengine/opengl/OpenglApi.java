package io.github.ardentengine.opengl;

import io.github.ardentengine.core.rendering.RenderingApi;
import io.github.ardentengine.core.rendering.ShaderProgram;
import io.github.ardentengine.core.rendering.TextureData;
import io.github.ardentengine.core.rendering.VertexData;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class OpenglApi extends RenderingApi {

    public OpenglApi() {
        // TODO: OpenGL needs different capabilities for different windows
        //  Capabilities also need to have been created before any OpenGL function can be called
        //  This however must be called AFTER glfwMakeContextCurrent
        GL.createCapabilities();
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
}
