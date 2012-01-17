package de.mpi.displaid;

import java.util.ArrayList;

import de.mpi.displaid.ui.Button;
import de.mpi.displaid.ui.CoverFlow;
import de.mpi.displaid.ui.ProfilePanel;
import de.mpi.displaid.ui.UIPanel;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Displaid
 * 
 * public display dating platform
 * 
 * @author damocles
 */
public class Displaid extends PApplet {
	
	public static final int MODE_AMBIENT= 0,
							MODE_SUBTLE	= 1,
							MODE_INPUT	= 2;

	private int modeActive = MODE_AMBIENT;
	
	private PImage logo = null;
	
	/**
	 * list of available user profiles
	 */
	private ArrayList<Profile> profiles = new ArrayList<Profile>();
	
	/**
	 * at which index to start taking the CoverFlow profiles out of the profile list
	 * @see profiles
	 */
	private CoverFlow coverflow;
	
	private ProfilePanel panelProfile;
		
	@Override
	public void setup() {
		size(1280, 800);
		loadImages();
		loadProfiles();
		setupUI();
	}

	private void setupUI()
	{
		
		float borderAt = width * 3 / 4;
		coverflow = new CoverFlow(profiles, 0, 0, borderAt, height);
		panelProfile = new ProfilePanel(null, borderAt, 20, width / 4, 160);
		//btnTest = new Button("Foobar", borderAt + 10, 50, 200, 90);
	}
	
	private void drawWait(float percent, float x, float y)
	{
		fill(color(80, 255, 80, 80));
		arc(x, y, 20, 20, 0, (float) Math.PI);
	}
	
	private void loadImages()
	{
		// load static images at startup
		logo = loadImage("img/displaid_logo.png");
	}
	
	private void loadProfiles()
	{
		profiles = new ArrayList<Profile>();
		
		// TODO: load profiles from a file
		profiles.add(new Profile(Profile.MALE, 56, Profile.FEMALE, loadImage("img/profile/000.jpg")));
		profiles.add(new Profile(Profile.FEMALE, 39, Profile.MALE, loadImage("img/profile/001.jpg")));
		profiles.add(new Profile(Profile.MALE, 56, Profile.FEMALE, loadImage("img/profile/002.jpg")));
		profiles.add(new Profile(Profile.MALE, 52, Profile.FEMALE, loadImage("img/profile/003.jpg")));
		profiles.add(new Profile(Profile.FEMALE, 53, Profile.BOTH, loadImage("img/profile/004.jpg")));
		profiles.add(new Profile(Profile.MALE, 59, Profile.BOTH, loadImage("img/profile/005.jpg")));
		profiles.add(new Profile(Profile.FEMALE, 21, Profile.FEMALE, loadImage("img/profile/006.jpg")));
		profiles.add(new Profile(Profile.MALE, 43, Profile.FEMALE, loadImage("img/profile/007.jpg")));
		profiles.add(new Profile(Profile.FEMALE, 42, Profile.MALE, loadImage("img/profile/008.jpg")));
		profiles.add(new Profile(Profile.FEMALE, 38, Profile.MALE, loadImage("img/profile/009.jpg")));
		profiles.add(new Profile(Profile.MALE, 56, Profile.FEMALE, loadImage("img/profile/010.jpg")));
		profiles.add(new Profile(Profile.FEMALE, 22, Profile.MALE, loadImage("img/profile/011.jpg")));
		
	}
	
	@Override
	public void draw() {
		
		background(0);
	
		drawLogo();

		coverflow.draw(this);
		panelProfile.setProfile(coverflow.activeProfile);
		panelProfile.draw(this);
		drawWait(0.5f, 20, 20);
		//btnTest.draw(this);
		animate();
	}

	public void animate()
	{
		float dT = 1.0f / frameRate;
		coverflow.move(dT);
	}
	
	private void drawLogo()
	{
		image(logo, 0, 0);
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		PApplet.main(new String[] { "de.mpi.displaid.Displaid" });
	}

}
