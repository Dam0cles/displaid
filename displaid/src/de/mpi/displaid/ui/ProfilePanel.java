package de.mpi.displaid.ui;

import processing.core.PApplet;
import processing.core.PImage;
import de.mpi.displaid.Profile;

public class ProfilePanel extends UIPanel {

	private Profile profile;
	
	private PImage[] icons = null;
	
	public ProfilePanel(Profile profile, float x, float y, float width,
			float height) {
		super("Profile", x, y, width, height);
		this.profile = profile;
	}

	private void loadIcons(PApplet canvas)
	{
		if (icons != null)
			return;
		String[] iconFiles = {"gender_na.png", "gender_male.png", "gender_female.png", "gender_both.png"};
		icons = new PImage[4];
		for (int i = 0; i < iconFiles.length; i++) {
			icons[i] = canvas.loadImage("img/icons/"+iconFiles[i]);
		}
	}
	
	private void drawIcon(PApplet canvas, int gender, float x, float y, float edge)
	{
		if (gender < 0 || gender > 3)
			gender = 0;
		PImage ico = icons[gender];
		float iw = ico.width;
		float ih = ico.height;
		
		canvas.image(ico, x - (edge)/2f, y - (edge)/2f, edge, edge);
	}
	
	public void draw(PApplet canvas)
	{
		super.draw(canvas);
		
		loadIcons(canvas);
		
		canvas.fill(50);
		canvas.textAlign(canvas.LEFT, canvas.CENTER);
		canvas.textSize(18);
		
		canvas.text("Gender:", x + 20, y + 40);
		canvas.text("Age:", x + 20, y + 80);
		canvas.text("Interest:", x + 20, y + 120);
		
		if (profile == null) {
			drawIcon(canvas, Profile.UNKNOWN, x + 120, y + 50, 25);
			drawIcon(canvas, Profile.UNKNOWN, x + 120, y + 130, 25);
		}
		else
		{
			drawIcon(canvas, profile.gender, x + 120, y + 50, 25);
			canvas.textSize(22);
			canvas.text(profile.age, x + 110, y + 80);
			drawIcon(canvas, profile.interest, x + 120, y + 130, 25);
		}
		
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

}
