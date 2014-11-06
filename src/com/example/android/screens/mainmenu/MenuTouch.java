package com.example.android.screens.mainmenu;

import com.example.android.main.MainActivity;
import com.example.android.main.MyGLRenderer;
import com.example.android.main.MyGLSurfaceView;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

public class MenuTouch {
	private static GestureDetector gd;
	private static Context context;
	
	public static void initTouch(final MyGLSurfaceView mGLView, final Context mContext)
	{
		context = mContext;
		gd = new GestureDetector(mContext,new GestureDetector.SimpleOnGestureListener(){
			@Override
			public boolean onSingleTapConfirmed(MotionEvent e)
			{
				switch(MenuRenderer.getTitleRot()){
				case MenuRenderer.OPTIONS:
					MainActivity.getmGLView().mRenderer.setCurrentLoop(MyGLRenderer.OPTIONS);
					Toast.makeText(context, "OPTIONS", Toast.LENGTH_SHORT).show();
					break;
				case MenuRenderer.DESIGNER:
					MainActivity.getmGLView().mRenderer.setCurrentLoop(MyGLRenderer.DESIGNER);
					Toast.makeText(context, "DESIGNER", Toast.LENGTH_SHORT).show();
					break;
				case MenuRenderer.MORE_APPS:
					MainActivity.getmGLView().mRenderer.setCurrentLoop(MyGLRenderer.MOREAPPS);
					Toast.makeText(context, "MORE_APPS", Toast.LENGTH_SHORT).show();
					break;
				case MenuRenderer.PLAY_GAME:
					MainActivity.getmGLView().mRenderer.setCurrentLoop(MyGLRenderer.PLAYGAME);
					Toast.makeText(context, "PLAY_GAME", Toast.LENGTH_SHORT).show();
					break;
				default:
				}
				return false;
			}
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
					float velocityY) {
					if(velocityX>0){
						if(Math.abs(MenuRenderer.getvAngle()) < 0.1)
							{
							MenuRenderer.setvAngle(10);
							MenuRenderer.setTitleRot(MenuRenderer.getTitleRot()+1);
							}
					}
					if(velocityX<0){
						if(Math.abs(MenuRenderer.getvAngle()) <0.1)
							{
							MenuRenderer.setvAngle(-10);
							MenuRenderer.setTitleRot(MenuRenderer.getTitleRot()-1);
							}
					}
					if(MenuRenderer.getTitleRot()==4)MenuRenderer.setTitleRot(0);
					if(MenuRenderer.getTitleRot()==-4)MenuRenderer.setTitleRot(0);
					if(MenuRenderer.getTitleRot()<0)MenuRenderer.setTitleRot(MenuRenderer.getTitleRot()+4);
				return false;
			}
		});
	}
	
	static public void handleTouch(MotionEvent e)
	{
		//handle gestures
		gd.onTouchEvent(e);
		//handle touches	
	}
}