package com.ipvision.sulaiman.firstopenglproject.utils;

import android.util.Log;

import static android.opengl.GLES20.*;

/**
 * Created by sulaiman on 3/21/2017.
 */

public class ShaderHelper {
    private static final String TAG = "ShaderHelper";

    public static int compileVertexShader(String shaderCode) {
        return compileShader(GL_VERTEX_SHADER, shaderCode);
    }

    public static int compileFragmentShader(String shaderCode) {
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }

    private static int compileShader(int type, String shaderCode) {
        final int shaderObjectId = glCreateShader(type); // Create a shader object, return o if can't create the resource
        //this shaderObecjID is the reference of our OpenGL object


        if (shaderObjectId == 0) {
            if (LoggerConfig.ON) {
                Log.w(TAG, "Could not create new shader.");
            }
            return 0;
        }
        glShaderSource(shaderObjectId, shaderCode); //this line upload the shader source code to the shader object
        glCompileShader(shaderObjectId); // Now we can complile the shader object
        final int[] compileStatus = new int[1];
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0); //This will add teh status of compile of source shader code to compileStatus

        if (LoggerConfig.ON) {
            Log.v(TAG, "Results of compiling source:" + "\n" + shaderCode + "\n:" + glGetShaderInfoLog(shaderObjectId));
        }
        if (compileStatus[0] == 0) {
            // If it failed, delete the shader object.
            glDeleteShader(shaderObjectId);
            if (LoggerConfig.ON) {
                Log.w(TAG, "Compilation of shader failed.");
            }
            return 0;
        }

        return shaderObjectId;
    }


    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {


        return 0;
    }


}
