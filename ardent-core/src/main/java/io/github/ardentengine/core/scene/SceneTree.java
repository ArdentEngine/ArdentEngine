package io.github.ardentengine.core.scene;

import io.github.ardentengine.core.ApplicationProperties;
import io.github.ardentengine.core.EngineSystem;
import io.github.ardentengine.core.input.InputEvent;
import io.github.ardentengine.core.input.InputSystem;
import io.github.ardentengine.core.resources.ResourceManager;
import io.github.ardentengine.core.resources.SceneData;

import java.util.Objects;

public class SceneTree extends EngineSystem {

    /** The root of the current scene. */
    private Node root = null;

    /** Time elapsed since the previous update in nanoseconds. */
    private long previousTime = 0;

    /** Scene to change to the next frame. */
    private SceneData nextScene = null;

    /**
     * Changes the current scene to a new instance of the given {@code SceneData}.
     * <p>
     *     Operations happen in the following order when the scene is changed:
     *     <ol>
     *         <li>
     *             The current scene is immediately removed from the tree by calling {@link Node#removeFromScene()} on the root node.
     *             From that point, {@link Node#sceneTree()} and {@link SceneTree#root()} will return {@code null}.
     *         </li>
     *         <li>
     *             At the beginning of the next frame, the new scene will be instantiated and added to the tree.
     *             The garbage collector will be hinted to run using {@link System#gc()} to remove the previous scene from memory.
     *         </li>
     *     </ol>
     *     This ensures there are never two scenes running at the same time.
     * </p>
     *
     * @param sceneData Data of the scene to change to.
     * @throws NullPointerException If the given scene is null.
     * @see SceneData#instantiate()
     */
    public final void changeScene(SceneData sceneData) {
        Objects.requireNonNull(sceneData, "The new scene cannot be null");
        if (this.root != null) {
            this.root.removeFromScene();
            this.root = null;
        }
        this.nextScene = sceneData;
    }

    /**
     * Changes the current scene to the one at the given path after loading it using {@link ResourceManager#getOrLoad(String)}.
     *
     * @param sceneFile Path to the scene file in the classpath.
     * @throws ClassCastException If the resource at the given path is not a {@link SceneData} resource.
     * @throws NullPointerException If the given resource does not exist.
     * @see SceneTree#changeScene(SceneData)
     */
    public final void changeScene(String sceneFile) {
        this.changeScene((SceneData) ResourceManager.getOrLoad(sceneFile));
    }

    @Override
    protected void initialize() {
        this.previousTime = System.nanoTime();
        this.changeScene((String) ApplicationProperties.get("application.run.firstScene"));
        InputSystem.setEventDispatchFunction(this::input);
    }

    @Override
    protected void process() {
        var time = System.nanoTime();
        // Instantiate the new scene at the beginning of this frame if it was changed in the previous frame
        if (this.nextScene != null) {
            // Instantiate the scene
            this.root = this.nextScene.instantiate();
            this.nextScene = null;
            // Run the garbage collector to clear the previous scene
            System.gc();
            // Add the new scene to the tree
            this.root.enterScene(this);
        }
        // Update the scene this frame
        if (this.root != null) {
            this.root.update((time - this.previousTime) / 1_000_000_000.0f);
        }
        this.previousTime = time;
    }

    private void input(InputEvent event) {
        if (this.root != null) {
            this.root.input(event);
        }
    }

    @Override
    protected void terminate() {
        if (this.root != null) {
            this.root.exitScene();
        }
    }

    /**
     * Returns the root of the current scene or null if no scene is running.
     *
     * @return The root of the current scene or null if no scene is running.
     */
    public final Node root() {
        return this.root;
    }
}
