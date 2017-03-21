package com.ipvision.sulaiman.firstopenglproject;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;

import com.ipvision.sulaiman.firstopenglproject.utils.LoggerConfig;
import com.ipvision.sulaiman.firstopenglproject.utils.ShaderHelper;
import com.ipvision.sulaiman.firstopenglproject.utils.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;


/**
 * Created by sulaiman on 3/20/2017.
 */

public class AirHockeyRenderer implements Renderer {
    private static final String A_POSITION = "a_Position";
    private int aPositionLocation;
    private static final String U_COLOR = "u_Color";
    private int uColorLocation;
    private int program;
    private final Context mContext;
    private static final int POSITION_COMPONENT_COUNT = 2;
    float[] tableVerticesWithTriangles = {
// Triangle 1
            -0.5f, -0.5f,
            0.5f, 0.5f,
            -0.5f, 0.5f,
// Triangle 2
            -0.5f, -0.5f,
            0.5f, -0.5f,
            0.5f, 0.5f,
// Line 1
            -0.5f, 0f,
            0.5f, 0f,
// Mallets
            0f, -0.25f,
            0f, 0.25f
    };
    private static final int BYTES_PER_FLOAT = 4;
    private final FloatBuffer vertexData;


    public AirHockeyRenderer(Context context) {
        mContext = context;
        float[] tableVertices = {
                0f, 0f,
                0f, 14f,
                9f, 14f,
                9f, 0f
        };
        vertexData = ByteBuffer.allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexData.put(tableVerticesWithTriangles);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.f, 0f, 0f, 0f);
        String vertexShaderResource = TextResourceReader.readTextFileFromResource(mContext, R.raw.simple_vertex_shader);
        String fragmentShaderResource = TextResourceReader.readTextFileFromResource(mContext, R.raw.simple_fragment_shader);
        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderResource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderResource);
        program = ShaderHelper.linkProgram(vertexShader, fragmentShader);

        if (LoggerConfig.ON) {
            ShaderHelper.validateProgram(program);
        }
        glUseProgram(program); // now we will use this program as default program which linked our vertex shader and fragment shader
        uColorLocation = glGetUniformLocation(program, U_COLOR); //get uniform location of the fragment shader
        aPositionLocation = glGetAttribLocation(program, A_POSITION); //get attribute location of vertices shader

        vertexData.position(0); //reset buffer pointer to start of the buffer
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexData);
        //tell OpenGL that it can find the data for a_Position in the buffer vertexData
        glEnableVertexAttribArray(aPositionLocation); //Now enabling Vertex, With this final call, OpenGL now knows where to find all the data it needs


    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);
        glUniform4f(uColorLocation, 1f, 1f, 1f, 1f);
        glDrawArrays(GL_TRIANGLES, 0, 6);
        glUniform4f(uColorLocation, 0f, 0f, 1f, 1f);
        glDrawArrays(GL_LINES, 6, 2);

        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_POINTS, 8, 1);
// Draw the second mallet red.
        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_POINTS, 9, 1);
    }
}
