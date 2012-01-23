package de.mpi.displaid.ui;

import processing.core.PApplet;

public abstract class UserControl {

	public float x, y, width, height;
	int activateInMode;

	public UserControl(float x, float y, float width, float height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		activateInMode = 0;
	}

	public abstract void draw(PApplet canvas);

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public void setActiveInMode(int mode) {
		activateInMode = mode;
	}
	
	public int getActiveInMode() {
		return activateInMode;
	}
}
