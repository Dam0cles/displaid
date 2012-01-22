package de.mpi.displaid.motion.types;

import java.util.ArrayList;

import de.mpi.displaid.ui.CoverFlow;
import de.mpi.displaid.ui.UserControl;


/**
 * If this area gets "touched" the coverflow motion will be interrupted
 * @author Sebastian Garn
 *
 */
public class CoverFlowMotionLostHandler extends MotionHandler {

	ArrayList<CoverFlowMotionHandler> coverFlowHandlers;
	CoverFlow coverflow;
	
	public CoverFlowMotionLostHandler(UserControl object, CoverFlow coverflow) {
		coverFlowHandlers = new ArrayList<CoverFlowMotionHandler>();
		this.coverflow = coverflow;
		super.setup(object);
	}
	
	public void addCoverFlowMotionHandler(CoverFlowMotionHandler newHandler) {
		coverFlowHandlers.add(newHandler);
	}
	
	@Override
	public void leftHandEnteredEvent(int userId) {

	}

	@Override
	public void leftHandLeftEvent(int userId) {
	
	}

	@Override
	public void rightHandEnteredEvent(int userId) {
		coverflow.setSpinDirection(CoverFlow.SPIN_STOP);
		
		for (CoverFlowMotionHandler currHandler : coverFlowHandlers) {
			//currHandler.stopDetection();
		}
	}

	@Override
	public void rightHandLeftEvent(int userId) {
		
	}

}
