#version 450

layout(location = 0) in vec3 in_vertex;
layout(location = 1) in vec2 in_uv;
layout(location = 2) in vec3 in_normal;

out vec3 vertex;
out vec2 uv;
out vec3 normal;

// TODO: Replace this with an 'in' variable when instanced rendering is implemented
uniform mat4 transform;

layout(std140, binding = 1) uniform Camera3D {
    mat4 view_matrix;
    mat4 projection_matrix;
};

void vertex_shader();

out vec3 world_position;
out vec3 camera_position;
out vec3 surface_normal;

void main() {
    vertex = in_vertex;
    uv = in_uv;
    normal = in_normal;
#ifdef SHADER_TYPE
    vertex_shader();
#endif
    world_position = (transform * vec4(vertex, 1.0)).xyz;
    camera_position = inverse(view_matrix)[3].xyz; // TODO: Find a better way to get the camera position
    surface_normal = normalize(transform * vec4(normal, 0.0)).xyz;
    gl_Position = projection_matrix * view_matrix * vec4(world_position, 1.0);
}
