package io.github.ardentengine.opengl;

import io.github.ardentengine.core.EngineSystem;
import io.github.ardentengine.core.math.Color;
import io.github.ardentengine.core.math.Vector3;
import io.github.ardentengine.core.rendering.LightData;
import io.github.ardentengine.core.rendering.RenderingApi;
import io.github.ardentengine.core.rendering.UniformBuffer;

import java.util.WeakHashMap;

public class LightSystem extends EngineSystem {

    private static final WeakHashMap<OpenglLight, Object> LIGHTS = new WeakHashMap<>();
    private static final Object PRESENT = new Object();

    private final UniformBuffer lightBuffer = new OpenglUniformBuffer(3, 128 * 4 * 4 * Float.BYTES + Integer.BYTES); // TODO: Remove uniform buffers from the common module

    @Override
    protected void initialize() {

    }

    @Override
    protected void process() {
        var buffer = RenderingApi.getInstance().createFloatBuffer(4 * 4 * LIGHTS.size());
        var count = 0;
        for (var light : LIGHTS.keySet()) {
            if (light.enabled) {
                buffer.put(light.position.x()).put(light.position.y()).put(light.position.z()).put(0.0f);
                buffer.put(light.ambient.r()).put(light.ambient.g()).put(light.ambient.b()).put(light.ambient.a());
                buffer.put(light.diffuse.r()).put(light.diffuse.g()).put(light.diffuse.b()).put(light.diffuse.a());
                buffer.put(light.specular.r()).put(light.specular.g()).put(light.specular.b()).put(light.specular.a());
                count++;
            }
        }
        this.lightBuffer.put(buffer.flip());
        this.lightBuffer.put(RenderingApi.getInstance().createIntBuffer(1).put(count).flip(), 128 * 4 * 4 * Float.BYTES);
    }

    @Override
    protected void terminate() {

    }

    public static LightData createLight() {
        var light = new OpenglLight();
        LIGHTS.put(light, PRESENT);
        return light;
    }

    private static class OpenglLight extends LightData {

        private boolean enabled = false;
        private Vector3 position = Vector3.ZERO;
        private Color ambient = Color.WHITE;
        private Color diffuse = Color.WHITE;
        private Color specular = Color.WHITE;

        @Override
        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        @Override
        public void setPosition(Vector3 position) {
            this.position = position;
        }

        @Override
        public void setAmbient(Color ambient) {
            this.ambient = ambient;
        }

        @Override
        public void setDiffuse(Color diffuse) {
            this.diffuse = diffuse;
        }

        @Override
        public void setSpecular(Color specular) {
            this.specular = specular;
        }
    }
}
