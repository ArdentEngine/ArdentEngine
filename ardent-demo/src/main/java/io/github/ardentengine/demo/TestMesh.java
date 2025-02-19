package io.github.ardentengine.demo;

import io.github.ardentengine.core.scene.MeshRenderer3D;

public class TestMesh extends MeshRenderer3D {

    @Override
    protected void onUpdate(float deltaTime) {
        this.rotate(0.0f, deltaTime, 0.0f);
        super.onUpdate(deltaTime);
    }
}
