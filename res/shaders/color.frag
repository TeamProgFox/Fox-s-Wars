#version 400 core
out vec4 frag_color;
in vec4 v_colors;
in float v_light;
void main(void)
{

	frag_color = vec4(v_colors.x, v_colors.y, v_colors.z, v_colors.w) * v_light;

}