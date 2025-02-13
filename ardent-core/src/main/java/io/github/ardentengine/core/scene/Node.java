package io.github.ardentengine.core.scene;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {

    private final ArrayList<Node> children = new ArrayList<>();
    private Node parent = null;

    private SceneTree sceneTree = null;

    protected void onEnter() {

    }

    void enterScene(SceneTree sceneTree) {
        for (var child : this.children) {
            child.enterScene(sceneTree);
        }
        this.sceneTree = sceneTree;
        this.onEnter();
    }

    protected void onUpdate(float deltaTime) {

    }

    void update(float deltaTime) {
        for (var child : this.children) {
            child.update(deltaTime);
        }
        this.onUpdate(deltaTime);
    }

    protected void onExit() {

    }

    void exitScene() {
        for (var child : this.children) {
            child.exitScene();
        }
        this.onExit();
        this.sceneTree = null;
    }

    public final boolean isInsideScene() {
        return this.sceneTree != null;
    }

    public final SceneTree sceneTree() {
        return this.sceneTree;
    }

    public final Node parent() {
        return this.parent;
    }

    private void checkValidChild(Node node) {
        Objects.requireNonNull(node, "Cannot add null as a child of " + this);
        if (node == this) {
            // The child cannot be the same node
            throw new IllegalArgumentException("Cannot add " + this + " as a child of itself");
        } else if (node.parent != null) {
            // A child node cannot be added if it already has a parent
            throw new IllegalArgumentException("Cannot add " + node + " as a child of " + this + " because it already has parent " + node.parent);
        } else if (node.isInsideScene()) {
            // The only node inside the scene tree to not have a parent is the root
            throw new IllegalArgumentException("Cannot add the root node as a child of " + this);
        } else if (!this.isInsideScene()) {
            // The given child might be an ancestor of this node if they are not inside the scene tree
            // If this node is inside the scene tree its ancestors already have a parent
            var parent = this.parent;
            while (parent != null) {
                if (parent == node) {
                    throw new IllegalArgumentException("Node " + node + " is an ancestor of " + this);
                }
                parent = parent.parent;
            }
        }
    }

    public final void addChild(Node node) {
        this.checkValidChild(node);
        this.children.add(node);
        node.parent = this;
        if(this.isInsideScene()) {
            node.enterScene(this.sceneTree);
        }
    }

    public final List<Node> children() {
        return new ArrayList<>(this.children);
    }

    public final int childCount() {
        return this.children.size();
    }
}
