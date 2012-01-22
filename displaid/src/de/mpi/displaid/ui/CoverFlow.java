package de.mpi.displaid.ui;

import java.util.ArrayList;

import de.mpi.displaid.Profile;
import processing.core.PApplet;
import processing.core.PImage;

public class CoverFlow extends UserControl {

	public static final int SPIN_STOP = 0,
							SPIN_LEFT = 1,
							SPIN_RIGHT= 2;
	
	/**
	 * spin direction of CoverFlow
	 */
	private int spin = SPIN_STOP;

	/**
	 * CoverFlow starting index, indicating that the following next {@link #count} relevant pictures will be show
	 */
	private int index = 0;
	/**
	 * number of actively shown profile pictures
	 */
	private int count = 10;
	
	/**
	 * rotation speed, will gradually increase or decrease to/from {@link #maxSpeed}
	 */
	private float speed = 0f;
	
	/**
	 * maximum rotation speed
	 */
	private float maxSpeed = 0.01f;
	
	private int lastDirection;
	
	/**
	 * maximal edge length of an image, can be either width or height (depending on which of them is longer)
	 */
	private int imageMaxEdge = 180;
	
	private class MovingImage implements Comparable<MovingImage> {
		public PImage image;
		public float angle;
		public Profile profile;
			
		public MovingImage(PImage img, float a) {
			image = img;
			angle = a;
		}

		@Override
		public int compareTo(MovingImage o) {
			
			float a = Math.abs(this.angle),
				  ao = Math.abs(o.angle);
			return (a < ao) ? 1 : (a > ao) ? -1 : 0;
		}
	}
	
	/**
	 * list of available profiles
	 */
	public ArrayList<Profile> profiles = null;
	/**
	 * list of currently shown profiles
	 */
	public ArrayList<Profile> active = null;
	
	public ArrayList<MovingImage> images = null;
	
	/**
	 * contains the currently active profile (if present)
	 */
	public Profile activeProfile = null;
	
	/**
	 * current interest of viewer, exclude profiles that do not match
	 */
	private int interest = Profile.BOTH;
	
	public CoverFlow(ArrayList<Profile> profiles, float x, float y, float width, float height)
	{
		super(x, y, width, height);
		this.profiles = profiles;
		lastDirection = SPIN_STOP;
		
		// distribute profile images across ellipse
		initialize(0);
	}
	
	public void initialize(int index)
	{
		this.index = index;
		images = new ArrayList<CoverFlow.MovingImage>();
		active = new ArrayList<Profile>();
		
		
		int i = index;
		boolean done = false;
		while (!done)
		{
			Profile prof = profiles.get(i);
			if (prof.matchesInterest(this.interest)) {
				active.add(prof);
				if (active.size() >= count)
					done = true;
			}
			i++;
			if (i >= profiles.size())
				i = 0;
			if (i == index)	// after one full loop, exit whether or not we have count profiles
				done = true;
		}
		
		float alpha = (float)(-1 * Math.PI),
			  dAlpha = (float) (2 * Math.PI / active.size());

		for (int j = index; j < active.size(); j++)
		{
			Profile prof = active.get(j);
			PImage photo = null;
			try {
				photo = (PImage) prof.getPicture().clone();
				// resize
				if (photo.width >= photo.height)
					photo.resize(imageMaxEdge, 0);
				else
					photo.resize(0, imageMaxEdge);
				
			} catch (CloneNotSupportedException e) {
			}
			MovingImage mi = new MovingImage(photo, alpha);
			mi.profile = prof;
			alpha += dAlpha;
			images.add(mi);
		}
	}

	public int getInterest() {
		return interest;
	}

	public void setInterest(int interest) {
		if (interest != this.interest) {
			// reinitialize
			this.interest = interest;
			initialize(0);
		}
		this.interest = interest;
	}
	
	public void move(float dT)
	{
		float da = speed;
		if (spin == SPIN_LEFT) da = -speed;
		else if (spin == SPIN_STOP){
			if (lastDirection == SPIN_LEFT) da = -speed;
		}
		
		for (int i = 0; i < images.size(); i++) {
			MovingImage mi = images.get(i);
			mi.angle += da;
			if (mi.angle > Math.PI)
				mi.angle = (float) - Math.PI;
			else if (mi.angle < - Math.PI)
				mi.angle = (float) Math.PI;
		}
		if ((spin == SPIN_LEFT || spin == SPIN_RIGHT) && (speed < maxSpeed)) {
			lastDirection = spin;
			speed += 0.005f;
		}
		if (spin == SPIN_STOP && speed > 0f)
			speed -= 0.00012f;
	}
	
	@Override
	public void draw(PApplet canvas) {
		
		float center_x	= width / 2,
			  center_y	= height / 2,
			  r_x		= width / 2 - imageMaxEdge,
			  r_y		= height / 5;
		
		
		MovingImage[] imgs = new MovingImage[images.size()];
		for (int i = 0; i < images.size(); i++)
			imgs[i] = images.get(i);
		java.util.Arrays.sort(imgs);
		
		for (int i = 0; i < imgs.length; i++) {
			MovingImage mi = imgs[i];
			float image_x = (float) (center_x - Math.sin(mi.angle) * r_x);
			float image_y = (float) (center_y + Math.cos(mi.angle) * r_y) ;

			float scalef = (float) ( 1.5 - (Math.abs(mi.angle) / Math.PI) );
			
			float image_w = mi.image.width * scalef;
			float image_h = mi.image.height * scalef;
			
			// if currently active picture, draw flashy border
			if (i == imgs.length - 1) {
				activeProfile = mi.profile;
				float margin = 12;
				canvas.fill(255, 0, 0, 100);
				canvas.rect( image_x  - (image_w / 2) - margin, image_y - (image_h / 2) - margin, image_w + margin * 2, image_h + margin * 2);
			}
			
			canvas.image(mi.image, image_x  - (image_w / 2), image_y - (image_h / 2), image_w, image_h);
		}
	}
	
	public void setSpinDirection(int direction) {
		spin = direction;
	}
}
