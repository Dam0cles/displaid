package de.mpi.displaid;

import java.util.ArrayList;

import SimpleOpenNI.SimpleOpenNI;

import de.mpi.displaid.actions.ActionListener;
import de.mpi.displaid.motion.MotionManager;
import de.mpi.displaid.motion.types.ButtonHandler;
import de.mpi.displaid.motion.types.CoverFlowButtonHandler;
import de.mpi.displaid.motion.types.CoverFlowMotionHandler;
import de.mpi.displaid.motion.types.CoverFlowMotionLostHandler;
import de.mpi.displaid.ui.Button;
import de.mpi.displaid.ui.CoverFlow;
import de.mpi.displaid.ui.ProfilePanel;
import de.mpi.displaid.ui.TrackingArea;
import de.mpi.displaid.ui.UserControl;

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
	
	private ArrayList<UserControl> graphObjects;
	
	private int coverFlowInteractionDistance = 80;
	
	private SimpleOpenNI context;

	private ArrayList<Integer> validUsers;
	private MotionManager mManager;
		
	@Override
	public void setup() {
		graphObjects = new ArrayList<UserControl>();
		mManager = new MotionManager(this);
		
		size(1280, 800);
		loadImages();
		loadProfiles();
		setupUI();
		
		setupMotionObjects();
	}

	private void setupUI()
	{
		float borderAt = width * 3 / 4;
		
		coverflow = new CoverFlow(profiles, 0, 0, borderAt, height);
		graphObjects.add(coverflow);
		
		panelProfile = new ProfilePanel(null, borderAt-10, height-300, width / 4, 190);
		graphObjects.add(panelProfile);
		
		// male
		Button coverflowMaleSelect = new Button("Male",width/2-160,50,100,60);
		mManager.registerMotionHandler(new ButtonHandler(coverflowMaleSelect,1000));//(coverflowMaleSelect, coverflow, Profile.MALE));		
		graphObjects.add(coverflowMaleSelect);
		
		coverflowMaleSelect.addActionListener( new ActionListener() {
			public void actionPerformed() {
				coverflow.setInterest(Profile.MALE);
			}
		});
		
		// neutral
		Button coverflowNeutralSelect = new Button("Neutral",width/2-50,50,100,60);
		mManager.registerMotionHandler(new ButtonHandler(coverflowNeutralSelect,1000));		
		graphObjects.add(coverflowNeutralSelect);
		
		coverflowNeutralSelect.addActionListener(new ActionListener() {
			public void actionPerformed() {
				coverflow.setInterest(Profile.BOTH);
			}
		});	
		
		// female
		Button coverflowFemaleSelect = new Button("Female",width/2+60,50,100,60);
		mManager.registerMotionHandler(new ButtonHandler(coverflowFemaleSelect,1000));		
		graphObjects.add(coverflowFemaleSelect);
		
		coverflowFemaleSelect.addActionListener(new ActionListener() {
			public void actionPerformed() {
				coverflow.setInterest(Profile.FEMALE);
			}
		});
		
		// new profile
		Button newProfileSelect = new Button("New Profile",borderAt-10,10,width/4,300);
		mManager.registerMotionHandler(new ButtonHandler(newProfileSelect,1000));		
		graphObjects.add(newProfileSelect);
		
		newProfileSelect.addActionListener(new ActionListener() {
			public void actionPerformed() {
				modeActive = MODE_INPUT;
			}
		});
		
		// back
		Button backSelect = new Button("Back",borderAt-10,300,width/4,200);
		backSelect.setActiveInMode(MODE_INPUT);
		mManager.registerMotionHandler(new ButtonHandler(backSelect,1000));		
		graphObjects.add(backSelect);
		
		backSelect.addActionListener(new ActionListener() {
			public void actionPerformed() {
				modeActive = MODE_AMBIENT;
			}
		});
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
		
		background(255);
	
		tint(200);
		PImage img = mManager.getContext().depthImage();
		//img.resize(width, 0);
		image(img,0,0);
		
		if (modeActive == MODE_AMBIENT) {
			drawLogo();	
		}

		for (UserControl currElem : graphObjects) {
			if (currElem.getActiveInMode() == modeActive) {
				currElem.draw(this);
			}
		}
		
		panelProfile.setProfile(coverflow.activeProfile);
		
		animate();
		
		mManager.processMotion(modeActive);
		
		mManager.drawHands(this);
		
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

	private void setupMotionObjects() {
		validUsers = mManager.getValidUsers();
		context = mManager.getContext();
		
		UserControl trackingArea = new TrackingArea(0,0,width,height);
		CoverFlowMotionHandler cfHandlerLeft = new CoverFlowMotionHandler(coverFlowInteractionDistance, coverflow, trackingArea);
/*
		UserControl trackingAreaRight = new TrackingArea(coverFlowStartAreaX+coverFlowTrackingAreaThicknessX+coverFlowTrackingAreaDistX,coverFlowStartAreaY+coverFlowTrackingAreaThicknessY,coverFlowTrackingAreaThicknessX,coverFlowTrackingAreaDistY);
		CoverFlowMotionHandler cfHandlerRight = new CoverFlowMotionHandler(coverFlowInteractionSpeed, trackingAreaRight, true, coverflow);
		
		cfHandlerLeft.setOppositeSideHandler(cfHandlerRight);
		cfHandlerRight.setOppositeSideHandler(cfHandlerLeft);
		
		UserControl interruptAreaBottom = new TrackingArea(coverFlowStartAreaX,coverFlowStartAreaY+coverFlowTrackingAreaThicknessY+coverFlowTrackingAreaDistY,coverFlowTrackingAreaDistX+2*coverFlowTrackingAreaThicknessX,coverFlowTrackingAreaThicknessY);
		UserControl interruptAreaTop = new TrackingArea(coverFlowStartAreaX,coverFlowStartAreaY,coverFlowTrackingAreaDistX+2*coverFlowTrackingAreaThicknessX,coverFlowTrackingAreaThicknessY);
		
		CoverFlowMotionLostHandler cfLostTop = new CoverFlowMotionLostHandler(interruptAreaTop, coverflow);
		CoverFlowMotionLostHandler cfLostBottom = new CoverFlowMotionLostHandler(interruptAreaBottom, coverflow);
		
		cfLostTop.addCoverFlowMotionHandler(cfHandlerLeft);
		cfLostTop.addCoverFlowMotionHandler(cfHandlerRight);
		cfLostBottom.addCoverFlowMotionHandler(cfHandlerLeft);
		cfLostBottom.addCoverFlowMotionHandler(cfHandlerRight);
	*/	
		mManager.registerMotionHandler(cfHandlerLeft);
		/*
		mManager.registerMotionHandler(cfHandlerRight);
		mManager.registerMotionHandler(cfLostTop);
		mManager.registerMotionHandler(cfLostBottom);
		*/
		graphObjects.add(trackingArea);
		/*
		graphObjects.add(trackingAreaRight);
		graphObjects.add(interruptAreaTop);
		graphObjects.add(interruptAreaBottom);
		*/
	}
	
	public void onNewUser(int userId) {
		context.startPoseDetection("Psi",userId);
		
		if (userId < 3 && !validUsers.contains(userId)) {
			validUsers.add(userId);
		}
	}
	
	public void onLostUser(int userId) {
		if (validUsers.contains(userId)) {
			validUsers.remove(validUsers.indexOf(userId));
		}
	}

	public void onEndCalibration(int userId, boolean successfull) {
		if (successfull) {
			context.startTrackingSkeleton(userId);
		} 
		else {
			context.startPoseDetection("Psi",userId);
		}
	}

	public void onStartPose(String pose,int userId) {
		context.stopPoseDetection(userId); 
		context.requestCalibrationSkeleton(userId, true);
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		PApplet.main(new String[] { "de.mpi.displaid.Displaid" });
	}

}
