#version 330 core

// il vertex shader recupera due attributi (VERTEX_ATTRIB e TCOORD_ATTRIB, dichiarati in 'Shader.java')
// 'location = 0' è riferita a VERTEX_ATTRIB, 'in' perché serve come input al programma, 'vec4' è il formato; alla posizione 4 viene assegnato un valore di default, cioè 1.0.
// 'location = 1' è riferita a TCOORD_ATTRIB, 'in' perché input, 'vec2' è il formato

layout (location = 0) in vec4 position; 
layout (location = 1) in vec2 tc; 

// la variabile Uniform 'pr_matrix', dichiarata in 'Main.init()', contiene la Projection Matrix 
uniform mat4 pr_matrix;

void main()
{
	gl_Position = pr_matrix * position;
}