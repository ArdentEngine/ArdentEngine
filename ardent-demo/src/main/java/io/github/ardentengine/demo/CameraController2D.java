package io.github.ardentengine.demo;

import io.github.ardentengine.core.input.InputEvent;
import io.github.ardentengine.core.input.InputEventScroll;
import io.github.ardentengine.core.input.InputSystem;
import io.github.ardentengine.core.scene.Camera2D;

public class CameraController2D extends Camera2D {

    private float speed = 160.0f;

    @Override
    protected void onUpdate(float deltaTime) {
        var movementInput = InputSystem.getVector("up", "down", "left", "right");
        this.translate(movementInput.multiply(deltaTime * this.speed));
        super.onUpdate(deltaTime);
    }

    @Override
    protected void onInput(InputEvent event) {
        if (event instanceof InputEventScroll eventScroll) {
            this.setZoom(this.zoom().plus(eventScroll.vertical(), eventScroll.vertical()));
        }
        super.onInput(event);
    }

    public final void setSpeed(float speed) {
        this.speed = speed;
    }

    public final float speed() {
        return this.speed;
    }
}
