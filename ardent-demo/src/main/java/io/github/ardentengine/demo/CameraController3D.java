package io.github.ardentengine.demo;

import io.github.ardentengine.core.display.CursorMode;
import io.github.ardentengine.core.display.DisplayApi;
import io.github.ardentengine.core.input.InputEvent;
import io.github.ardentengine.core.input.InputEventMouseMotion;
import io.github.ardentengine.core.input.InputSystem;
import io.github.ardentengine.core.math.MathUtils;
import io.github.ardentengine.core.scene.Camera3D;

public class CameraController3D extends Camera3D {

    private float speed = 8.0f;
    private float mouseSensitivity = 0.005f;

    @Override
    protected void onEnter() {
        DisplayApi.getInstance().getMainWindow().setCursorMode(CursorMode.CAPTURED);
        super.onEnter();
    }

    @Override
    protected void onUpdate(float deltaTime) {
        var horizontalInput = InputSystem.getVector("up", "down", "left", "right");
        var verticalInput = InputSystem.getAxis("space", "shift");
        var horizontalMovement = horizontalInput.rotated(-this.rotation().y()).multiply(this.speed * deltaTime);
        var verticalMovement = verticalInput * this.speed * deltaTime;
        this.translate(horizontalMovement.x(), verticalMovement, -horizontalMovement.y()); // TODO: Why is z backwards?
        super.onUpdate(deltaTime);
    }

    @Override
    protected void onInput(InputEvent event) {
        if (event instanceof InputEventMouseMotion mouseMotion) {
            this.rotate(mouseMotion.y() * this.mouseSensitivity, mouseMotion.x() * this.mouseSensitivity, 0.0f);
            this.setRotation(MathUtils.clamp(this.rotation().x(), (float) (-Math.PI / 2.0), (float) (Math.PI / 2.0)), this.rotation().y(), this.rotation().z());
        }
        super.onInput(event);
    }

    @Override
    protected void onExit() {
        DisplayApi.getInstance().getMainWindow().setCursorMode(CursorMode.VISIBLE);
        super.onExit();
    }

    public final float speed() {
        return this.speed;
    }

    public final void setSpeed(float speed) {
        this.speed = speed;
    }

    public final float mouseSensitivity() {
        return this.mouseSensitivity;
    }

    public final void setMouseSensitivity(float mouseSensitivity) {
        this.mouseSensitivity = mouseSensitivity;
    }
}
