package io.github.ardentengine.core.resources;

import io.github.ardentengine.core.rendering.RenderingApi;

import java.nio.FloatBuffer;

public class ShaderMaterial extends Material {

    private Shader shader;

    public final Shader shader() {
        return this.shader;
    }

    public final void setShader(Shader shader) {
        this.shader = shader;
    }

    @Override
    public FloatBuffer getMaterialData() {
        // TODO: Add a way to pass uniform variables to shaders
        return RenderingApi.getInstance().createFloatBuffer(0).flip();
    }
}
