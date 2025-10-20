#ifdef GL_ES
precision mediump float;
#endif

//SpriteBatch will use texture unit 0
uniform sampler2D u_texture;
uniform float progress;          // 渐变进度（0.0 → 1.0）
uniform float u_maxContrast;   // 目标对比度
uniform float u_maxBrightness; // 目标亮度

//"in" varyings from our vertex shader
varying vec4 v_color;
varying vec2 v_texCoord;

void main() {
    vec4 color = texture2D(u_texture, v_texCoord);

    // 灰度计算
    float gray = dot(color.rgb, vec3(0.299, 0.587, 0.114));

    //暂不应用测试
    vec3 tmpColor = vec3(gray,gray,gray);
    float contrast = u_maxContrast;
    float brightness = u_maxBrightness;

    // 应用对比度和亮度
    tmpColor = (tmpColor - 0.5) * contrast + 0.5; // 对比度公式
    tmpColor += brightness;             // 亮度叠加

    // 确保颜色在 [0,1] 范围内
    tmpColor = clamp(tmpColor, 0.0, 1.0);
    float alpha = color.a * progress;

    gl_FragColor = vec4(tmpColor, alpha);
}
