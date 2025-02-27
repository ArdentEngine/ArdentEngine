package io.github.ardentengine.maven;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Mojo(name = "process-shaders", defaultPhase = LifecyclePhase.PROCESS_RESOURCES)
public class ProcessShadersMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project.build.resources}", readonly = true, required = true)
    private List<Resource> projectResources;

    @Parameter(defaultValue = "${project.build.outputDirectory}", readonly = true, required = true)
    private String outputDirectory;

    // TODO: Add an option to keep white spaces for easier debugging

    @Override
    public void execute() throws MojoFailureException {
        // Process all the project's resource directories
        for (var resource : this.projectResources) {
            var resourceDirectory = Path.of(resource.getDirectory());
            // Walk through the files in this resource directory
            try (var stream = Files.walk(resourceDirectory)) {
                stream.forEach(file -> {
                    try {
                        var fileString = file.toString();
                        var outputFile = Path.of(this.outputDirectory, resourceDirectory.relativize(file).toString());
                        if (fileString.endsWith(".vert") || fileString.endsWith(".frag")) {
                            // Trim code from builtin shaders
                            var shaderCode = Files.readString(file);
                            shaderCode = ShaderProcessor.trimCode(shaderCode);
                            shaderCode = ShaderProcessor.addConstants(shaderCode);
                            // Write the shader code to the output file
                            Files.writeString(outputFile, shaderCode);
                        } else if (fileString.endsWith(".glsl")) {
                            // Process shaders
                            var shaderCode = Files.readString(file);
                            var vertexCode = ShaderProcessor.extractVertexCode(shaderCode);
                            var fragmentCode = ShaderProcessor.extractFragmentCode(shaderCode);
                            // Write vertex and fragment code in separate files
                            if (!vertexCode.isEmpty()) {
                                Files.writeString(Path.of(outputFile.toString().replace(".glsl", ".vert")), vertexCode);
                            }
                            if (!fragmentCode.isEmpty()) {
                                Files.writeString(Path.of(outputFile.toString().replace(".glsl", ".frag")), fragmentCode);
                            }
                            // Delete the old glsl file
                            Files.delete(outputFile);
                        }
                        // TODO: Compile shaders to SPIR-V when using Vulkan
                    } catch (IOException e) {
                        throw new UncheckedIOException("Exception occurred while processing " + file, e);
                    }
                });
            } catch (IOException e) {
                throw new MojoFailureException("Exception occurred while processing " + resourceDirectory, e);
            }
        }
    }

    // TODO: Create a "shader validator" that causes the compilation to fail if a shader is invalid
}
