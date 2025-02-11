package io.github.ardentengine.opengl;

import io.github.ardentengine.core.rendering.RenderingSystem;
import io.github.ardentengine.core.rendering.ShaderProgram;
import io.github.ardentengine.core.rendering.TextureData;
import io.github.ardentengine.core.rendering.VertexData;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class OpenglSystem extends RenderingSystem {

    public OpenglSystem() {
        // TODO: OpenGL needs different capabilities for different windows
        //  Capabilities also need to have been created before any OpenGL function can be called
        //  This however must be called AFTER glfwMakeContextCurrent
        GL.createCapabilities();
    }

    @Override
    protected void initialize() {
        this.setClearColor(0.0f, 0.5f, 1.0f);
    }

    @Override
    protected void process() {
        ResourceCleaner.runCleaner();
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    // TODO: Check for gl errors
    //  https://www.khronos.org/opengl/wiki/OpenGL_Error

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
    protected void terminate() {
        ResourceCleaner.cleanRemaining();
    }
}
