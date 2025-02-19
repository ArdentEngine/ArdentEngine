package io.github.ardentengine.core.scene;

import io.github.ardentengine.core.math.Matrix4;
import io.github.ardentengine.core.math.Vector2i;
import io.github.ardentengine.core.rendering.RenderingApi;

public class Camera3D extends Node3D {

    public double fov = 7.0 / 18.0 * Math.PI;
    public float nearPlane = 0.01f;
    public float farPlane = 1000.0f;

    // TODO: Allow cameras to render to a texture
    //  When the camera is enabled and no render target is assigned, it should render to the screen
    //  Only one camera can be enabled and have no render target at the same time
    //  If a render target is assigned, the camera should be allowed to be enabled/disabled at any time

    // TODO: The camera should also contain stuff related to post processing

    @Override
    void update(float deltaTime) {
        // TODO: This does not need to be done every frame
        RenderingApi.getInstance().setCamera3D(this.viewMatrix(), this.projectionMatrix());
        super.update(deltaTime);
    }

    public final Matrix4 projectionMatrix() {
        // TODO: Use the size of the viewport instead of the size of the window because the window does not exist on mobile
//        return Matrix4.perspectiveProjection(this.fov, DisplayApi.getInstance().getWindowSize(), this.nearPlane, this.farPlane);
        return Matrix4.perspectiveProjection(this.fov, new Vector2i(800, 450), this.nearPlane, this.farPlane);
    }

    public final Matrix4 viewMatrix() {
        // TODO: Use global rotation instead
        return Matrix4.rotation(this.rotation()).multiply(Matrix4.translation(this.globalPosition().negated()));
    }
}
