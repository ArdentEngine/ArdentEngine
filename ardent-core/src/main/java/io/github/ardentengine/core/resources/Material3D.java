package io.github.ardentengine.core.resources;

import io.github.ardentengine.core.math.Color;
import io.github.ardentengine.core.rendering.RenderingApi;

import java.nio.FloatBuffer;

public class Material3D extends Material {

    private Color diffuse = new Color(0.2f, 0.2f, 0.2f);

    public final Color diffuse() {
        return this.diffuse;
    }

    public final void setDiffuse(Color diffuse) {
        this.diffuse = diffuse;
    }

    @Override
    public FloatBuffer getMaterialData() {
        return RenderingApi.getInstance().createFloatBuffer(4)
            .put(this.diffuse().r()).put(this.diffuse().g()).put(this.diffuse().b()).put(this.diffuse().a())
            .flip();
    }
}
