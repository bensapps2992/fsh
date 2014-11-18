package com.example.android.Managers;

import com.example.android.main.MyGLRenderer;

class FishData {
	private TextManager mTextManager;
	private boolean MenuOn = true;
	
	FishData(TextManager textIn)
	{
		mTextManager = textIn;
	    mTextManager.addText("FISH_NAME",new float[]{-MyGLRenderer.getRatio()+0.05f,0.8f}, new float[]{0,0,0,0.8f});
	    mTextManager.addText("HEALTH 23", new float[]{-MyGLRenderer.getRatio()+0.05f,0.6f}, new float[]{1,1,0,0.8f});
	}
	
	public void draw(float x, float y)
	{
		if(MenuOn)
		{
			mTextManager.draw();
		}
	}
	
	public void update()
	{
		
	}
	
	public void onTouchEvent()
	{
		
	}

}
