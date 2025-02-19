package io.github.ardentengine.core.resources;

import io.github.ardentengine.core.math.Vector3;
import io.github.ardentengine.core.rendering.RenderingApi;

import java.util.Objects;

public class BoxMesh extends Mesh {

    private Vector3 size = Vector3.ONE;

    public BoxMesh() {
        // Vertices are updated the first time the mesh is drawn
        this.allocateSpaceForVertices(24);
        // Create box indices
        var indices = RenderingApi.getInstance().createIntBuffer(36);
        indices.put(0).put(3).put(2).put(2).put(1).put(0);
        indices.put(4).put(5).put(6).put(6).put(7).put(4);
        indices.put(11).put(8).put(9).put(9).put(10).put(11);
        indices.put(12).put(13).put(14).put(14).put(15).put(12);
        indices.put(16).put(17).put(18).put(18).put(19).put(16);
        indices.put(20).put(21).put(22).put(22).put(23).put(20);
        this.setIndices(indices.flip());
        // Create box uvs
        var uvs = RenderingApi.getInstance().createFloatBuffer(48);
        uvs.put(0).put(0).put(0).put(1).put(1).put(1).put(1).put(0);
        uvs.put(0).put(0).put(0).put(1).put(1).put(1).put(1).put(0);
        uvs.put(0).put(0).put(0).put(1).put(1).put(1).put(1).put(0);
        uvs.put(0).put(0).put(0).put(1).put(1).put(1).put(1).put(0);
        uvs.put(0).put(0).put(0).put(1).put(1).put(1).put(1).put(0);
        uvs.put(0).put(0).put(0).put(1).put(1).put(1).put(1).put(0);
        this.setUVs(uvs.flip());
        // Create box normals
        var normals = RenderingApi.getInstance().createFloatBuffer(72);
        normals.put(0.0f).put(0.0f).put(-1.0f).put(0.0f).put(0.0f).put(-1.0f).put(0.0f).put(0.0f).put(-1.0f).put(0.0f).put(0.0f).put(-1.0f);
        normals.put(0.0f).put(0.0f).put(1.0f).put(0.0f).put(0.0f).put(1.0f).put(0.0f).put(0.0f).put(1.0f).put(0.0f).put(0.0f).put(1.0f);
        normals.put(-1.0f).put(0.0f).put(0.0f).put(-1.0f).put(0.0f).put(0.0f).put(-1.0f).put(0.0f).put(0.0f).put(-1.0f).put(0.0f).put(0.0f);
        normals.put(1.0f).put(0.0f).put(0.0f).put(1.0f).put(0.0f).put(0.0f).put(1.0f).put(0.0f).put(0.0f).put(1.0f).put(0.0f).put(0.0f);
        normals.put(0.0f).put(-1.0f).put(0.0f).put(0.0f).put(-1.0f).put(0.0f).put(0.0f).put(-1.0f).put(0.0f).put(0.0f).put(-1.0f).put(0.0f);
        normals.put(0.0f).put(1.0f).put(0.0f).put(0.0f).put(1.0f).put(0.0f).put(0.0f).put(1.0f).put(0.0f).put(0.0f).put(1.0f).put(0.0f);
        this.setNormals(normals.flip());
        // Vertices must be set the first time the mesh is drawn
        this.requestUpdate();
    }

    @Override
    void updateMesh() {
        var vertices = RenderingApi.getInstance().createFloatBuffer(72);
        vertices.put(-0.5f * this.size().x()).put(-0.5f * this.size().y()).put(-0.5f * this.size().z());
        vertices.put(0.5f * this.size().x()).put(-0.5f * this.size().y()).put(-0.5f * this.size().z());
        vertices.put(0.5f * this.size().x()).put(0.5f * this.size().y()).put(-0.5f * this.size().z());
        vertices.put(-0.5f * this.size().x()).put(0.5f * this.size().y()).put(-0.5f * this.size().z());
        vertices.put(-0.5f * this.size().x()).put(-0.5f * this.size().y()).put(0.5f * this.size().z());
        vertices.put(0.5f * this.size().x()).put(-0.5f * this.size().y()).put(0.5f * this.size().z());
        vertices.put(0.5f * this.size().x()).put(0.5f * this.size().y()).put(0.5f * this.size().z());
        vertices.put(-0.5f * this.size().x()).put(0.5f * this.size().y()).put(0.5f * this.size().z());
        vertices.put(-0.5f * this.size().x()).put(0.5f * this.size().y()).put(-0.5f * this.size().z());
        vertices.put(-0.5f * this.size().x()).put(-0.5f * this.size().y()).put(-0.5f * this.size().z());
        vertices.put(-0.5f * this.size().x()).put(-0.5f * this.size().y()).put(0.5f * this.size().z());
        vertices.put(-0.5f * this.size().x()).put(0.5f * this.size().y()).put(0.5f * this.size().z());
        vertices.put(0.5f * this.size().x()).put(-0.5f * this.size().y()).put(-0.5f * this.size().z());
        vertices.put(0.5f * this.size().x()).put(0.5f * this.size().y()).put(-0.5f * this.size().z());
        vertices.put(0.5f * this.size().x()).put(0.5f * this.size().y()).put(0.5f * this.size().z());
        vertices.put(0.5f * this.size().x()).put(-0.5f * this.size().y()).put(0.5f * this.size().z());
        vertices.put(-0.5f * this.size().x()).put(-0.5f * this.size().y()).put(-0.5f * this.size().z());
        vertices.put(0.5f * this.size().x()).put(-0.5f * this.size().y()).put(-0.5f * this.size().z());
        vertices.put(0.5f * this.size().x()).put(-0.5f * this.size().y()).put(0.5f * this.size().z());
        vertices.put(-0.5f * this.size().x()).put(-0.5f * this.size().y()).put(0.5f * this.size().z());
        vertices.put(0.5f * this.size().x()).put(0.5f * this.size().y()).put(-0.5f * this.size().z());
        vertices.put(-0.5f * this.size().x()).put(0.5f * this.size().y()).put(-0.5f * this.size().z());
        vertices.put(-0.5f * this.size().x()).put(0.5f * this.size().y()).put(0.5f * this.size().z());
        vertices.put(0.5f * this.size().x()).put(0.5f * this.size().y()).put(0.5f * this.size().z());
        this.updateVertices(0, vertices.flip());
        super.updateMesh();
    }

    public final Vector3 size() {
        return this.size;
    }

    public final void setSize(Vector3 size) {
        Objects.requireNonNull(size, "Size cannot be null");
        if (!this.size.equals(size)) {
            this.size = size;
            this.requestUpdate();
        }
    }

    public final void setSize(float x, float y, float z) {
        if (!this.size.equals(x, y, z)) {
            this.size = new Vector3(x, y, z);
            this.requestUpdate();
        }
    }
}
