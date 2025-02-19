package io.github.ardentengine.core.scene;

import io.github.ardentengine.core.math.Matrix3x4;
import io.github.ardentengine.core.math.Matrix4;
import io.github.ardentengine.core.math.Vector3;

import java.util.Objects;

public class Node3D extends Node {

    private Vector3 position = Vector3.ZERO;
    // TODO: Rotation using quaternion?
    private Vector3 rotation = Vector3.ZERO;
    private Vector3 scale = Vector3.ONE;

    private Matrix3x4 localTransform = null;
    private Matrix3x4 globalTransform = null;

    private void invalidateTransform() {
        if (this.globalTransform != null) {
            this.localTransform = null;
            this.globalTransform = null;
            for (var child : this.children()) {
                if (child instanceof Node3D) {
                    ((Node3D) child).invalidateTransform();
                }
            }
        }
    }

    @Override
    void exitScene() {
        this.invalidateTransform();
        super.exitScene();
    }

    public final Vector3 position() {
        return this.position;
    }

    public final void setPosition(Vector3 position) {
        Objects.requireNonNull(position, "Position cannot be null");
        if (!this.position.equals(position)) {
            this.position = position;
            this.invalidateTransform();
        }
    }

    public final void setPosition(float x, float y, float z) {
        if (!this.position.equals(x, y, z)) {
            this.position = new Vector3(x, y, z);
            this.invalidateTransform();
        }
    }

    public final void translate(Vector3 translation) {
        Objects.requireNonNull(translation, "Translation cannot be null");
        if (!translation.equals(0.0f, 0.0f, 0.0f)) {
            this.position = this.position.plus(translation);
            this.invalidateTransform();
        }
    }

    public final void translate(float x, float y, float z) {
        if (x != 0.0f || y != 0.0f || z != 0.0f) {
            this.position = this.position.plus(x, y, z);
            this.invalidateTransform();
        }
    }

    public final Vector3 rotation() {
        return this.rotation;
    }

    public final Vector3 rotationDegrees() {
        return new Vector3(
            (float) Math.toDegrees(this.rotation().x()),
            (float) Math.toDegrees(this.rotation().y()),
            (float) Math.toDegrees(this.rotation().z())
        );
    }

    // TODO: Rotation quaternion?

    public final void setRotation(float x, float y, float z) {
        if (!this.rotation.equals(x, y, z)) {
            this.rotation = new Vector3(x, y, z);
            this.invalidateTransform();
        }
    }

    public final void setRotation(Vector3 rotation) {
        Objects.requireNonNull(rotation, "Rotation cannot be null");
        if(!this.rotation.equals(rotation)) {
            this.rotation = rotation;
            this.invalidateTransform();
        }
    }

    public final void setRotationDegrees(float x, float y, float z) {
        this.setRotation(
            (float) Math.toDegrees(x),
            (float) Math.toDegrees(y),
            (float) Math.toDegrees(z)
        );
    }

    public final void setRotationDegrees(Vector3 rotation) {
        Objects.requireNonNull(rotation, "Rotation cannot be null");
        this.setRotationDegrees(rotation.x(), rotation.y(), rotation.z());
    }

    public final void rotate(float x, float y, float z) {
        if (x != 0.0f || y != 0.0f || z != 0.0f) {
            this.rotation = this.rotation.plus(x, y, z);
            this.invalidateTransform();
        }
    }

    public final void rotate(Vector3 rotation) {
        Objects.requireNonNull(rotation, "Rotation cannot be null");
        if (!rotation.equals(0.0f, 0.0f, 0.0f)) {
            this.rotation = this.rotation.plus(rotation);
            this.invalidateTransform();
        }
    }

    public final void rotateDegrees(float x, float y, float z) {
        this.rotate(
            (float) Math.toDegrees(x),
            (float) Math.toDegrees(y),
            (float) Math.toDegrees(z)
        );
    }

    public final void rotateDegrees(Vector3 rotation) {
        Objects.requireNonNull(rotation, "Rotation cannot be null");
        this.rotateDegrees(rotation.x(), rotation.y(), rotation.z());
    }

    public final Vector3 scale() {
        return this.scale;
    }

    public final void setScale(Vector3 scale) {
        Objects.requireNonNull(scale, "Scale cannot be null");
        if (!this.scale.equals(scale)) {
            this.scale = scale;
            this.invalidateTransform();
        }
    }

    public final void setScale(float x, float y, float z) {
        if (!this.scale.equals(x, y, z)) {
            this.scale = new Vector3(x, y, z);
            this.invalidateTransform();
        }
    }

    public final Matrix3x4 localTransform() {
        return this.localTransform == null ? this.localTransform = Matrix3x4.translation(this.position())
            .multiply(Matrix4.rotation(this.rotation()))
            .multiply(Matrix4.scaling(this.scale())) : this.localTransform;
    }

    public final Matrix3x4 globalTransform() {
        if (this.globalTransform != null) {
            return this.globalTransform;
        } else if (this.parent() instanceof Node3D parent) {
            return this.globalTransform = parent.globalTransform().multiply(this.localTransform(), 0.0f, 0.0f, 0.0f, 1.0f);
        }
        return this.globalTransform = this.localTransform();
    }

    public final Vector3 globalPosition() {
        return this.globalTransform().column3();
    }
}
