#version 330 core

in vec3 passColor;
in vec2 passTextureCoords;
in vec4 passColourOffset;

out vec4 outColor;

uniform sampler2D tex;

void main() {
    outColor = texture(tex, passTextureCoords) * passColourOffset;
}