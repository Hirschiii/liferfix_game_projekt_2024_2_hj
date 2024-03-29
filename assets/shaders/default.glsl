#type vertex
#version 330 core

layout (location = 0) in vec3 aPos;
layout (location = 1) in vec4 aColor;
layout (location = 2) in vec2 aTexCoords;
layout (location = 3) in float aTexId;
layout (location = 4) in float aEntityId;
layout (location = 5) in float aApplyOutline;

uniform mat4 uProjection;
uniform mat4 uView;

out vec4 fColor;
out vec2 fTexCoords;
out float fTexId;
flat out float fApplyOutline;

void main(){
	fColor = aColor;
	fTexCoords = aTexCoords;
	fTexId = aTexId;
	fApplyOutline = aApplyOutline;

	gl_Position = uProjection * uView * vec4(aPos, 1.0);
}

#type fragment
#version 330 core

in vec4 fColor;
in vec2 fTexCoords;
in float fTexId;
in float fApplyOutline;

// float border_width = 0.1;
// float aspect = 1;

uniform sampler2D uTextures[8];

out vec4 color;
void main() {
	// color = texture(TEX_SAMPLER, fTexCoords);
	if (fTexId > 0) {
		int id = int(fTexId);
		// if(fApplyOutline > 0.5) {
		// 	float maxX = 1.0 - border_width;
		// 	float minX = border_width;
		// 	float maxY = maxX / aspect;
		// 	float minY = minX / aspect;
		//
		// 	if (fTexCoords.x < maxX && fTexCoords.x > minX &&
		// 			fTexCoords.y < maxY && fTexCoords.y > minY) {
		// 		// gl_FragColor = **rect color**;
		// 		color = fColor * texture(uTextures[id], fTexCoords);
		// 	} else {
		// 		// gl_FragColor = **border color**;
		// 		color = vec4(0.0, 0.0, 0.0, 1.0);
		// 	}
		// } else {
        switch (id) {
            case 0:
                color = fColor * texture(uTextures[0], fTexCoords);
            break;
            case 1:
                color = fColor * texture(uTextures[1], fTexCoords);
            break;
            case 2:
                color = fColor * texture(uTextures[2], fTexCoords);
            break;
            case 3:
                color = fColor * texture(uTextures[3], fTexCoords);
            break;
            case 4:
                color = fColor * texture(uTextures[4], fTexCoords);
            break;
            case 5:
                color = fColor * texture(uTextures[5], fTexCoords);
            break;
            case 6:
                color = fColor * texture(uTextures[6], fTexCoords);
            break;
            case 7:
                color = fColor * texture(uTextures[7], fTexCoords);
            break;
		}
		// }
		// color = vec4(fTexCoords, 0, 1);
	} else {
		color = fColor;
	}
}
