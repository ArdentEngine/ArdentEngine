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

    public final void draw(Matrix3x4 transform, Material material) {
        // Update the mesh before drawing it if an update was requested
        if (this.dirty) {
            this.updateMesh();
            this.dirty = false;
        }
        // Queue the drawing of the mesh
        if (material instanceof ShaderMaterial shaderMaterial && shaderMaterial.shader() != null) {
            // Draw the mesh using the given shader if the given shader is a shader material
            RenderingSystem3D.batchDraw(this.vertexData, transform, shaderMaterial.shader().shaderProgram(), material);
        } else if (material instanceof Material3D) {
            // Draw the mesh using the given material if it is of the correct type
            RenderingSystem3D.batchDraw(this.vertexData, transform, material);
        } else {
            // Draw the mesh using the default material
            RenderingSystem3D.batchDraw(this.vertexData, transform);
        }
    }

    public final void draw(Matrix3x4 transform) {
        this.draw(transform, null);
    }
}
