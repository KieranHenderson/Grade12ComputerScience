/* Asteroid is the class that extends to the space object class and holds all the information for each asteroid that is created through out the game no matter what screen the asteroid is on */
/* Kieran Henderson */
/* 6/7/20 */

//My project is a recreation the classic arcade game asteroids from 1979, 
//there are some aspects of the game that may not be exactly how the original game worked as I have never played the original game but I did my best to include everything I found during research
//a more in depth description can be found at the bottom of the extras text file

package asteroids;

import java.util.Vector;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Asteroid extends SpaceObject{

	//static variables that dictate the number of asteroids that go on each screen
	static int startAsteroids = 4;
	static int numAsteroids = startAsteroids;
	static int startScreenAsteroids = 8;
	
	//final variables that dictate how fast the asteroids can go, they will be able to have a dx or dy of anywhere between the value/2 and -value/2
	final double largeSpeedRange = 2;
	final double medSpeedRange = 3;
	final double smallSpeedRange = 4;
	
	//the score value assigned to the asteroid depending on size
	int scoreValue;
	
	//Variables to hold and deal with the randomly generated points  
	Point[] points = new Point[10];
	Vector<Point> hull;
	int numPoints;
	
	//size of the asteroid so the class knows the area to randomly generate the points in
	double size;
	String sizeName;
	
	//if active then collision can occur with other objects 
	boolean active = true;
	//if remove is true then the asteroid gets removed from the game
	boolean remove;
	
	//all of the variables for handling the asteroid debris
	Point[] debris = new Point[20]; //all the individual points
	double[][] debrisSpeed = new double[debris.length][2]; //the speed for each debris
	double[] debrisLife = new double[debris.length]; //how long each debris piece will stay on screen
	double lifeTimer = 0; //Variables for tracking how long to keep the asteroids on screen / existing 
	double deadTime = 6;
		
	public Asteroid(String size, GraphicsContext gc, Canvas gameCanvas, double x, double y) {
		//Increase the number of asteroids
		numAsteroids++;
		
		//depending on the size of the asteroid determine the size 
		if(size == "med") {
			//set the size of the area of the possible randomly generated points 
			this.size = 90;
			//set the score value
			this.scoreValue = 50;
			
			//Randomly set the speed of the x and y in a range for example 2 to -2 while avoiding 
			while(this.dx < medSpeedRange/2-1 && this.dx > - medSpeedRange/2+1) {
				this.dx = Math.random()*medSpeedRange-medSpeedRange/2;
			}
			while(this.dy < medSpeedRange/2-1 && this.dy > - medSpeedRange/2+1) {
				this.dy = Math.random()*medSpeedRange-medSpeedRange/2;
			}
		} 
		
		else {
			//set the size of the area of the possible randomly generated points 
			this.size = 60;
			//set the score value
			this.scoreValue = 100;
			
			//Randomly set the speed of the x and y in a range for example 2 to -2 while avoiding 
			while(this.dx < smallSpeedRange/2-1 && this.dx > - smallSpeedRange/2+1) {
				this.dx = Math.random()*smallSpeedRange-smallSpeedRange/2;
			}
			while(this.dy < smallSpeedRange/2-1 && this.dy > - smallSpeedRange/2+1) {
				this.dy = Math.random()*smallSpeedRange-smallSpeedRange/2;
			}
		}	
		
		//set the variables based off the parameters the method received
		this.sizeName = size;
		this.gc = gc;
		this.gameCanvas = gameCanvas;
		this.x = x;
		this.y = y;

		generateRandomPoints(); //generate the random points 
		this.hull = GeneratePolygonPoints.ConvexHull(points, points.length); //pass the random points through the convex hull algorithm to find the out most points that were generated
		this.xPoints = new double[hull.size()]; //the size of xPoints array
		this.yPoints = new double[hull.size()]; //the size of the yPoints array
		this.numPoints = hull.size(); //the number of points
		findOutmostPoints(); //finds the top most, bottom most, left most, and right most points which will later help to cleanly wrap around the screen
	}
	
	public Asteroid(String size, GraphicsContext gc, Canvas gameCanvas) {
		//Assign size and score values 
		if(size == "big") {
			this.size = 125;
			scoreValue = 20;
		} else if(size == "med") {
			this.size = 90;
			scoreValue = 50;
		} else {
			this.size = 60;
			scoreValue = 100;
		}	
		
		//set the variables based off the parameters the method received
		this.sizeName = size;
		this.gc = gc;
		this.gameCanvas = gameCanvas;
		
		//Randomly set the speed of the x and y in a range for example 2 to -2 while avoiding 
		while(this.dx < largeSpeedRange/2 - 0.5 && this.dx > - largeSpeedRange/2+0.5) {
			this.dx = Math.random()*largeSpeedRange-largeSpeedRange/2;
		}
		while(this.dy < largeSpeedRange/2 - 0.5 && this.dy > - largeSpeedRange/2+0.5) {
			this.dy = Math.random()*largeSpeedRange-largeSpeedRange/2;
		}
		
		randomStart(); //give the asteroid a random start location 
		
		generateRandomPoints(); //generate the random points 
		this.hull = GeneratePolygonPoints.ConvexHull(points, points.length); //pass the random points through the convex hull algorithm to find the out most points that were generated
		this.xPoints = new double[hull.size()]; //the size of xPoints array
		this.yPoints = new double[hull.size()]; //the size of the yPoints array
		this.numPoints = hull.size(); //the number of points
		findOutmostPoints(); //finds the top most, bottom most, left most, and right most points which will later help to cleanly wrap around the screen
	}
	
	public void findOutmostPoints() {
		//set the outer most points to extremes so they will be rewritten by the rest of the method
		leftMostPoint = 100000;
		rightMostPoint = 0;
		bottomMostPoint = 0;
		topMostPoint = 10000;

		//find the x and y points 
		for (int i = 0; i < hull.size(); i++) {
			xPoints[i] = hull.get(i).x;
			yPoints[i] = hull.get(i).y;
		}
		
		//for each point of the asteroid
		for (int i = 0; i < points.length; i++) {
			if (points[i].x < leftMostPoint) { //if the point is less than the left most point it becomes the new left most point 
				leftMostPoint = points[i].x;
			}
			if (points[i].x > rightMostPoint) { //if the point is more than the right most point it becomes the new right most point 
				rightMostPoint = points[i].x;
			}
			if (points[i].y > bottomMostPoint) {  //if the point is more than the bottom most point it becomes the new bottom most point 
				bottomMostPoint = points[i].y;
			}
			if (points[i].y < topMostPoint) { //if the point is less than the top most point it becomes the new top most point 
				topMostPoint = points[i].y;
			}
		}
	}
	
	public void generateRandomPoints() {
		for (int i = 0; i < points.length; i++) { //for each point 
			double xPoint = (Math.random()*this.size) - (this.size/2) + this.x; //randomly generate a x value for the point
			double yPoint = (Math.random()*this.size) - (this.size/2) + this.y; //randomly generate a y value for the point
			points[i] = new Point(xPoint, yPoint); //add the two values to a point object and then add it to the points array
		}
	}
	
	public void update() { 
		if (!active) { //if the asteroid is not active (used for debris animations)
			lifeTimer+=.02; //the life timer increases 
			if (lifeTimer > deadTime){ //if the life timer is greater than the death timer then the asteroid gets removed
				remove = true;
			}
			
			for (int i = 0; i < debris.length; i++) { //move each piece of debris 
				debris[i].x += debrisSpeed[i][0];
				debris[i].y += debrisSpeed[i][1];
			}
		}
		
		for (int i = 0; i < points.length; i++) {
			points[i].x += dx; //move the x of each point
			points[i].y += dy; //move the y of each point
		}
		
		//update the outer most points 
		findOutmostPoints();		

		this.y +=dy; //update the x position
		this.x +=dx; //update the y position 
		
		//wrap
		wrap();
	}
	
	public void draw() {
		gc.setStroke(Color.WHITE);
		gc.beginPath(); //set up the stroke
		
		if(active) { //if the asteroid is active 
			this.gc.strokePolygon(xPoints, yPoints, numPoints); //draw the asteroid in the new location 
		} else { //if the asteroid is not active
			for (int i = 0; i < debris.length; i++) { //for each piece of debris
				if(lifeTimer <= debrisLife[i]) { //if the piece of debris has not exceeded its life span then draw it
					gc.setFill(Color.WHITE);
					gc.fillOval(debris[i].x, debris[i].y, 2, 2);
				}
			}
		}
		gc.closePath(); //close the path
	}
	
	public void explode() { //called when the asteroid get destroyed 
		active = false; // set the asteroid to inactive
		
		for (int i = 0; i < debris.length; i++) {
			Point p = new Point(x,y); //create a new point at the x and y of the asteroid upon its death
			debris[i] = p; //assign this point to the piece of debris
			debrisSpeed[i][0] = Math.random()*4-2; //randomly generate the x speed of this piece of debris
			debrisSpeed[i][1] = Math.random()*4-2; //randomly generate the y speed of this piece of debris
			debrisLife[i] = Math.random()*1.5; //randomly generate the life span of the debris piece
		}
	}
	
	public void wrap() {
		if (leftMostPoint > gameCanvas.getWidth()) { //if the left most point goes off the right side of the screen (the whole asteroid is off screen)
			for (int i = 0; i < points.length; i++) { //set all the points x positions to the other side of the screen
				points[i].x -= (leftMostPoint + (rightMostPoint-leftMostPoint));
			}
			x -= (leftMostPoint + (rightMostPoint-leftMostPoint)); //set the x of the asteroid to the other side of the screen
		}
		
		if (rightMostPoint <= 0) {//if the right most point goes off the left side of the screen (the whole asteroid is off screen)
			for (int i = 0; i < points.length; i++) { //set all the points x positions to the other side of the screen
				points[i].x += (gameCanvas.getWidth() - leftMostPoint); 
			}
			x += (gameCanvas.getWidth() - leftMostPoint); //set the x of the asteroid to the other side of the screen
		}
		
		if (topMostPoint > gameCanvas.getHeight()) { //if the top most point goes off the bottom side of the screen (the whole asteroid is off screen)
			for (int i = 0; i < points.length; i++) {
				points[i].y -= (topMostPoint + (bottomMostPoint-topMostPoint)); //set all the points y positions to the other side of the screen
			}
			y -= (topMostPoint + (bottomMostPoint-topMostPoint));
		}
		
		if (bottomMostPoint <= 0) { //if the bottom most point goes off the top side of the screen (the whole asteroid is off screen)
			for (int i = 0; i < points.length; i++) {
				points[i].y += (gameCanvas.getHeight() - topMostPoint); //set all the points y positions to the other side of the screen
			}
			y += (gameCanvas.getHeight() - topMostPoint);
		}
	}
	
	/* getters and setters */
	public boolean isRemove() {
		return remove;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}

	public static int getStartAsteroids() {
		return startAsteroids;
	}

	public static void setStartAsteroids(int startAsteroids) {
		Asteroid.startAsteroids = startAsteroids;
	}

	public static int getNumAsteroids() {
		return numAsteroids;
	}

	public static void setNumAsteroids(int numAsteroids) {
		Asteroid.numAsteroids = numAsteroids;
	}

	public static int getStartScreenAsteroids() {
		return startScreenAsteroids;
	}

	public static void setStartScreenAsteroids(int startScreenAsteroids) {
		Asteroid.startScreenAsteroids = startScreenAsteroids;
	}

	public int getScoreValue() {
		return scoreValue;
	}

	public void setScoreValue(int scoreValue) {
		this.scoreValue = scoreValue;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}
	
	
}
