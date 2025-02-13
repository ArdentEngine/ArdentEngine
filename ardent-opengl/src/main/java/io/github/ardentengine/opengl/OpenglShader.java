package io.github.ardentengine.opengl;

import io.github.ardentengine.core.rendering.ShaderProgram;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.util.HashMap;
import java.util.Map;

public class OpenglShader extends ShaderProgram {

    private final int program;
    private final Map<Integer, Integer> shaders;

    public OpenglShader() {
        var program = GL20.glCreateProgram();
        var shaders = new HashMap<Integer, Integer>();
        CleaningSystem.registerAction(this, () -> {
            for(var shader : shaders.values()) {
                GL20.glDetachShader(program, shader);
                GL20.glDeleteShader(shader);
            }
            GL20.glDeleteProgram(program);
        });
        this.program = program;
        this.shaders = shaders;
    }

    private int getShader(int type) {
        // Create a new shader for this type or return the already existing one
        return this.shaders.computeIfAbsent(type, GL20::glCreateShader);
    }

    private void setShaderCode(int type, CharSequence code) {
        var shader = this.getShader(type);
        GL20.glShaderSource(shader, code);
        GL20.glCompileShader(shader);
        if(GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            // TODO: Better compilation output
            System.out.println(GL20.glGetShaderInfoLog(shader));
        }
        GL20.glAttachShader(this.program, shader);
    }

    @Override
    public void setVertexCode(CharSequence code) {
        this.setShaderCode(GL20.GL_VERTEX_SHADER, code);
    }

    @Override
    public void setFragmentCode(CharSequence code) {
        this.setShaderCode(GL20.GL_FRAGMENT_SHADER, code);
    }

    @Override
    public void compile() {
        GL20.glLinkProgram(this.program);
        GL20.glValidateProgram(this.program);
    }

    @Override
    public void start() {
        GL20.glUseProgram(this.program);
    }
}
