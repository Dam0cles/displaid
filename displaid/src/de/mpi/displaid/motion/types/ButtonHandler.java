package de.mpi.displaid.motion.types;

import java.util.Calendar;

import de.mpi.displaid.ui.Button;
import de.mpi.displaid.ui.UserControl;

public class ButtonHandler extends MotionHandler {
	long leftHandEnteredTime, rightHandEnteredTime;
	int hoverTime;
	Button graphObject;
	boolean actionPerformed;
	
	
	public ButtonHandler(Button object, int hoverTime) {
		this.hoverTime = hoverTime;
		this.graphObject = object;
		actionPerformed = false;
		
		super.setup(object);
	}
	
	@Override
	public void leftHandEnteredEvent(int userId) {
		leftHandEnteredTime = Calendar.getInstance().getTimeInMillis();
	}

	@Override
	public void leftHandLeftEvent(int userId) {
		graphObject.setHover(0);
		actionPerformed = false;
	}

	@Override
	public void rightHandEnteredEvent(int userId) {
		rightHandEnteredTime = Calendar.getInstance().getTimeInMillis();		
	}

	@Override
	public void rightHandLeftEvent(int userId) {
		graphObject.setHover(0);
		actionPerformed = false;
	}

	@Override
	public void leftHandStaysInEvent(int userId) {
		long currTime = Calendar.getInstance().getTimeInMillis();
		long diff = currTime-leftHandEnteredTime;
		
		if (diff > hoverTime) {
			if (!actionPerformed) {
				graphObject.getActionListener().actionPerformed();
			}
			actionPerformed = true;
		}
		else {
			graphObject.setHover(diff/hoverTime);
		}
	}

	@Override
	public void rightHandStaysInEvent(int userId) {
		
		long currTime = Calendar.getInstance().getTimeInMillis();
		long diff = currTime-rightHandEnteredTime;
		
		if (diff > hoverTime) {
			if (!actionPerformed) {
				graphObject.getActionListener().actionPerformed();
			}
			actionPerformed = true;
		}
		else {
			graphObject.setHover(diff*1.0f/hoverTime);
		}
	}
}
