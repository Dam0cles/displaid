package de.mpi.displaid.motion.types;

import SimpleOpenNI.SimpleOpenNI;
import processing.core.PVector;
import de.mpi.displaid.ui.UserControl;
import de.mpi.displaid.motion.MotionManager;

public abstract class MotionHandler {
	double areaStartX, areaStartY, areaEndX, areaEndY;
	private boolean leftHandIsIn, rightHandIsIn = false;
	UserControl userControl;
	
	public void setup(UserControl object) {
		if (areaStartX == 0 || areaEndX == 0) {
			areaStartX = object.getX();
			areaStartY = object.getY();
			areaEndX = object.getX()+object.getWidth();
			areaEndY = object.getY()+object.getHeight();
			userControl = object;
		}
	}
	
	public void setAreaStart(double x, double y) {
		areaStartX = x;
		areaStartY = y;
	}
	
	public void setAreaEnd(double x, double y) {
		areaEndX = x;
		areaEndY = y;
	}
	
	/**
	 * Triggers all event methods 
	 */
	public void processMotion(int userId, PVector vector, int bodyPart) {
		if (bodyPart == SimpleOpenNI.SKEL_RIGHT_HAND) {
			//System.out.println(vector);
		}
		if (vector != null && isIn(vector)) {
			if (bodyPart == SimpleOpenNI.SKEL_LEFT_HAND) {
				if (!leftHandIsIn) {
					leftHandEnteredEvent(userId);
					leftHandIsIn = true;
				}
				else {
					leftHandStaysInEvent(userId);
				}
			}
			else if (bodyPart == SimpleOpenNI.SKEL_RIGHT_HAND) {
				if (!rightHandIsIn) {
					rightHandEnteredEvent(userId);
					rightHandIsIn = true;
				}
				else {
					rightHandStaysInEvent(userId);
				}
			}
			else {
					System.out.println("Bodypart undefined: "+bodyPart);
			}
		}
		else if (vector != null && !isIn(vector)) {
			if (bodyPart == SimpleOpenNI.SKEL_LEFT_HAND) {
				if (leftHandIsIn) {
					leftHandLeftEvent(userId);
					leftHandIsIn = false;
				}
			}
			else if (bodyPart == SimpleOpenNI.SKEL_RIGHT_HAND) {
				if (rightHandIsIn) {
					rightHandLeftEvent(userId);
					rightHandIsIn = false;
				}
			}
			else {
					System.out.println("Bodypart undefined: "+bodyPart);			
			}
		}
	}
	
	public boolean isIn(PVector point) {
		return point.x >= areaStartX && point.x <= areaEndX && point.y >= areaStartY && point.y <= areaEndY;
	}
	
	public UserControl getUserControl() {
		return userControl;
	}
	
	public abstract void leftHandEnteredEvent(int userId);
	public abstract void leftHandLeftEvent(int userId);
	public abstract void rightHandEnteredEvent(int userId);
	public abstract void rightHandLeftEvent(int userId);
	public abstract void leftHandStaysInEvent(int userId);
	public abstract void rightHandStaysInEvent(int userId);
}