package de.mpi.displaid.ui;

import javax.swing.Box.Filler;

import processing.core.PApplet;

public class UIPanel extends UserControl {

	private static float HEADER = 30;
	
	private String caption;
	
	private boolean active = false;
	
	public UIPanel(String caption, float x, float y, float width, float height)
	{
		super(x, y, width, height);
		this.caption = caption;
	}
	
	@Override
	public void draw(PApplet canvas) {
		
		
		canvas.fill(canvas.color(255, 0, 0, 100));
		canvas.noStroke();
		float radius = HEADER / 2.0f;
		canvas.rect(x, y + HEADER, width, height - HEADER, 0, 0, 10, 10);
		
		canvas.fill(canvas.color(200, 100, 100, 200));
		canvas.rect(x, y, width, HEADER, 10, 10, 0, 0);
		
		canvas.fill(255);
		canvas.textAlign(canvas.LEFT, canvas.CENTER);
		canvas.textSize(25);
		canvas.text(caption, x+15, y+HEADER / 2);
	}

}
