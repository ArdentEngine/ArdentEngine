package io.github.ardentengine.core.resources;

import io.github.ardentengine.core.math.Matrix3x4;
import io.github.ardentengine.core.rendering.RenderingApi;
import io.github.ardentengine.core.rendering.RenderingSystem3D;
import io.github.ardentengine.core.rendering.VertexData;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public abstract class Mesh {

    private final VertexData vertexData = RenderingApi.getInstance().createVertexData();

    private boolean dirty = false;

    protected final void setVertices(FloatBuffer vertices) {
        this.vertexData.setAttribute(0, vertices, 3);
    }

    protected final void allocateSpaceForVertices(int count) {
        this.vertexData.allocateSpace(0, count, 3);
    }

    protected final void updateVertices(int offset, FloatBuffer vertices) {
        this.vertexData.updateAttribute(0, offset, vertices);
    }

    protected final void setIndices(IntBuffer indices) {
        this.vertexData.setIndices(indices);
    }

    protected final void setUVs(FloatBuffer uvs) {
        this.vertexData.setAttribute(1, uvs, 2);
    }

    protected final void setNormals(FloatBuffer normals) {
        this.vertexData.setAttribute(2, normals, 3);
    }

    protected final void requestUpdate() {
        this.dirty = true;
    }

    protected void onUpdateMesh() {

    }

    void updateMesh() {
        this.onUpdateMesh();
    }

    public final void draw(Matrix3x4 transform) {
        if (this.dirty) {
            this.updateMesh();
            this.dirty = false;
        }
        RenderingSystem3D.batchDraw(this.vertexData, transform);
    }
}
