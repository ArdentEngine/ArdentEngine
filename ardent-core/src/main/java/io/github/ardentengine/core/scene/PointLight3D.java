package io.github.ardentengine.core.scene;

import io.github.ardentengine.core.math.Color;
import io.github.ardentengine.core.rendering.LightSystem;

import java.util.Objects;

public class PointLight3D extends Node3D {

    private Color ambient = Color.WHITE;
    private Color diffuse = Color.WHITE;
    private Color specular = Color.WHITE;
    /**
     * If {@code true}, the light will emit light.
     */
    private boolean enabled = true;

    @Override
    void enterScene(SceneTree sceneTree) {
        super.enterScene(sceneTree);
        // Enable the light in the LightSystem when it enters the scene if it should be enabled
        if (this.enabled) {
            LightSystem.enableLight(this);
        }
    }

    @Override
    void exitScene() {
        super.exitScene();
        // Disable the light when it exits the scene tree to hide it
        LightSystem.disableLight(this);
    }

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

    /**
     * Getter method for {@link PointLight3D#enabled}.
     * <p>
     *     If {@code true}, the light will emit light.
     * </p>
     *
     * @return {@code true} if the light is enabled, {@code false} if it is disabled.
     */
    public final boolean enabled() {
        return this.enabled;
    }

    /**
     * Setter method for {@link PointLight3D#enabled}.
     * <p>
     *     If {@code true}, the light will emit light.
     * </p>
     *
     * @param enabled True to enable the light, false to disable it.
     */
    public final void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (this.enabled) {
            LightSystem.enableLight(this);
        } else {
            LightSystem.disableLight(this);
        }
    }
}
