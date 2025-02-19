package io.github.ardentengine.core.resources;

import io.github.ardentengine.core.rendering.RenderingApi;

public class ArrayMesh extends Mesh {

    public final void setVertices(float[] vertices) {
        this.setVertices(RenderingApi.getInstance().createFloatBuffer(vertices.length).put(vertices).flip());
    }

    public final void setIndices(int[] indices) {
        this.setIndices(RenderingApi.getInstance().createIntBuffer(indices.length).put(indices).flip());
    }

    public final void setUVs(float[] uvs) {
        this.setUVs(RenderingApi.getInstance().createFloatBuffer(uvs.length).put(uvs).flip());
    }

    public final void setNormals(float[] normals) {
        this.setNormals(RenderingApi.getInstance().createFloatBuffer(normals.length).put(normals).flip());
    }

    // TODO: Allow vertex colors and custom attributes
}
