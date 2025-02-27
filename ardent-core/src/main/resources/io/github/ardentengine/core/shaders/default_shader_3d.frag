#version 450

in vec3 vertex;
in vec2 uv;
in vec3 normal;

layout(std140, binding = 2) uniform Material {
    vec3 diffuse;
};

out vec4 frag_color;

void main() {
//    frag_color = vec4(vertex + 0.5, 1.0);
    frag_color = vec4(diffuse, 1.0);
}

// TODO: Implement lighting
