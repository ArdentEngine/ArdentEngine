package io.github.ardentengine.core.resources;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Pattern;

public class ShaderLoader implements ResourceLoader {

    // TODO: The ShaderLoader must be different in Vulkan
    //  Shaders in Vulkan are compiled to SPIR-V when the project is compiled, no processing is done at runtime

    /**
     * Regex used to match the {@code #define SHADER_TYPE} preprocessor.
     * Used to get the shader type from the shader code.
     */
    private static final Pattern SHADER_TYPE_REGEX = Pattern.compile("#define\\s+SHADER_TYPE\\s+(\\w+)");

    /**
     * Map used to hold the builtin shader code.
     * Prevents builtin shaders from being loaded multiple times and allows the builtin rendering system to access the builtin shaders.
     */
    private static final HashMap<String, String> BUILTIN_SHADER_CODE = new HashMap<>();

    private static String loadShaderCode(String filePath) {
        // TODO: In Vulkan, this should return a byte[]
        try (var inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath)) {
            // Load the shader code if the shader file exists
            if (inputStream != null) {
                return new String(inputStream.readAllBytes());
            }
            // Return an empty string if the file isn't found to signal that the shader should use the builtin code for this stage
        } catch (IOException e) {
            System.err.println("Could not load shader file " + filePath);
            e.printStackTrace();
        }
        return "";
    }

    public static String getBuiltinShaderCode(String fileName) {
        // TODO: In Vulkan, this should return binary SPIR-V
        return BUILTIN_SHADER_CODE.computeIfAbsent(fileName, shaderFile -> loadShaderCode("io/github/ardentengine/core/shaders/" + shaderFile));
    }

    /**
     * Extracts the shader type from the given code.
     * <p>
     *     The code for all shader stages should be passed here.
     *     This method will return the first defined shader type.
     * </p>
     * <p>
     *     Returns an empty string if none of the given shaders define a shader type.
     *     In such cases, an error should be logged.
     * </p>
     *
     * @param shaders Shader code for each stage.
     * @return The shader type to use for this shader.
     */
    private static String getShaderType(String... shaders) {
        // Return the shader type found in the first shader that contains it
        for (var shaderCode : shaders) {
            // Skip empty shaders
            if (!shaderCode.isEmpty()) {
                var regex = SHADER_TYPE_REGEX.matcher(shaderCode);
                if(regex.find()) {
                    return regex.group(1);
                }
            }
        }
        // An empty shader type indicates that the shader code is invalid
        return "";
    }

    /**
     * Returns the final shader code that will be used in the shader.
     * <p>
     *     The resulting shader code may correspond to the builtin shader code or have the additional code written by the user.
     * </p>
     *
     * @param shaderCode The shader code written by the user for this stage.
     * @param builtinShader Name of the builtin shader file with the {@code .vert} or {@code .frag} extension.
     * @param functionName Name of the shader function. Must be either {@code vertex_shader} or {@code fragment_shader}.
     * @return The final shader code to be used in the shader.
     */
    private static String getFinalShaderCode(String shaderCode, String builtinShader, String functionName) {
        // Load the requested builtin shader
        builtinShader = getBuiltinShaderCode(builtinShader);
        // Use the builtin shader only if the shader code is empty
        if (shaderCode.isEmpty()) {
            return builtinShader;
        }
        // Put the user shader code into the builtin shader code
        return builtinShader.replaceFirst("void\\s+" + functionName + "\\s*\\(\\)\\s*;", shaderCode);
    }

    @Override
    public Object loadResource(String resourcePath) {
        // The ResourceLoader already checks if the path is valid
        var basePath = resourcePath.substring(0, resourcePath.lastIndexOf('.'));
        // Load the shader stages one by one
        var vertexCode = loadShaderCode(basePath + ".vert");
        var fragmentCode = loadShaderCode(basePath + ".frag");
        // Get the shader type from the first non-empty shader code
        var shaderType = getShaderType(vertexCode, fragmentCode);
        if (!shaderType.isEmpty()) {
            // Paste the shader code into the builtin one or use the builtin one only
            vertexCode = getFinalShaderCode(vertexCode, shaderType + ".vert", "vertex_shader");
            fragmentCode = getFinalShaderCode(fragmentCode, shaderType + ".frag", "fragment_shader");
            // Return the created shader if there was no error while loading it
            if (!vertexCode.isEmpty() && !fragmentCode.isEmpty()) {
                var shader = new Shader();
                shader.setVertexCode(vertexCode);
                shader.setFragmentCode(fragmentCode);
                shader.compile();
                return shader;
            }
            // TODO: Error here?
        } else {
            // Either the requested shader does not exist or it does not define a shader type
            System.err.println("Cannot get shader type from shader " + resourcePath);
        }
        return null;
    }

    @Override
    public String[] supportedExtensions() {
        return new String[] {".glsl"};
    }
}
