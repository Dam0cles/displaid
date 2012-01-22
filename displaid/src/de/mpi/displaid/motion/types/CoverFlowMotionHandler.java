package de.mpi.displaid.motion.types;

import processing.core.PVector;
import de.mpi.displaid.ui.CoverFlow;
import de.mpi.displaid.ui.UserControl;

public class CoverFlowMotionHandler extends MotionHandler {
	int minInteractionDistance = 50;
	CoverFlow coverflow;
	PVector lastLeftHandPos, lastRightHandPos;
	
	/**
	 * 
	 * @param maxSpeedOfInteractionDuration in ms
	 */
	public CoverFlowMotionHandler(int minInteractionDistance, CoverFlow coverflow, UserControl object) {
		this.minInteractionDistance = minInteractionDistance;
		this.coverflow = coverflow;
		
		super.setup(object);
	}
	
	@Override
	public void leftHandEnteredEvent(int userId) {
		
	}

	@Override
	public void leftHandLeftEvent(int userId) {
		
	}

	@Override
	public void rightHandEnteredEvent(int userId) {
		
	}

	@Override
	public void rightHandLeftEvent(int userId) {
		
	}
	
	public int getMinInteractionDistance() {
		return minInteractionDistance;
	}
	
	public void stopSpinAnimation() {
		coverflow.setSpinDirection(CoverFlow.SPIN_STOP);
	}
	
	public void checkForFastHandMotion(PVector rightHandPos, PVector leftHandPos) {
		
		if (lastRightHandPos == null) {
			lastRightHandPos = rightHandPos;
		}
		if (lastLeftHandPos == null) {
			lastLeftHandPos = leftHandPos;
		}
		
		/*if (java.lang.Math.abs(rightHandPos.x-lastRightHandPos.x) > 1) {
			System.out.println("Diff: "+java.lang.Math.abs(rightHandPos.x-lastRightHandPos.x));
		}*/
		
		if (java.lang.Math.abs(rightHandPos.x-lastRightHandPos.x) > minInteractionDistance) {
			if (rightHandPos.x < lastRightHandPos.x) {
				coverflow.setSpinDirection(CoverFlow.SPIN_RIGHT);
			}
			else {
				coverflow.setSpinDirection(CoverFlow.SPIN_LEFT);
			}
		}
		else if (java.lang.Math.abs(leftHandPos.x-lastLeftHandPos.x) > minInteractionDistance*500) {
			/*if (leftHandPos.x < lastLeftHandPos.x) {
				coverflow.setSpinDirection(CoverFlow.SPIN_RIGHT);
			}
			else {
				coverflow.setSpinDirection(CoverFlow.SPIN_LEFT);
			}*/
		}
		else {
			coverflow.setSpinDirection(CoverFlow.SPIN_STOP);
		}
		
		lastLeftHandPos = leftHandPos;
		lastRightHandPos = rightHandPos;
	}
}
