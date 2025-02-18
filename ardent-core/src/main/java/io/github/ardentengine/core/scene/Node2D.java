package io.github.ardentengine.core.scene;

import io.github.ardentengine.core.math.Matrix2x3;
import io.github.ardentengine.core.math.Vector2;

import java.util.Objects;

public class Node2D extends Node {

    private Vector2 position = Vector2.ZERO;
    private double rotation = 0.0;
    private Vector2 scale = Vector2.ONE;

    // TODO: Skew

    // TODO: Z-index and Y-sort

    private Matrix2x3 localTransform = null;
    private Matrix2x3 globalTransform = null;

    @Override
    void exitScene() {
        this.localTransform = null;
        this.globalTransform = null;
        super.exitScene();
    }

    void invalidateTransform() {
        if (this.globalTransform != null) {
            this.localTransform = null;
            this.globalTransform = null;
            for (var child : this.children()) {
                if (child instanceof Node2D) {
                    ((Node2D) child).invalidateTransform();
                }
            }
        }
    }

    public final Vector2 position() {
        return this.position;
    }

    public final void setPosition(Vector2 position) {
        Objects.requireNonNull(position, "Position cannot be null");
        if (!this.position.equals(position)) {
            this.position = position;
            this.invalidateTransform();
        }
    }

    public final void setPosition(float x, float y) {
        if (!this.position.equals(x, y)) {
            this.position = new Vector2(x, y);
            this.invalidateTransform();
        }
    }

    public final void translate(Vector2 translation) {
        Objects.requireNonNull(translation, "Translation cannot be null");
        if (!translation.equals(0.0f, 0.0f)) {
            this.position = this.position.plus(translation);
            this.invalidateTransform();
        }
    }

    public final void translate(float x, float y) {
        if (x != 0.0f || y != 0.0f) {
            this.position = this.position.plus(x, y);
            this.invalidateTransform();
        }
    }

    public final double rotation() {
        return this.rotation;
    }

    public final void setRotation(double rotation) {
        if (this.rotation != rotation) {
            this.rotation = rotation;
            this.invalidateTransform();
        }
    }

    public final Vector2 scale() {
        return this.scale;
    }

    public final void setScale(Vector2 scale) {
        Objects.requireNonNull(scale, "Scale cannot be null");
        if (!this.scale.equals(scale)) {
            this.scale = scale;
            this.invalidateTransform();
        }
    }

    public final void setScale(float x, float y) {
        if (!this.scale.equals(x, y)) {
            this.scale = new Vector2(x, y);
            this.invalidateTransform();
        }
    }

    public final Matrix2x3 localTransform() {
        return this.localTransform == null ? this.localTransform = Matrix2x3.transformation(this.position(), this.rotation(), this.scale()) : this.localTransform;
    }

    public final Matrix2x3 globalTransform() {
        // Recompute the global transform if it is not cached
        if (this.globalTransform == null) {
            // Check if this node is a child of a Node2D
            if (this.parent() instanceof Node2D parent) {
                // Compute the global transform from the transform of this node's parent
                this.globalTransform = parent.globalTransform().multiply(this.localTransform(), 0.0f, 0.0f, 1.0f);
            } else {
                // This node is the root of its own subtree
                this.globalTransform = this.localTransform();
            }
        }
        return this.globalTransform;
    }

    public final Vector2 globalPosition() {
        return this.globalTransform().column2();
    }

    public final double globalRotation() {
        var transform = this.globalTransform();
        return Math.atan2(transform.m10(), transform.m00());
    }
}
