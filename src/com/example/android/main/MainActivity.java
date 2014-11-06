package com.example.android.main;

import com.example.android.screens.mainmenu.MenuTouch;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.app.Activity;
import android.content.Context;


public class MainActivity extends Activity {

	// Our OpenGL Surfaceview
	private static MyGLSurfaceView mGLView;
	private Handler handler = new Handler();
	private Context mContext;
	
	public static MyGLSurfaceView getmGLView()
	{
		return mGLView;
	}
	
	private Runnable getRunnable(int loop)
	{
		switch(loop)
		{
		case MyGLRenderer.MAINMENU:
			return MainMenu;
		case MyGLRenderer.PLAYGAME:
			return PlayGame;
		case MyGLRenderer.DESIGNER:
			return Designer;
		case MyGLRenderer.MOREAPPS:
			return MoreApps;
		case MyGLRenderer.OPTIONS:
			return Options;
		default:
			return MainMenu;	
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		mGLView = new MyGLSurfaceView(this);
		MenuTouch.initTouch(mGLView,this);
        setContentView(mGLView);
        mGLView.mRenderer.setCurrentLoop(MyGLRenderer.MAINMENU);
        handler.postDelayed(getRunnable(mGLView.mRenderer.getCurrentLoop()),3000);
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
  		handler.postDelayed(getRunnable(mGLView.mRenderer.getCurrentLoop()), 15);
  		mGLView.requestRender();
  	  }   
};

private Runnable PlayGame = new Runnable(){
	@Override
	public void run() {
		handler.postDelayed(getRunnable(mGLView.mRenderer.getCurrentLoop()), 15);
		mGLView.requestRender();
	}
};

private Runnable Designer = new Runnable(){
	@Override
	public void run() {
		handler.postDelayed(getRunnable(mGLView.mRenderer.getCurrentLoop()), 15);
		mGLView.requestRender();
	}
};

private Runnable Options = new Runnable(){
	@Override
	public void run() {
		handler.postDelayed(getRunnable(mGLView.mRenderer.getCurrentLoop()), 15);
		mGLView.requestRender();
	}
};

private Runnable MoreApps = new Runnable(){
	@Override
	public void run() {
		handler.postDelayed(getRunnable(mGLView.mRenderer.getCurrentLoop()), 15);
		mGLView.requestRender();
	}
};
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		switch(mGLView.mRenderer.getCurrentLoop())
		{
		case MyGLRenderer.MAINMENU:
			MenuTouch.handleTouch(e);
			break;
		case MyGLRenderer.PLAYGAME:
			break;
		case MyGLRenderer.OPTIONS:
			break;
		case MyGLRenderer.MOREAPPS:
			break;
		case MyGLRenderer.DESIGNER:
			break;
		default:
		}
		return true;
	}

}
