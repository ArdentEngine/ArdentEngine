package io.github.ardentengine.core.rendering;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public abstract class VertexData {

    public abstract void setAttribute(int attribute, FloatBuffer data, int size);

    public abstract void allocateSpace(int attribute, int count, int size);

    public abstract void updateAttribute(int attribute, int offset, FloatBuffer data);

    public abstract void setIndices(IntBuffer indices);

    public abstract void setDrawMode(DrawMode drawMode);

    public abstract void draw(int count);

    public abstract void draw();
}
