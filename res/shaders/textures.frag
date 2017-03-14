#version 400 core
out vec4 frag_color;

in float v_light;
in vec2 coords;

uniform sampler2D tex;

void main(void)
{

	frag_color = texture(tex, coords);

}