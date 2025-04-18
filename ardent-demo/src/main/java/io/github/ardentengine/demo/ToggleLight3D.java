package io.github.ardentengine.demo;

import io.github.ardentengine.core.input.InputEvent;
import io.github.ardentengine.core.input.InputEventKey;
import io.github.ardentengine.core.scene.PointLight3D;

public class ToggleLight3D extends PointLight3D {

    @Override
    protected void onInput(InputEvent event) {
        if (event.isPressed() && event instanceof InputEventKey eventKey && eventKey.key() == InputEventKey.KEY_0) {
            this.setEnabled(!this.enabled());
        }
        super.onInput(event);
    }
}
