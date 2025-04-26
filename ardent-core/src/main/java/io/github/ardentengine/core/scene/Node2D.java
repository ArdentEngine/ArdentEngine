package io.github.ardentengine.core.scene;

import io.github.ardentengine.core.math.Matrix2x3;
import io.github.ardentengine.core.math.Vector2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Function;

/**
 * Base class for all 2D objects.
 * <p>
 *     Has a position, rotation, and scale.
 *     Controls the rendering order of its children using z index and y sort.
 * </p>
 */
public class Node2D extends Node {

    private static final Comparator<Node2D> Y_SORT_COMPARATOR = (n1, n2) -> Float.compare(n2.globalPosition().y(), n1.globalPosition().y());
    private static final Function<Integer, ArrayList<Node2D>> LIST_FUNCTION = z -> new ArrayList<>();

    /** Position of this node relative to its parent. */
    private Vector2 position = Vector2.ZERO;
    /**Rotation in radians of this node relative to its parent. */
    private double rotation = 0.0;
    /** Scale of this node. */
    private Vector2 scale = Vector2.ONE;

    // TODO: Skew

    /**
     * Controls the order in which the node is drawn.
     * <p>
     *     Nodes with a lower z index will be drawn first.
     *     Nodes with a higher z index will be drawn in front of others.
     * </p>
     */
    private int zIndex = 0;
    /**
     * If {@code true}, child nodes with a higher y position will be drawn behind nodes with a lower y position.
     * <p>
     *     Nodes sort relative to each other only if they are on the same {@link Node2D#zIndex}.
     * </p>
     */
    private boolean ySort = false;

    /** Cached local transform. */
    private Matrix2x3 localTransform = null;
    /** Cached global transform. */
    private Matrix2x3 globalTransform = null;

    /**
     * Package-protected method used to invalidate this node's transform when its position, rotation, or scale are changed.
     * <p>
     *     Calling this method causes the local and global transform to be recomputed when the {@link Node2D#localTransform()} and {@link Node2D#globalTransform()} methods are called.
     * </p>
     * <p>
     *     Iterates recursively through the children of this node and invalidates their transform if it isn't already.
     * </p>
     * <p>
     *     Calling this method has no effect if this node's transform is already invalid.
     * </p>
     */
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

    /**
     * Getter method for {@link Node2D#position)}.
     * Returns the position of this node relative to its parent.
     *
     * @return The position of this node relative to its parent.
     */
    public final Vector2 position() {
        return this.position;
    }

    /**
     * Setter method for {@link Node2D#position}.
     * Sets the position of this node relative to its parent.
     *
     * @param position Position of this node relative to its parent.
     * @throws NullPointerException If the given position is {@code null}.
     * @see Node2D#setPosition(float, float)
     */
    public final void setPosition(Vector2 position) {
        Objects.requireNonNull(position, "Position cannot be null");
        if (!this.position.equals(position)) {
            this.position = position;
            this.invalidateTransform();
        }
    }

    /**
     * Setter method for {@link Node2D#position}.
     * Sets the position of this node relative to its parent.
     *
     * @param x Position of this node on the x axis.
     * @param y Position of this node on the y axis.
     * @see Node2D#setPosition(Vector2)
     */
    public final void setPosition(float x, float y) {
        if (!this.position.equals(x, y)) {
            this.position = new Vector2(x, y);
            this.invalidateTransform();
        }
    }

    /**
     * Translates this node by the given offset.
     *
     * @param translation The translation to apply.
     * @throws NullPointerException If the given translation is {@code null}.
     * @see Node2D#translate(float, float)
     */
    public final void translate(Vector2 translation) {
        Objects.requireNonNull(translation, "Translation cannot be null");
        if (!translation.equals(0.0f, 0.0f)) {
            this.position = this.position.plus(translation);
            this.invalidateTransform();
        }
    }

    /**
     * Translates this node by the given offset.
     *
     * @param x Translation on the x axis.
     * @param y Translation on the y axis.
     * @see Node2D#translate(Vector2)
     */
    public final void translate(float x, float y) {
        if (x != 0.0f || y != 0.0f) {
            this.position = this.position.plus(x, y);
            this.invalidateTransform();
        }
    }

    /**
     * Getter method for {@link Node2D#rotation}.
     * Returns the rotation in radians of this node relative to its parent.
     * <p>
     *     Use {@link Node2D#rotationDegrees()} to use degrees instead of radians.
     * </p>
     *
     * @return The rotation in radians of this node relative to its parent.
     */
    public final double rotation() {
        return this.rotation;
    }

    /**
     * Setter method for {@link Node2D#rotation}.
     * Sets the rotation in radians of this node relative to its parent.
     * <p>
     *     Use {@link Node2D#setRotationDegrees(double)} to use degrees instead of radians.
     * </p>
     *
     * @param rotation Rotation in radians of this node relative to its parent.
     */
    public final void setRotation(double rotation) {
        if (this.rotation != rotation) {
            this.rotation = rotation;
            this.invalidateTransform();
        }
    }

    /**
     * Getter method for {@link Node2D#rotation} that uses degrees instead of radians.
     * Returns the rotation of this node relative to its parent.
     * <p>
     *     Use {@link Node2D#rotation()} to use radians instead of degrees.
     * </p>
     *
     * @return The rotation of this node relative to its parent.
     */
    public final double rotationDegrees() {
        return Math.toDegrees(this.rotation());
    }

    /**
     * Setter method for {@link Node2D#rotation} that uses degrees instead of radians.
     * Sets the rotation of this node relative to its parent.
     * <p>
     *     Use {@link Node2D#setRotation(double)} to use radians instead of degrees.
     * </p>
     *
     * @param angle Rotation of this node relative to its parent.
     */
    public final void setRotationDegrees(double angle) {
        this.setRotation(Math.toRadians(angle));
    }

    /**
     * Applies a rotation to the node starting from its current rotation.
     *
     * @param angle The rotation angle in radians.
     * @see Node2D#rotateDegrees(double)
     */
    public final void rotate(double angle) {
        if (angle != 0.0) {
            this.rotation += angle;
            this.invalidateTransform();
        }
    }

    /**
     * Applies a rotation to the node starting from its current rotation.
     *
     * @param angle The rotation angle in degrees.
     * @see Node2D#rotate(double)
     */
    public final void rotateDegrees(double angle) {
        this.rotate(Math.toRadians(angle));
    }

    /**
     * Getter method for {@link Node2D#scale}.
     * Returns the scale of this node.
     *
     * @return The scale of this node.
     */
    public final Vector2 scale() {
        return this.scale;
    }

    /**
     * Setter method for {@link Node2D#scale)}.
     * Sets the scale of this node.
     *
     * @param scale Scale of this node.
     * @throws NullPointerException If the given scale is {@code null}.
     * @see Node2D#setScale(float, float)
     */
    public final void setScale(Vector2 scale) {
        Objects.requireNonNull(scale, "Scale cannot be null");
        if (!this.scale.equals(scale)) {
            this.scale = scale;
            this.invalidateTransform();
        }
    }

    /**
     * Setter method for {@link Node2D#scale}.
     * Sets the scale of this node.
     *
     * @param x Scale on the x axis.
     * @param y Scale on the y axis.
     * @see Node2D#setScale(Vector2)
     */
    public final void setScale(float x, float y) {
        if (!this.scale.equals(x, y)) {
            this.scale = new Vector2(x, y);
            this.invalidateTransform();
        }
    }

    /**
     * Multiplies this node's scale by the given value.
     *
     * @param scale Scale ratio.
     * @throws NullPointerException If the given scale is {@code null}.
     * @see Node2D#applyScale(float, float)
     */
    public final void applyScale(Vector2 scale) {
        Objects.requireNonNull(scale, "Scale cannot be null");
        if (!scale.equals(1.0f, 1.0f)) {
            this.scale = this.scale.multiply(scale);
            this.invalidateTransform();
        }
    }

    /**
     * Multiplies this node's scale by the given value.
     *
     * @param x Scale ratio on the x axis.
     * @param y Scale ratio on the y axis.
     * @see Node2D#applyScale(Vector2)
     */
    public final void applyScale(float x, float y) {
        if (x != 1.0f || y != 1.0f) {
            this.scale = this.scale.multiply(x, y);
            this.invalidateTransform();
        }
    }

    /**
     * Getter method for {@link Node2D#zIndex}.
     * <p>
     *     Controls the order in which the node is drawn.
     * </p>
     *
     * @return Index of the z layer of this node.
     */
    public final int zIndex() {
        return this.zIndex;
    }

    /**
     * Setter method for {@link Node2D#zIndex}.
     * <p>
     *     Controls the order in which the node is drawn.
     * </p>
     *
     * @param zIndex Index of the z layer of this node.
     */
    public final void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    /**
     * Getter method for {@link Node2D#ySort}.
     *
     * @return {@code true} if y-sorting is enabled, otherwise {@code false}.
     */
    public final boolean ySort() {
        return this.ySort;
    }

    /**
     * Setter method for {@link Node2D#ySort}.
     *
     * @param ySort {@code true} to enable y-sorting, {@code false} to disable it.
     */
    public final void setYSort(boolean ySort) {
        this.ySort = ySort;
    }

    /**
     * Called when this node is being drawn.
     * <p>
     *     This method can be extended to draw something specific for which there isn't a dedicated node.
     * </p>
     */
    protected void onDraw() {

    }

    /**
     * Called when this node is being drawn.
     */
    void draw() {
        this.onDraw();
    }

    // TODO: Check if PriorityQueue is better than TreeMap or ArrayList

    /**
     * Recursive method that adds all descendants of this node with {@link Node2D#ySort} enabled to the given map.
     * This method effectively flattens the tree so that nodes can then be sorted properly according to their y position.
     *
     * @param ySortChildren Resulting map.
     * @param zIndex Effective z index of this node.
     */
    private void ySortCollect(TreeMap<Integer, ArrayList<Node2D>> ySortChildren, int zIndex) {
        // Iterate through the children of this node
        for (var child : this.children()) {
            // Look for children of type Node2D
            if (child instanceof Node2D child2d) {
                // Add all children of type Node2D to the resulting map
                ySortChildren.computeIfAbsent(zIndex + child2d.zIndex(), LIST_FUNCTION).add(child2d);
                // Call this method recursively if this child also has ySort enabled
                if (child2d.ySort()) {
                    child2d.ySortCollect(ySortChildren, zIndex + child2d.zIndex());
                }
            }
        }
    }

    // TODO: Allow nodes to be set as invisible

    /**
     * Recursive method that adds all descendants of this node to the given map.
     * The resulting map can then be used to draw the nodes in the correct order.
     *
     * @param drawQueue Queue to which nodes to be drawn are added.
     * @param zIndex Effective z index of the parent node.
     */
    private void queueDraw(TreeMap<Integer, ArrayList<Node2D>> drawQueue, int zIndex, boolean ySort) {
        // Add this node to the draw queue
        drawQueue.computeIfAbsent(zIndex + this.zIndex(), LIST_FUNCTION).add(this);
        // The tree must be flattened if ySort is enabled
        if (this.ySort()) {
            // This branch has already been queued if the parent of this node has ySort enabled
            if (!ySort) {
                // Create a new tree map to hold nodes that should be sorted
                var ySortChildren = new TreeMap<Integer, ArrayList<Node2D>>();
                // Recursively look for descendants that should be y sorted
                this.ySortCollect(ySortChildren, zIndex + this.zIndex());
                // Draw the collected nodes in order according to their y position
                for (var z : ySortChildren.keySet()) {
                    var ySortList = ySortChildren.get(z);
                    ySortList.sort(Y_SORT_COMPARATOR);
                    // Add the node and possibly its descendant to the draw queue
                    for (var ySortNode : ySortList) {
                        ySortNode.queueDraw(drawQueue, z, true);
                    }
                }
            }
        } else {
            // Recursively add the children of this node to the draw queue
            for (var child : this.children()) {
                if (child instanceof Node2D) {
                    ((Node2D) child).queueDraw(drawQueue, zIndex + this.zIndex(), this.ySort());
                }
            }
        }
    }

    @Override
    void update(float deltaTime) {
        // Draw this node and its descendants if it is the root of a 2D branch
        if (!(this.parent() instanceof Node2D)) {
            // Use a draw queue to respect zIndex and ySort
            var drawQueue = new TreeMap<Integer, ArrayList<Node2D>>();
            this.queueDraw(drawQueue, 0, false);
            // Drawing happens in preorder
            for (var drawList : drawQueue.values()) {
                for (var drawNode : drawList) {
                    drawNode.draw();
                }
            }
        }
        // Update happens in postorder
        super.update(deltaTime);
    }

    @Override
    void exitScene() {
        this.localTransform = null;
        this.globalTransform = null;
        super.exitScene();
    }

    /**
     * Returns this node's local transform as a 2x3 transformation matrix.
     *
     * @return This node's local transform as a 2x3 transformation matrix.
     */
    public final Matrix2x3 localTransform() {
        return this.localTransform == null ? this.localTransform = Matrix2x3.transformation(this.position(), this.rotation(), this.scale()) : this.localTransform;
    }

    /**
     * Returns this node's global transform as a 2x3 transformation matrix.
     * <p>
     *     If this node's parent is not a {@code Node2D}, this method is equivalent to {@link Node2D#localTransform()}.
     * </p>
     *
     * @return This node's global transform as a 2x3 transformation matrix.
     */
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

    /**
     * Returns the global position of this node.
     *
     * @return The global position of this node.
     */
    public final Vector2 globalPosition() {
        return this.globalTransform().column2();
    }

    /**
     * Sets the global position of this node.
     * <p>
     *     Sets this node's {@link Node2D#position} to a value such that its {@link Node2D#globalPosition()} will be equal to the given value.
     * </p>
     *
     * @param x Global position on the x axis.
     * @param y Global position on the y axis.
     * @see Node2D#setGlobalPosition(Vector2)
     */
    public final void setGlobalPosition(float x, float y) {
        if (this.parent() instanceof Node2D parent) {
            this.setPosition(parent.globalTransform().affineInverse().multiply(x, y, 1.0f));
        } else {
            this.setPosition(x, y);
        }
    }

    /**
     * Sets the global position of this node.
     * <p>
     *     Sets the {@link Node2D#position} of this node to a value such that its {@link Node2D#globalPosition()} will be equal to the given value.
     * </p>
     *
     * @param position Global position of this node.
     * @throws NullPointerException If the given position is {@code null}.
     * @see Node2D#setGlobalPosition(float, float)
     */
    public final void setGlobalPosition(Vector2 position) {
        Objects.requireNonNull(position, "Position cannot be null");
        if (this.parent() instanceof Node2D parent) {
            this.setPosition(parent.globalTransform().affineInverse().multiply(position, 1.0f));
        } else {
            this.setPosition(position);
        }
    }

    /**
     * Returns the global rotation of this node in radians.
     * <p>
     *     Use {@link Node2D#globalRotationDegrees()} to get degrees instead of radians.
     * </p>
     *
     * @return The global rotation of this node in radians.
     */
    public final double globalRotation() {
        var transform = this.globalTransform();
        return Math.atan2(transform.m10(), transform.m00());
    }

    /**
     * Returns the global rotation of this node in degrees.
     * <p>
     *     Use {@link Node2D#globalRotation()} to get radians instead of degrees.
     * </p>
     *
     * @return The global rotation of this node in degrees.
     */
    public final double globalRotationDegrees() {
        return Math.toDegrees(this.globalRotation());
    }

    /**
     * Sets the global rotation of this node.
     * <p>
     *     Sets the {@link Node2D#rotation} of this node to a value such that its {@link Node2D#globalRotation()} will be equal to the given value.
     * </p>
     * <p>
     *     Use {@link Node2D#setGlobalRotationDegrees(double)} to use degrees instead of radians.
     * </p>
     *
     * @param rotation Global rotation in radians.
     */
    public final void setGlobalRotation(double rotation) {
        if (this.parent() instanceof Node2D parent) {
            // Get global transform
            var parentTransform = parent.globalTransform();
            var transform = parentTransform.multiply(this.localTransform(), 0.0f, 0.0f, 1.0f);
            // Set rotation
            var sin = (float) Math.sin(rotation);
            var cos = (float) Math.cos(rotation);
            var sx = transform.column0().length();
            var sy = Math.signum(transform.m00() * transform.m11() - transform.m10() * transform.m01()) * transform.column1().length();
            transform = new Matrix2x3(cos * sx, -sin * sy, transform.m02(), sin * sx, cos * sy, transform.m12());
            // Get local transform from global transform
            transform = parentTransform.affineInverse().multiply(transform, 0.0f, 0.0f, 1.0f);
            // Set rotation
            this.setRotation(Math.atan2(transform.m10(), transform.m00()));
        } else {
            this.setRotation(rotation);
        }
    }

    /**
     * Sets the global rotation of this node.
     * <p>
     *     Sets the {@link Node2D#rotation} of this node to a value such that its {@link Node2D#globalRotation()} will be equal to the given value.
     * </p>
     * <p>
     *     Use {@link Node2D#setGlobalRotation(double)} to use radians instead of degrees.
     * </p>
     *
     * @param rotation Global rotation in degrees.
     */
    public final void setGlobalRotationDegrees(double rotation) {
        this.setGlobalRotation(Math.toRadians(rotation));
    }

    /**
     * Returns the global scale of this node.
     *
     * @return The global scale of this node.
     */
    public final Vector2 globalScale() {
        var transform = this.globalTransform();
        var sign = Math.signum(transform.m00() * transform.m11() - transform.m10() * transform.m01());
        return new Vector2(transform.column0().length(), sign * transform.column1().length());
    }

    /**
     * Sets the global scale of this node.
     * <p>
     *     Sets the {@link Node2D#scale} of this node to a value such that its {@link Node2D#globalScale()} will be equal to the given value.
     * </p>
     *
     * @param x Global scale on the x axis.
     * @param y Global scale on the y axis.
     *
     * @see Node2D#setGlobalScale(Vector2)
     */
    public final void setGlobalScale(float x, float y) {
        if (this.parent() instanceof Node2D parent) {
            // Get global transform
            var parentTransform = parent.globalTransform();
            var transform = parentTransform.multiply(this.localTransform(), 0.0f, 0.0f, 1.0f);
            // Set scale
            transform = Matrix2x3.fromColumns(
                transform.column0().normalized().multiply(x),
                transform.column1().normalized().multiply(y),
                transform.column2()
            );
            // Get local transform from global transform
            transform = parentTransform.affineInverse().multiply(transform, 0.0f, 0.0f, 1.0f);
            // Set scale
            var sign = Math.signum(transform.m00() * transform.m11() - transform.m10() * transform.m01());
            this.setScale(transform.column0().length(), sign * transform.column1().length());
        } else {
            this.setScale(x, y);
        }
    }

    /**
     * Sets the global scale of this node.
     * <p>
     *     Sets the {@link Node2D#scale} of this node to a value such that its {@link Node2D#globalScale()} will be equal to the given value.
     * </p>
     *
     * @param scale Global scale.
     * @throws NullPointerException If the given scale is {@code null}.
     *
     * @see Node2D#setGlobalScale(float, float)
     */
    public final void setGlobalScale(Vector2 scale) {
        Objects.requireNonNull(scale, "Scale cannot be null");
        this.setGlobalScale(scale.x(), scale.y());
    }

    /**
     * Sets the local transform of this node.
     * <p>
     *     Sets the {@link Node2D#position}, {@link Node2D#rotation}, and {@link Node2D#scale} of this node so that its {@link Node2D#localTransform()} will be equal to the given transform.
     * </p>
     *
     * @param transform Local transform as a 2x3 transformation matrix.
     * @throws NullPointerException If the given transform is {@code null}.
     */
    public final void setLocalTransform(Matrix2x3 transform) {
        Objects.requireNonNull(transform, "Transform cannot be null");
        this.setPosition(transform.m02(), transform.m12());
        this.setRotation(Math.atan2(transform.m10(), transform.m00()));
        var sign = Math.signum(transform.m00() * transform.m11() - transform.m10() * transform.m01());
        this.setScale(transform.column0().length(), sign * transform.column1().length());
    }

    /**
     * Sets the global transform of this node.
     * <p>
     *     Sets the {@link Node2D#position}, {@link Node2D#rotation}, and {@link Node2D#scale} of this node so that its {@link Node2D#globalTransform()} will be equal to the given transform.
     * </p>
     * <p>
     *     If this node's parent is not a {@code Node2D}, this method is equivalent to {@link Node2D#setLocalTransform(Matrix2x3)}.
     * </p>
     *
     * @param transform Global transform as a 2x3 transformation matrix.
     * @throws NullPointerException If the given transform is {@code null}.
     */
    public final void setGlobalTransform(Matrix2x3 transform) {
        Objects.requireNonNull(transform, "Transform cannot be null");
        if (this.parent() instanceof Node2D parent) {
            this.setLocalTransform(parent.globalTransform().affineInverse().multiply(transform, 0.0f, 0.0f, 1.0f));
        } else {
            this.setLocalTransform(transform);
        }
    }

    /**
     * Sets the parent of this node.
     * <p>
     *     This method is equivalent to removing the node from the scene and adding it again to the given parent.
     *     Calls {@link Node#removeFromScene()} and {@link Node#addChild(Node)} in this order.
     * </p>
     * <p>
     *     If {@code keepTransform} is true, this node will preserve its global transform.
     * </p>
     *
     * @param parent The new parent of this node.
     * @param keepTransform True to preserve the global transform.
     * @throws NullPointerException If the given parent is {@code null}.
     * @throws IllegalArgumentException If this node cannot be added as a child of the given node.
     */
    public final void setParent(Node parent, boolean keepTransform) {
        if (keepTransform) {
            var transform = this.globalTransform();
            this.setParent(parent);
            this.setGlobalTransform(transform);
        } else {
            this.setParent(parent);
        }
    }

    /**
     * Transforms the given global position into a position in local coordinates relative to this node.
     *
     * @param globalPosition Position in global coordinates.
     * @return The given global position in local coordinates relative to this node.
     * @throws NullPointerException If the given position is {@code null}.
     * @see Node2D#toLocal(float, float)
     */
    public final Vector2 toLocal(Vector2 globalPosition) {
        Objects.requireNonNull(globalPosition, "Position cannot be null");
        return this.globalTransform().affineInverse().multiply(globalPosition, 1.0f);
    }

    /**
     * Transforms the given global position into a position in local coordinates relative to this node.
     *
     * @param x Position in global coordinates on the x axis.
     * @param y Position in global coordinates on the y axis,
     * @return The given global position in local coordinates relative to this node.
     * @see Node2D#toLocal(Vector2)
     */
    public final Vector2 toLocal(float x, float y) {
        return this.globalTransform().affineInverse().multiply(x, y, 1.0f);
    }

    /**
     * Transforms the given local position relative to this node into a position in global coordinates.
     *
     * @param localPosition Position in local coordinates relative to this node.
     * @return The given local position relative to this node in global coordinates.
     * @throws NullPointerException If the given position is {@code null}.
     * @see Node2D#toGlobal(float, float)
     */
    public final Vector2 toGlobal(Vector2 localPosition) {
        Objects.requireNonNull(localPosition, "Position cannot be null");
        return this.globalTransform().multiply(localPosition, 1.0f);
    }

    /**
     * Transforms the given local position relative to this node into a position in global coordinates.
     *
     * @param x Position in local coordinates on the x axis relative to this node.
     * @param y Position in local coordinates on the y axis relative to this node.
     * @return The given local position relative to this node in global coordinates.
     * @see Node2D#toGlobal(Vector2)
     */
    public final Vector2 toGlobal(float x, float y) {
        return this.globalTransform().multiply(x, y, 1.0f);
    }

    /**
     * Returns the angle between this node and the given point in radians.
     *
     * @param position Second position.
     * @return The angle between this node and the given point in radians.
     * @throws NullPointerException If the given position is {@code null}.
     */
    public final double angleTo(Vector2 position) {
        Objects.requireNonNull(position, "Position cannot be null");
        return this.toLocal(position).multiply(this.scale()).angle();
    }

    /**
     * Returns the angle between this node and the given point in radians.
     *
     * @param x Position on the x axis.
     * @param y Position on the y axis.
     * @return The angle between this node and the given point in radians.
     */
    public final double angleTo(float x, float y) {
        return this.toLocal(x, y).multiply(this.scale()).angle();
    }

    /**
     * Rotates this node so that it points towards the given position in global coordinates.
     *
     * @param position The point this node should look at in global coordinates.
     * @throws NullPointerException If the given position is {@code null}.
     */
    public final void lookAt(Vector2 position) {
        Objects.requireNonNull(position, "Position cannot be null");
        this.rotate(this.angleTo(position));
    }

    /**
     * Rotates this node so that it points towards the given position in global coordinates.
     *
     * @param x Position on the x axis.
     * @param y Position on the y axis.
     */
    public final void lookAt(float x, float y) {
        this.rotate(this.angleTo(x, y));
    }
}
