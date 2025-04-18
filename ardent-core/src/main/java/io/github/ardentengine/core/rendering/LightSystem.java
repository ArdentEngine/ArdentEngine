package io.github.ardentengine.core.rendering;

import io.github.ardentengine.core.EngineSystem;
import io.github.ardentengine.core.scene.PointLight3D;

import java.util.WeakHashMap;

public class LightSystem extends EngineSystem {

    private static final WeakHashMap<PointLight3D, Object> ENABLED_LIGHTS = new WeakHashMap<>();
    private static final Object PRESENT = new Object();

    private final UniformBuffer lightBuffer = RenderingApi.getInstance().createUniformBuffer(3, 128 * 4 * 4 * Float.BYTES + Integer.BYTES);

    @Override
    protected void initialize() {

    }

    @Override
    protected void process() {
        var buffer = RenderingApi.getInstance().createFloatBuffer(4 * 4 * ENABLED_LIGHTS.size());
        var count = 0;
        for (var light : ENABLED_LIGHTS.keySet()) {
            var position = light.globalPosition();
            buffer.put(position.x()).put(position.y()).put(position.z()).put(0.0f);
            buffer.put(light.ambient().r()).put(light.ambient().g()).put(light.ambient().b()).put(light.ambient().a());
            buffer.put(light.diffuse().r()).put(light.diffuse().g()).put(light.diffuse().b()).put(light.diffuse().a());
            buffer.put(light.specular().r()).put(light.specular().g()).put(light.specular().b()).put(light.specular().a());
            count++;
        }
        this.lightBuffer.put(buffer.flip());
        this.lightBuffer.put(RenderingApi.getInstance().createIntBuffer(1).put(count).flip(), 128 * 4 * 4 * Float.BYTES);
    }

    @Override
    protected void terminate() {

    }

    /**
     * Enables the given light so that it will emit light.
     * <p>
     *     This method is equivalent to {@link PointLight3D#setEnabled(boolean)}
     * </p>
     *
     * @param light The light to enable.
     */
    public static void enableLight(PointLight3D light) {
        if (!light.enabled()) {
            // Keep consistency in the light's state
            light.setEnabled(true);
        } else if (light.isInsideScene()) {
            // Lights can only be enabled if they are inside the scene
            ENABLED_LIGHTS.put(light, PRESENT);
        }
    }

    /**
     * Disables the given light so that it will not emit light.
     * <p>
     *     This method is equivalent to {@link PointLight3D#setEnabled(boolean)}
     * </p>
     *
     * @param light The light to enable.
     */
    public static void disableLight(PointLight3D light) {
        if (light.enabled()) {
            // Keep consistency in the light's state
            light.setEnabled(false);
        } else if (light.isInsideScene()) {
            // The light cannot be enabled if it is not inside the scene
            ENABLED_LIGHTS.remove(light);
        }
    }

    // TODO: Add directional light and spot light as well
}
