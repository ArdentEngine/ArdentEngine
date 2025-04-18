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

    // TODO: Add a method to set a shader parameter (uniform variable)

    @Override
    public FloatBuffer getMaterialData() {
        return RenderingApi.getInstance().createFloatBuffer(0).flip();
    }
}
