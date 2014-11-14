precision mediump float;
varying vec2 v_texCoord;
varying vec3 v_Position;
varying vec3 v_Normal;
uniform sampler2D s_texture;
uniform vec3 u_LightPos;
void main() {

	float distance = length(v_Position - u_LightPos);
	vec3 lightVector = normalize(v_Position - u_LightPos);
	float diffuse = max(dot(v_Normal, lightVector), 0.0);
	diffuse = diffuse * (1.0 / (1.0 + (1. * distance * distance)));
	vec4 color = texture2D(s_texture,v_texCoord);
	float ambient = 0.1;
	color *= diffuse + ambient;
	color.xyz = clamp(color.xyz,0.,1.);
	gl_FragColor = vec4(color.xyz,1.0);
}
