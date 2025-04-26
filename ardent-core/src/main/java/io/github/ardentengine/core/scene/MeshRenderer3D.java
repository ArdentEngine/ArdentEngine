package io.github.ardentengine.core.scene;

import io.github.ardentengine.core.resources.Material;
import io.github.ardentengine.core.resources.Mesh;

public class MeshRenderer3D extends Node3D {

    private Mesh mesh;
    private Material material;

    // TODO: Replace the 'update' method with a dedicated 'draw' method like it is done for 2d

    @Override
    void update(float deltaTime) {
        // TODO: Don't draw the mesh if it is not within the camera's frustum
        if (this.mesh != null) {
            this.mesh.draw(this.globalTransform(), this.material);
        }
        super.update(deltaTime);
    }
}
