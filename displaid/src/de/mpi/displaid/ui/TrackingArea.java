package de.mpi.displaid.ui;

import processing.core.PApplet;

public class TrackingArea extends UserControl {

	public TrackingArea(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	@Override
	public void draw(PApplet canvas) {
		canvas.stroke(230);
		canvas.noFill();
		canvas.rect(x,y,width,height);
	}

}
