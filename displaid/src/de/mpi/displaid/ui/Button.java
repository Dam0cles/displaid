package de.mpi.displaid.ui;

import processing.core.PApplet;

public class Button extends UserControl {

	private String caption;
	
	private float red = 255,
				  green = 128,
				  blue = 0;
	
	public Button(String caption, float x, float y, float width, float height) {
		super(x, y, width, height);
		this.caption = caption;
	}

	public void setColor(float red, float green, float blue)
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	@Override
	public void draw(PApplet canvas) {
		
		canvas.fill(canvas.color(red, green, blue, 128));
		canvas.rect(x, y, width, height, 10, 10);
		
		canvas.textSize(20);
		canvas.textAlign(canvas.CENTER, canvas.CENTER);
		canvas.fill(255);
		canvas.text(caption, x + width/2, y + height/2);
	}

}
