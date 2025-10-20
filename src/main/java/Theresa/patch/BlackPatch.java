package Theresa.patch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class BlackPatch {
    public static boolean isTransitioning = false;
    private static float transitionDegree = 0F;
    private static float transitionTime = 0F;

    public static ShaderManager shaderManager = new ShaderManager();


    public static void beginShader(){
        isTransitioning = true;
        transitionTime = 0F;
        transitionDegree = 0F;
    }

    public static void stopShader(){
        isTransitioning = false;
        transitionTime = 0F;
        transitionDegree = 0F;
    }

    public static void render(SpriteBatch sb){
        if(isTransitioning){
            sb.setShader(shaderManager.bwShader);
            shaderManager.bwShader.setUniformf("u_time",transitionDegree);
            shaderManager.bwShader.setUniformf("u_maxContrast",1.5f);
            shaderManager.bwShader.setUniformf("u_maxBrightness",1.5f);
        }
        else {
            sb.setShader(null);
        }
    }

    public static void update(){
        if(isTransitioning){
            transitionTime += Gdx.graphics.getDeltaTime();
            if(transitionTime<=0.2F){
                transitionDegree = 2.5F*transitionTime;
            }
            else if(transitionTime<=1.2F){
                transitionDegree = 0.5F;
            }
            else if(transitionTime<=1.4F){
                transitionDegree = 2.5F*(transitionTime-1F);
            }
            else
                stopShader();
        }
    }

    public static class ShaderManager{
        public ShaderProgram bwShader;

        public ShaderManager(){

        }
    }
}
