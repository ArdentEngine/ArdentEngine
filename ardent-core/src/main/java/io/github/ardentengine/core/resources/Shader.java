package io.github.ardentengine.core.resources;

import io.github.ardentengine.core.rendering.RenderingApi;
import io.github.ardentengine.core.rendering.ShaderProgram;

public class Shader {

    private final ShaderProgram shaderProgram = RenderingApi.getInstance().createShader();

    // TODO: Do we want to allow users to change shaders at runtime?
    //  Should users set vertex and fragment code separately or should they be able to pass here the unprocessed ardent engine code?

    void setVertexCode(CharSequence code) {
        this.shaderProgram.setVertexCode(code);
    }

    void setFragmentCode(CharSequence code) {
        this.shaderProgram.setFragmentCode(code);
    }

    void compile() {
        this.shaderProgram.compile();
    }

    // TODO: Get list of shader uniforms
    //  This is simple in OpenGL because the code is a glsl string, but more difficult in Vulkan because the code is binary SPIR-V

    protected final ShaderProgram shaderProgram() {
        return this.shaderProgram;
    }
}
