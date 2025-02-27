package io.github.ardentengine.core.rendering;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public abstract class UniformBuffer {

    // TODO: Uniform buffers may need to be removed from the core module

    public abstract void put(ByteBuffer data, long offset);

    public final void put(ByteBuffer data) {
        this.put(data, 0L);
    }

    public abstract void put(FloatBuffer data, long offset);

    public final void put(FloatBuffer data) {
        this.put(data, 0L);
    }

    public abstract void put(IntBuffer data, long offset);

    public final void put(IntBuffer data) {
        this.put(data, 0L);
    }
}
