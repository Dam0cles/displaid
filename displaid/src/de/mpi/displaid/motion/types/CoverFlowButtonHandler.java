package de.mpi.displaid.motion.types;

import de.mpi.displaid.Profile;
import de.mpi.displaid.ui.CoverFlow;
import de.mpi.displaid.ui.UserControl;

public class CoverFlowButtonHandler extends MotionHandler {
	UserControl graphObject;
	CoverFlow coverflow;
	int gender;
	
	/**
	 * 
	 * @param maxSpeedOfInteractionDuration in ms
	 */
	public CoverFlowButtonHandler(UserControl object, CoverFlow coverflow, int gender) {
		graphObject = object;
		this.coverflow = coverflow;
		this.gender = gender;
		super.setup(object);
	}
	
	@Override
	public void leftHandEnteredEvent(int userId) {
		coverflow.setInterest(gender);
	}

	@Override
	public void leftHandLeftEvent(int userId) {
		
	}

	@Override
	public void rightHandEnteredEvent(int userId) {
		coverflow.setInterest(gender);
	}

	@Override
	public void rightHandLeftEvent(int userId) {
	
	}
}
