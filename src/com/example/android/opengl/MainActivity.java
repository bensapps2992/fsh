package com.example.android.opengl;

import java.util.Random;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;


public class MainActivity extends Activity {

	// Our OpenGL Surfaceview
	public MyGLSurfaceView mGLView;
	private Handler handler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mGLView = new MyGLSurfaceView(this);
        
        setContentView(mGLView);
        mGLView.mRenderer.setCurrentLoop(mGLView.mRenderer.MAINMENU);
        handler.postDelayed(MainMenu,3000);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mGLView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mGLView.onResume();
	}
	
	private Runnable MainMenu = new Runnable(){
  	  @Override
  	  public void run() {
  		handler.postDelayed(MainMenu, 15);
		
  		mGLView.requestRender();
  	  }   
};

	private Runnable PlayGame = new Runnable(){
		@Override
		public void run() {
			handler.postDelayed(PlayGame, 15);
			mGLView.requestRender();
		}
	};
	
	

}
