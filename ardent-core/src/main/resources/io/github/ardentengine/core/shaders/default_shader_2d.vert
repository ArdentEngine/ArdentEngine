#version 450

layout(location = 0) in vec2 in_vertex;
layout(location = 1) in vec2 in_uv;

out vec2 vertex;
out vec2 uv;

layout(std140, binding = 0) uniform Camera2D {
    mat3 view_matrix;
    mat4 projection_matrix;
};

void vertex_shader();

void main() {
    vertex = in_vertex;
    uv = in_uv;
#ifdef SHADER_TYPE
    vertex_shader();
#endif
    vec3 world_position = vec3(vertex, 1.0);
    gl_Position = projection_matrix * vec4(view_matrix * world_position, 1.0);
}
