package de.mpi.displaid.motion;

import java.util.ArrayList;

import SimpleOpenNI.SimpleOpenNI;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import de.mpi.displaid.motion.types.CoverFlowMotionHandler;
import de.mpi.displaid.motion.types.MotionHandler;

public class MotionManager {
	private ArrayList<MotionHandler> handlers;
	private SimpleOpenNI context;
	private ArrayList<Integer> validUsers;
	public ArrayList<Integer> bodyParts;
	PApplet surface;
	long lastMotionCheck;
	long motionCheckInterval = 10;
	
	private PImage iconHandLeft = null, iconHandRight = null;
	
	public MotionManager(PApplet surface) {
		this.surface = surface;
		handlers = new ArrayList<MotionHandler>();
		validUsers = new ArrayList<Integer>();
		lastMotionCheck = java.util.Calendar.getInstance().getTimeInMillis();

		context = new SimpleOpenNI(surface,SimpleOpenNI.RUN_MODE_MULTI_THREADED);
		context.setMirror(true);
		
		context.enableDepth();

		context.enableUser(SimpleOpenNI.SKEL_PROFILE_ALL);
		
		bodyParts = new ArrayList<Integer>();
		bodyParts.add(SimpleOpenNI.SKEL_LEFT_HAND);
		bodyParts.add(SimpleOpenNI.SKEL_RIGHT_HAND);
		
	}

	
	public void processMotion(int currentMode) {
		long time = java.util.Calendar.getInstance().getTimeInMillis();
		
		PVector currRightHandPos = null;
		PVector currLeftHandPos = null;
		
		for (Integer currUser : validUsers) {
			for (MotionHandler currHandler : handlers) {
				if (currHandler.getUserControl().getActiveInMode() != currentMode) continue;
				
				for (Integer currBodyPart : bodyParts) {
					if (!(currHandler instanceof CoverFlowMotionHandler)) {
						currHandler.processMotion(currUser, limb3DTo2D(currUser, currBodyPart), currBodyPart);
					}
					
					if (currBodyPart == SimpleOpenNI.SKEL_LEFT_HAND) currLeftHandPos = limb3DTo2D(currUser, currBodyPart);
					else if (currBodyPart == SimpleOpenNI.SKEL_RIGHT_HAND) currRightHandPos = limb3DTo2D(currUser, currBodyPart);
				}
			}
		}
		
		if (currRightHandPos != null && currLeftHandPos != null) {
			for (MotionHandler currHandler : handlers) {
				if (currHandler instanceof CoverFlowMotionHandler) {
					CoverFlowMotionHandler handler = (CoverFlowMotionHandler)currHandler;
					
					handler.checkForFastHandMotion(currRightHandPos, currLeftHandPos);
					/*if ((time-lastMotionCheck) > motionCheckInterval) {
						CoverFlowMotionHandler handler = (CoverFlowMotionHandler)currHandler;
						handler.checkForFastHandMotion(rightHandPos, leftHandPos);
						lastMotionCheck = time;
					}*/
				}
			}
		}
	}
	
	/**
	 * The MotionHandler will observe the size of object by default. You can set the Size and position manually.
	 * @param handler
	 * @param object
	 */
	public void registerMotionHandler(MotionHandler handler) {
		handlers.add(handler);
	}
	
	public void drawHands(PApplet surface) {
		if (iconHandLeft == null) {
			iconHandLeft = surface.loadImage("img/icons/handleft.png");
			iconHandLeft.resize(0, 50);
		}
		if (iconHandRight == null) {
			iconHandRight = surface.loadImage("img/icons/handright.png");
			iconHandRight.resize(0, 50);
		}
		for (Integer currUser : validUsers) {
			if (context.isTrackingSkeleton(currUser)) {
				PVector leftHand = limb3DTo2D(currUser,SimpleOpenNI.SKEL_LEFT_HAND);
				PVector rightHand = limb3DTo2D(currUser,SimpleOpenNI.SKEL_RIGHT_HAND);
				
				surface.image(iconHandLeft, leftHand.x - iconHandLeft.width / 2, leftHand.y - iconHandLeft.height / 2);
				surface.image(iconHandRight, rightHand.x - iconHandRight.width / 2, rightHand.y - iconHandRight.height / 2);
			}
		}
	}
	private PVector limb3DTo2D(int userId, int limb) {
		if (context.isTrackingSkeleton(userId)) {
			PVector limb3D = new PVector();
			PVector limb2D = new PVector();
		
			context.getJointPositionSkeleton(userId, limb, limb3D);
			context.convertRealWorldToProjective(limb3D, limb2D);
			
			limb2D.x *= 2;
			limb2D.y *= 1.66;
			
			return limb2D;
		}
		
		return null;
	}
	
	public SimpleOpenNI getContext() {
		context.update();
		return context;
	}
	
	public ArrayList<Integer> getValidUsers() {
		return validUsers;
	}
}
