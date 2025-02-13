package io.github.ardentengine.opengl;

import io.github.ardentengine.core.rendering.VertexData;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class OpenglVertexData extends VertexData {

    public static final int MAX_ATTRIBUTES = 16;

    private final int vertexArray;
    private final int[] vertexBuffers;

    public OpenglVertexData() {
        // Create a vertex array
        var vertexArray = GL30.glGenVertexArrays();
        // Each buffer corresponds to one vertex attribute for easier usage
        // The last buffer is reserved for the element buffer
        var vertexBuffers = new int[MAX_ATTRIBUTES + 1];
        // Register a cleaning action for this vertex array
        CleaningSystem.registerAction(this, () -> {
            // Delete all used vertex buffers
            for (var vertexBuffer : vertexBuffers) {
                if (vertexBuffer > 0) {
                    GL15.glDeleteBuffers(vertexBuffer);
                }
            }
            // Delete the vertex array
            GL30.glDeleteVertexArrays(vertexArray);
        });
        // Prevent the cleaning action from capturing a reference to this object
        this.vertexArray = vertexArray;
        this.vertexBuffers = vertexBuffers;
    }

    @Override
    public void setAttribute(int attribute, FloatBuffer data, int size) {
        // Throw an exception if the attribute is out of bounds
        if (attribute < 0 || attribute >= MAX_ATTRIBUTES) {
            throw new IndexOutOfBoundsException("Attribute " + attribute + " is out of bounds [0, " + MAX_ATTRIBUTES + ")");
        }
        // Bind the vertex array to register this attribute
        GL30.glBindVertexArray(this.vertexArray);
        // Check if a vertex buffer for this attribute has already been created
        if (this.vertexBuffers[attribute] == 0) {
            // Create a vertex buffer for this attribute
            this.vertexBuffers[attribute] = GL15.glGenBuffers();
            // Store the given data in the vertex buffer using static mode by default
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vertexBuffers[attribute]);
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
        } else {
            // Store the data using dynamic mode because since it has changed it is likely to change again
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vertexBuffers[attribute]);
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_DYNAMIC_DRAW);
        }
        // Specify how the data is stored in the buffer
        GL20.glVertexAttribPointer(attribute, size, GL11.GL_FLOAT, false, 0, 0);
        // Enable the attribute for it to be used the next time the vertex array is bound
        GL20.glEnableVertexAttribArray(attribute);
    }

    @Override
    public void setAttribute(int attribute, long bytes, int size) {
        // Throw an exception if the attribute is out of bounds
        if (attribute < 0 || attribute >= MAX_ATTRIBUTES) {
            throw new IndexOutOfBoundsException("Attribute " + attribute + " is out of bounds [0, " + MAX_ATTRIBUTES + ")");
        }
        // Bind the vertex array to register this attribute
        GL30.glBindVertexArray(this.vertexArray);
        // Create a vertex buffer for this attribute if it has not been used yet
        if (this.vertexBuffers[attribute] == 0) {
            this.vertexBuffers[attribute] = GL15.glGenBuffers();
        }
        // Allocate space for this attribute using dynamic mode because it is likely not going to be left uninitialized
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vertexBuffers[attribute]);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, bytes, GL15.GL_DYNAMIC_DRAW);
        // Specify how the data is stored in the buffer
        GL20.glVertexAttribPointer(attribute, size, GL11.GL_FLOAT, false, 0, 0);
        // Enable the attribute for it to be used the next time the vertex array is bound
        GL20.glEnableVertexAttribArray(attribute);
    }

    // TODO: Add a check for when updateAttribute tries to write data past the end of the buffer

    @Override
    public void updateAttribute(int attribute, long offset, FloatBuffer data) {
        // Throw an exception if the attribute is out of bounds
        if (attribute < 0 || attribute >= MAX_ATTRIBUTES) {
            throw new IndexOutOfBoundsException("Attribute " + attribute + " is out of bounds [0, " + MAX_ATTRIBUTES + ")");
        }
        // Bind the vertex array to update the attribute
        GL30.glBindVertexArray(this.vertexArray);
        // Assume the buffer has already been created
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.vertexBuffers[attribute]);
        // Store the given data in the buffer
        GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, offset, data);
    }

    @Override
    public void setIndices(IntBuffer indices) {
        // Bind the vertex array to update the element buffer
        GL30.glBindVertexArray(this.vertexArray);
        // Check if an element buffer has already been created
        if (this.vertexBuffers[MAX_ATTRIBUTES] == 0) {
            // Create an element buffer to store the indices
            this.vertexBuffers[MAX_ATTRIBUTES] = GL15.glGenBuffers();
            // Store the indices using static mode by default
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.vertexBuffers[MAX_ATTRIBUTES]);
            GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);
        } else {
            // Store the indices using dynamic mode because since they have changed they are likely to change again
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.vertexBuffers[MAX_ATTRIBUTES]);
            GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_DYNAMIC_DRAW);
        }
    }

    // TODO: Allow different draw modes

    @Override
    public void draw(int count) {
        // Bind the vertex array before drawing
        GL30.glBindVertexArray(this.vertexArray);
        // Check if the element buffer has been created
        if (this.vertexBuffers[MAX_ATTRIBUTES] > 0) {
            // Use draw elements if indices are used
            GL15.glDrawElements(GL11.GL_TRIANGLES, count, GL11.GL_UNSIGNED_INT, 0);
        } else {
            // Use draw arrays if indices are not used
            GL15.glDrawArrays(GL11.GL_TRIANGLES, 0, count);
        }
    }
}
