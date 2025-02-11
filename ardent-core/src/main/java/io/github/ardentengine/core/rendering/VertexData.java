package io.github.ardentengine.core.rendering;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public abstract class VertexData {

    public abstract void setAttribute(int attribute, FloatBuffer data, int size);

    public abstract void setAttribute(int attribute, long bytes, int size);

    public abstract void updateAttribute(int attribute, long offset, FloatBuffer data);

    public abstract void setIndices(IntBuffer indices);

    public abstract void draw(int count);
}
