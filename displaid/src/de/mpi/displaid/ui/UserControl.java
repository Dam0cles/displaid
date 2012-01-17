package de.mpi.displaid.ui;

import processing.core.PApplet;

public abstract class UserControl {

	public float x, y, width, height;

	public UserControl(float x, float y, float width, float height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public abstract void draw(PApplet canvas);

}
