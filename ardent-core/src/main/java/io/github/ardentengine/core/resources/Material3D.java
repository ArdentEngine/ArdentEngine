package io.github.ardentengine.core.resources;

import io.github.ardentengine.core.math.Color;
import io.github.ardentengine.core.rendering.RenderingApi;

import java.nio.FloatBuffer;
import java.util.Objects;

public class Material3D extends Material {

    private Color ambient = new Color(0.2f, 0.2f, 0.2f);
    private Color diffuse = new Color(0.5f, 0.5f, 0.5f);
    private Color specular = Color.WHITE;
    private float shininess = 32.0f;

    public final Color ambient() {
        return this.ambient;
    }

    public final void setAmbient(Color ambient) {
        this.ambient = Objects.requireNonNull(ambient, "Color cannot be null");
    }

    public final Color diffuse() {
        return this.diffuse;
    }

    public final void setDiffuse(Color diffuse) {
        this.diffuse = Objects.requireNonNull(diffuse, "Color cannot be null");
    }

    public final Color specular() {
        return this.specular;
    }

    public final void setSpecular(Color specular) {
        this.specular = Objects.requireNonNull(specular, "Color cannot be null");
    }

    public final float shininess() {
        return this.shininess;
    }

    public final void setShininess(float shininess) {
        this.shininess = shininess;
    }

    @Override
    public FloatBuffer getMaterialData() {
        return RenderingApi.getInstance().createFloatBuffer(13)
            .put(this.ambient().r()).put(this.ambient().g()).put(this.ambient().b()).put(this.ambient().a())
            .put(this.diffuse().r()).put(this.diffuse().g()).put(this.diffuse().b()).put(this.diffuse().a())
            .put(this.specular().r()).put(this.specular().g()).put(this.specular().b()).put(this.specular().a())
            .put(this.shininess())
            .flip();
    }
}
