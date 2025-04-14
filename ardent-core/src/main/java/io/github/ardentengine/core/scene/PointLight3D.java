package io.github.ardentengine.core.scene;

import io.github.ardentengine.core.math.Color;
import io.github.ardentengine.core.rendering.LightData;
import io.github.ardentengine.core.rendering.RenderingApi;

import java.util.Objects;

public class PointLight3D extends Node3D {

    private final LightData lightData = RenderingApi.getInstance().createLight();

    private Color ambient = Color.WHITE;
    private Color diffuse = Color.WHITE;
    private Color specular = Color.WHITE;
    private boolean enabled = true;

    @Override
    void enterScene(SceneTree sceneTree) {
        super.enterScene(sceneTree);
        // Set the light to be enabled or disabled when entering the scene tree
        this.lightData.setEnabled(this.enabled);
        // TODO: When scenes are instantiated setter methods are not called
        this.lightData.setAmbient(this.ambient);
        this.lightData.setDiffuse(this.diffuse);
        this.lightData.setSpecular(this.specular);
    }

    @Override
    void update(float deltaTime) {
        super.update(deltaTime);
        // TODO: Do this when the transform is changed
        this.lightData.setPosition(this.globalPosition());
    }

    @Override
    void exitScene() {
        super.exitScene();
        // Disable the light when it exits the scene tree to hide it
        this.lightData.setEnabled(false);
    }

    public final Color ambient() {
        return this.ambient;
    }

    public final void setAmbient(Color ambient) {
        this.ambient = Objects.requireNonNull(ambient, "Color cannot be null");
        this.lightData.setAmbient(ambient);
    }

    public final Color diffuse() {
        return this.diffuse;
    }

    public final void setDiffuse(Color diffuse) {
        this.diffuse = Objects.requireNonNull(diffuse, "Color cannot be null");
        this.lightData.setDiffuse(diffuse);
    }

    public final Color specular() {
        return this.specular;
    }

    public final void setSpecular(Color specular) {
        this.specular = Objects.requireNonNull(specular, "Color cannot be null");
        this.lightData.setSpecular(specular);
    }

    public final boolean enabled() {
        return this.enabled;
    }

    public final void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (this.isInsideScene()) {
            this.lightData.setEnabled(enabled);
        }
    }
}
