package io.github.ardentengine.demo;

import io.github.ardentengine.core.Application;
import io.github.ardentengine.core.input.InputEvent;
import io.github.ardentengine.core.input.InputEventKey;
import io.github.ardentengine.core.scene.Node;

import java.util.Objects;

public class TestScene extends Node {

    private String nextScene = "";

    @Override
    protected void onEnter() {
        System.out.println("Enter test scene");
    }

    @Override
    protected void onInput(InputEvent event) {
        if (event.isPressed() && !event.isEcho() && event instanceof InputEventKey eventKey) {
            if (eventKey.key() == InputEventKey.KEY_ENTER && !this.nextScene().isEmpty()) {
                this.sceneTree().changeScene(this.nextScene());
            } else if (eventKey.key() == InputEventKey.KEY_ESCAPE) {
                Application.quit();
            }
        }
    }

    @Override
    protected void onExit() {
        System.out.println("Exit test scene");
    }

    public final String nextScene() {
        return this.nextScene;
    }

    public final void setNextScene(String nextScene) {
        this.nextScene = Objects.requireNonNull(nextScene, "Next scene cannot be null");
    }
}
