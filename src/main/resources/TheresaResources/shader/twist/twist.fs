#ifdef GL_ES
precision mediump float;
#endif

//SpriteBatch will use texture unit 0
uniform sampler2D u_texture;//图片
uniform float degree;//进程 范围[0,1]

//"in" varyings from our vertex shader
varying vec4 v_color;
varying vec2 v_texCoord;


void main() {
    // 中心点坐标 (0.5, 0.5)
    vec2 center = vec2(0.5, 0.5);
    vec2 dir = v_texCoord - center;
    float distanceFromCenter = length(dir);

    // 1. 非线性裂变速度（使用5次方曲线实现加速）
    float dynamicDegree = min(degree / 0.9, 1.0); // 0~0.9阶段处理裂变
    float acceleratedProgress = pow(dynamicDegree, 5); // 裂变加速曲线

    // 2. 圆形透明区域计算
    float maxRadius = sqrt(2.0) * 0.5; // 最大半径（屏幕对角线的一半）
    float currentRadius = acceleratedProgress * maxRadius;

    // 3. 圆形透明度渐变（中心透明，边缘不透明）
    float transparency = 0.0;
    if (distanceFromCenter < currentRadius) {
        // 从中心的1透明度到边缘的0.5透明度
        transparency = smoothstep(0.0, currentRadius, distanceFromCenter) * 0.8;
    }

    // 4. 十字裂痕参数（可选保留）
    float crackWidth = mix(0.0, 0.6, acceleratedProgress);
    float crackFeather = crackWidth * 0.3;
    float distToCrackX = abs(dir.x) - crackWidth;
    float distToCrackY = abs(dir.y) - crackWidth;
    float crackDist = min(distToCrackX, distToCrackY);

    // 5. 黑色渲染区域（现在受圆形透明度影响）
    if (crackDist < -crackFeather) {
        vec4 color = vec4(0.0, 0.0, 0.0, 0.85 * transparency);
        if (degree > 0.8) {
            float fadeProgress = (degree - 0.8) / 0.2;
            color.a *= (1.0 - fadeProgress);
        }
        gl_FragColor = color;
        return;
    }

    // 6. 边缘光效果（平滑过渡）
    vec3 edgeGlow = vec3(0.0,0.0,0.0);
    if (crackDist < crackFeather) {
        float glowIntensity = 1.0 - smoothstep(-crackFeather, crackFeather, crackDist);
        edgeGlow = glowIntensity * mix(0.5, 1.0, acceleratedProgress) * vec3(1.0, 0.7, 0.3);
    }

    // 7. 画面扭曲（基于加速曲线）
    vec2 quadrant = sign(dir);
    float distortion = acceleratedProgress * 0.6;
    vec2 distortedUV = clamp(v_texCoord + dir * distortion * quadrant, 0.0, 1.0);

    // 8. 混合原始画面和扭曲画面
    vec4 color = mix(
        texture2D(u_texture, v_texCoord),
        texture2D(u_texture, distortedUV),
        acceleratedProgress
    );

    // 9. 添加边缘光效果
    color.rgb += edgeGlow;

    // 10. 0.8~1.0阶段渐隐到透明
    if (degree > 0.8) {
        float fadeProgress = (degree - 0.8) / 0.2;
        color.a *= (1.0 - fadeProgress);
    }

    gl_FragColor = color;
}