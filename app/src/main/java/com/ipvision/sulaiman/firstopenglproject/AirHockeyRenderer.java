package com.ipvision.sulaiman.firstopenglproject;

import android.opengl.GLSurfaceView.Renderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.*;
import static android.opengl.GLUtils.*;
import static android.opengl.Matrix.*;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by sulaiman on 3/20/2017.
 */

public class AirHockeyRenderer implements Renderer {
    private static final int POSITION_COMPONENT_COUNT = 2;
    float[] tableVerticesWithTriangles = {
// Triangle 1
            0f, 0f,
            9f, 14f,
            0f, 14f,
// Triangle 2
            0f, 0f,
            9f, 0f,
            9f, 14f,
            // Line 1
            0f, 7f,
            9f, 7f,
// Mallets
            4.5f, 2f,
            4.5f, 12f
    };

    private static final int BYTES_PER_FLOAT = 4;
    private final FloatBuffer vertexData;



    public AirHockeyRenderer() {
        float[] tableVertices = {
                0f, 0f,
                0f, 14f,
                9f, 14f,
                9f, 0f
        };
        vertexData = ByteBuffer.allocateDirect(tableVerticesWithTriangles.length*BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexData.put(tableVerticesWithTriangles);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(1.f, 0f, 0f, 0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);
    }
}
