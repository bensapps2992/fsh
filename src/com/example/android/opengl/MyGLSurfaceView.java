package com.example.android.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class MyGLSurfaceView extends GLSurfaceView {

	public MyGLRenderer mRenderer;
	
	public MyGLSurfaceView(Context context) {
        super(context);

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);
        setEGLConfigChooser( true );

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new MyGLRenderer(context);
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

	@Override
	public void onPause() {
		super.onPause();
		mRenderer.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		mRenderer.onResume();
	}
	
	//put in touch controls
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		if(e.getActionMasked() == MotionEvent.ACTION_MOVE)
		{
			mRenderer.setAngle((float)e.getX());
		}
		return true;
	}

}
