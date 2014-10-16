package com.example.android.opengl.Managers;

import java.util.Random;

import com.example.android.opengl.MyGLRenderer;
import com.example.android.opengl.Models.Goldfish;

import android.content.Context;
import android.opengl.Matrix;

public class FishManager {
	private Goldfish masterGoldfish;
	private TextManager mTextManager;
	private FishData mFishData;
	private final int NO_GOLDFISH = 1;
	private fish mGoldfish[] = new fish[NO_GOLDFISH];
	Context mContext;
	
	public FishManager(Context m){
		mContext = m;
		//Only declare one goldfish then draw it multiple times on the screen
		masterGoldfish = new Goldfish(mContext);
	    mTextManager = new TextManager(0);//using text on layer 0;
	    mFishData = new FishData(mTextManager);

		for(int iii=0;iii<NO_GOLDFISH;iii++){
			mGoldfish[iii] = new fish();
		}
	}
	
	public void draw(){
		//draw a goldfish with masterGoldfish.draw(modelMatrix)
		mFishData.draw(mGoldfish[0].x,mGoldfish[0].y);
		for(int iii=0;iii<NO_GOLDFISH;iii++){
			mGoldfish[iii].updateFish();
			masterGoldfish.draw(mGoldfish[iii].draw(iii), mGoldfish[iii].angle);
		}
	}
	
	/*
	 * A class to store information about any type of fish
	 */
	private class fish{
		public float x;
		public float y;
		public float z;
		public float vx;
		public float vy;
		public float vz;
		public float angle = 0;
		private boolean increasing = true;
		fish(){
			Random r = new Random(System.nanoTime());
			x=2*MyGLRenderer.getRatio()*r.nextFloat()-MyGLRenderer.getRatio();// between -ratio and ratio
			y=2*r.nextFloat()-1;// between -1 and 1
			z=-r.nextFloat()-1;// between -1 and -4
			while(vx*vx+vy*vy<0.00003)
			{
			vx=(2*r.nextFloat()-1)/100;// between -1 and 1
			vy=(2*r.nextFloat()-1)/300;
			}
			//vx=-1f/100f;//- doesnt work
			//vy=-1f/100f;
			vz=(2*r.nextFloat()-1)/100;
			vz=0;
		}
		
		public float[] draw(int iii)
		{
			float[] m = new float[16];
			Matrix.setIdentityM(m, 0);
			Matrix.translateM(m, 0, mGoldfish[iii].x, mGoldfish[iii].y, mGoldfish[iii].z);
			float angle = (float)Math.toDegrees(Math.atan((double)(mGoldfish[iii].vy/mGoldfish[iii].vx)));
			Matrix.rotateM(m, 0, angle, 0, 0, 1);
			if(mGoldfish[iii].vx>0)Matrix.scaleM(m, 0, -1, 1, 1);
			Matrix.scaleM(m, 0, 0.1f, 0.1f, 0.1f);
			return m;
			
		}
		
		public void updateFish(){
			//Handle the velocities
			x+=vx;
			y+=vy;
			z+=vz;
			//Handle the edges
			if(x>MyGLRenderer.getRatio()+0.2f)vx*=-1;
			if(x<-MyGLRenderer.getRatio()-0.2f)vx*=-1;
			if(y<-1)vy*=-1;
			if(y>1)vy*=-1;
			if(z>0)vz*=-1;
			if(z<-4)vz*=-1;
			//Handle the tail flap
			Random r = new Random(System.nanoTime());
			if(increasing)angle=angle+4*r.nextFloat();
			else angle=angle-4*r.nextFloat();
			if(angle>30)increasing = false;
			if(angle<-30)increasing=true;
		}
	}

}
