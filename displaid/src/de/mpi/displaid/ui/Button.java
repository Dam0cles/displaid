package de.mpi.displaid.ui;

import de.mpi.displaid.actions.ActionListener;
import processing.core.PApplet;

public class Button extends UserControl {

	private String caption;
	
	private ActionListener actionListener;
	/**
	 * value between 0 and 1 representing progress until the Button is considered as pressed
	 */
	private float hover = 0.0f;
	private int cornerRadius = 5;
	
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
		canvas.rect(x, y, width, height, cornerRadius, cornerRadius);
		
		if (hover > 0.0f) {
			// draw hover progress as a growing rectangle
			canvas.fill(canvas.color(red, green, blue, 255));
			
			float prog_start = 0,
				  prog_dist = (width - cornerRadius) * hover;
				
			if (hover >= 1.0f) {
				prog_dist = width;
			}
			canvas.rect(x + prog_start, y, prog_dist+cornerRadius, height, 10, cornerRadius, 10, cornerRadius);
		}
		
		canvas.textSize(20);
		canvas.textAlign(canvas.CENTER, canvas.CENTER);
		canvas.fill(255);
		canvas.text(caption, x + width/2, y + height/2);
	}

	public void setHover(float progress)
	{
		if (progress < 0)
			progress = 0;
		if (progress > 1.0f)
			progress = 1;
		
		hover = progress;
	}
	
	public void addActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
	}
	
	public ActionListener getActionListener() {
		return actionListener;
	}
}
