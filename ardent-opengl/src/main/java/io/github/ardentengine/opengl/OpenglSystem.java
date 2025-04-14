package io.github.ardentengine.opengl;

import io.github.ardentengine.core.EngineSystem;
import io.github.ardentengine.core.rendering.RenderingApi;
import org.lwjgl.opengl.GL11;

public class OpenglSystem extends EngineSystem {

    // FIXME: This class only exists because GL.createCapabilities needs to be called in OpenglApi

    @Override
    protected void initialize() {
        RenderingApi.getInstance().setClearColor(0.0f, 0.5f, 1.0f);
    }

    @Override
    protected void process() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    // TODO: Check for gl errors
    //  https://www.khronos.org/opengl/wiki/OpenGL_Error

    @Override
    protected void terminate() {

    }
}
