package io.github.ardentengine.core.scene;

import io.github.ardentengine.core.math.Matrix3;
import io.github.ardentengine.core.math.Matrix4;
import io.github.ardentengine.core.math.Vector2;
import io.github.ardentengine.core.math.Vector2i;
import io.github.ardentengine.core.rendering.RenderingApi;

import java.util.Objects;

public class Camera2D extends Node2D {

    private Vector2 offset = Vector2.ZERO;
    private Vector2 zoom = Vector2.ONE;

    // TODO: Add the ability to have multiple cameras in the scene and switch between cameras

    // TODO: Limits, drag, smoothing

    // TODO: Allow cameras to render to a texture
    //  When the camera is enabled and no render target is assigned, it should render to the screen
    //  Only one camera can be enabled and have no render target at the same time
    //  If a render target is assigned, the camera should be allowed to be enabled/disabled at any time

    // TODO: The camera should also contain stuff related to post processing

    private Matrix3 viewMatrix = null;

    @Override
    void invalidateTransform() {
        this.viewMatrix = null;
        super.invalidateTransform();
    }

    @Override
    void update(float deltaTime) {
        // TODO: This does not need to be done every frame
        RenderingApi.getInstance().setCamera2D(this.viewMatrix(), this.projectionMatrix());
        super.update(deltaTime);
    }

    public final Vector2 offset() {
        return this.offset;
    }

    public final void setOffset(Vector2 offset) {
        Objects.requireNonNull(offset, "Offset cannot be null");
        if (!this.offset.equals(offset)) {
            this.offset = offset;
            this.viewMatrix = null;
        }
    }

    public final void setOffset(float x, float y) {
        if (!this.offset.equals(x, y)) {
            this.offset = new Vector2(x, y);
            this.viewMatrix = null;
        }
    }

    public final Vector2 zoom() {
        return this.zoom;
    }

    public final void setZoom(Vector2 zoom) {
        Objects.requireNonNull(zoom, "Zoom cannot be null");
        if (!this.zoom.equals(zoom)) {
            this.zoom = zoom;
            this.viewMatrix = null;
        }
    }

    public final void setZoom(float x, float y) {
        if (!this.zoom.equals(x, y)) {
            this.zoom = new Vector2(x, y);
            this.viewMatrix = null;
        }
    }

    public final Matrix4 projectionMatrix() {
        // TODO: The camera should have the option to have a fixed render size instead of using the window size
        //  This should also not rely on the window because it is not accessible on mobile
//        var windowSize = DisplayApi.getInstance().getWindowSize();
        var windowSize = new Vector2i(800, 450);
        return Matrix4.orthographicProjection(windowSize.x(), windowSize.aspect(), -4096.0f, 4096.0f);
    }

    public final Matrix3 viewMatrix() {
        // Recompute the view matrix if it is not cached
        if (this.viewMatrix == null) {
            var pos = this.globalPosition();
            var rotation = this.globalRotation();
            var cos = (float) Math.cos(rotation);
            var sin = (float) Math.sin(rotation);
            this.viewMatrix = new Matrix3(
                cos * this.zoom().x(), -sin * this.zoom().y(), -pos.x() - this.offset().x(),
                sin * this.zoom().x(), cos * this.zoom().y(), -pos.y() - this.offset().y(),
                0.0f, 0.0f, 1.0f
            );
        }
        return this.viewMatrix;
    }
}
