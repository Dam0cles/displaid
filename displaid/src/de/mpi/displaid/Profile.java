package de.mpi.displaid;

import processing.core.PApplet;
import processing.core.PImage;

public class Profile {

	public static final int UNKNOWN = 0,
							MALE	= 1,
							FEMALE	= 2,
							BOTH	= 3;
	
	private PImage photo;
	
	public int gender;
	public int interest;
	public int age;
	
	public Profile(int gender, int age, int interest, PImage photo)
	{
		setGender(gender);
		setAge(age);
		setInterest(interest);
		setPicture(photo);
	}
	
	public void setPicture(PImage photo)
	{
		this.photo = photo;
	}
	
	public PImage getPicture()
	{
		return photo;
	}
	
	public int getGender() {
		return gender;
	}
	
	public void setGender(int gender) {
		this.gender = gender;
	}
	
	public int getInterest() {
		return interest;
	}
	
	public void setInterest(int interest) {
		this.interest = interest;
	}
	
	public boolean isInterestedIn(int gender)
	{
		return (interest & gender) == gender;
	}
	
	public boolean matchesInterest(int gender)
	{
		return (this.gender & gender) != 0;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}

}
