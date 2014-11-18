uniform mat4 uMVPMatrix;
uniform mat4 uMVMatrix;
uniform mat4 tMatrix;

attribute vec4 a_Position;
attribute vec3 a_Normal;
attribute vec2 a_texCoord;

uniform float mAngle;

varying vec2 v_texCoord;
varying vec3 v_Position;
varying vec3 v_Normal;
void main() {
	mat4 testMatrix;
	float angle = clamp((a_Position.z+5.)/5.,0.,100.)*mAngle;
	float c = cos(angle);
	float s = sin(angle);
	vec4 col1 = vec4(c,0.,-s,0.);
	vec4 col2 = vec4(0.,1.,0.,0.);
	vec4 col3 = vec4(s,0,c,0.);
	vec4 col4 = vec4(0.,0.,0.,1.);
	testMatrix = mat4(col1,col2,col3,col4);
	v_Position = vec3(uMVMatrix * tMatrix *testMatrix* a_Position);
	v_Normal = vec3(uMVMatrix * vec4(a_Normal, 1.0));
	gl_Position = uMVPMatrix * tMatrix *testMatrix* a_Position;

	v_texCoord = a_texCoord;
}
