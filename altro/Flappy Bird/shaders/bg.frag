#version 330 core

layout (location = 0) out vec4 color;
// specifica il colore del fragment in output

void main()
{
	color = vec4(0.2, 0.3, 0.8, 1.0); // blu
}