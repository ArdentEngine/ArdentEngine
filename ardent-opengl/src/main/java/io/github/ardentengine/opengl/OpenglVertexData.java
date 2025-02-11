package io.github.ardentengine.opengl;

import io.github.ardentengine.core.rendering.VertexData;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class OpenglVertexData extends VertexData {

    private final int vertexArray;
    private final int vertexBuffer;
    private final int elementBuffer;

    public OpenglVertexData() {
        var vertexArray = GL30.glGenVertexArrays();
        var vertexBuffer = GL15.glGenBuffers();
        var elementBuffer = GL15.glGenBuffers();
        ResourceCleaner.registerAction(this, () -> {
            GL15.glDeleteBuffers(vertexBuffer);
            GL15.glDeleteBuffers(elementBuffer);
            GL30.glDeleteVertexArrays(vertexArray);
        });
        this.vertexArray = vertexArray;
        this.vertexBuffer = vertexBuffer;
        this.elementBuffer = elementBuffer;
    }

    // TODO: Allow different store modes to be chosen

    // TODO: Allow to use more attributes

    @Override
    public void setAttribute(int attribute, FloatBuffer data, int size) {
        GL30.glBindVertexArray(this.vertexArray);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vertexBuffer);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_DYNAMIC_DRAW);
        GL20.glVertexAttribPointer(attribute, size, GL11.GL_FLOAT, false, 0, 0);
        GL20.glEnableVertexAttribArray(attribute);
    }

    @Override
    public void setAttribute(int attribute, long bytes, int size) {
        GL30.glBindVertexArray(this.vertexArray);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vertexBuffer);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, bytes, GL15.GL_DYNAMIC_DRAW);
        GL20.glVertexAttribPointer(attribute, size, GL11.GL_FLOAT, false, 0, 0);
        GL20.glEnableVertexAttribArray(attribute);
    }

    @Override
    public void updateAttribute(int attribute, long offset, FloatBuffer data) {
        GL30.glBindVertexArray(this.vertexArray);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vertexBuffer);
        GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, offset, data);
    }

    @Override
    public void setIndices(IntBuffer indices) {
        GL30.glBindVertexArray(this.vertexArray);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.elementBuffer);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_DYNAMIC_DRAW);
    }

    @Override
    public void draw(int count) {
        GL30.glBindVertexArray(this.vertexArray);
        // TODO: Choose either glDrawArrays or glDrawElements
//        GL15.glDrawArrays(GL11.GL_TRIANGLES, 0, count);
        GL15.glDrawElements(GL11.GL_TRIANGLES, count, GL11.GL_UNSIGNED_INT, 0);
    }
}
