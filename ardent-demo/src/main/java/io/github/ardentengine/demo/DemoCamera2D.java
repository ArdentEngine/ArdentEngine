package io.github.ardentengine.demo;

import io.github.ardentengine.core.input.Input;
import io.github.ardentengine.core.scene.Camera2D;

public class DemoCamera2D extends Camera2D {

    public float speed = 80.0f;

    @Override
    public void onUpdate(float deltaTime) {
        var input = Input.getVector("up", "down", "left", "right");
        this.translate(input.multiply(this.speed * deltaTime));
        super.onUpdate(deltaTime);
    }
}