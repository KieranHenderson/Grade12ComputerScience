/* Space Object is a class that is the parent for each of the objects in the game, this includes the bullets, asteroids, player, and the enemy */
/* Kieran Henderson */
/* 6/7/20 */

//My project is a recreation the classic arcade game asteroids from 1979, 
//there are some aspects of the game that may not be exactly how the original game worked as I have never played the original game but I did my best to include everything I found during research
//a more in depth description can be found at the bottom of the extras text file

package asteroids;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class SpaceObject {
	
	static int score = 0; //variable that tracks the score 
	
	/* variables that each space object will have */ 
	
	//x and y position
	double x; 
	double y;
	
	//x and y speeds 
	double dx;
	double dy;

	double radians; //radians for direction 
	double speed; //speed 

	//arrays containing the x points and the y points of the object
	double[] xPoints;
	double[] yPoints;
	
	//outer most points 
	double leftMostPoint = 100000;
	double rightMostPoint = 0;
	double bottomMostPoint = 0;
	double topMostPoint = 10000;
		
	//import game canvas, scene and graphics context
	GraphicsContext gc;
	@FXML
	Canvas gameCanvas;
	
	public boolean collides(double x, double y) { //method that checks collision between a space object and an x and y position 
		boolean inside = false;
		for (int i = 0, j = xPoints.length-1; i < xPoints.length; j = i++) { //check if the point is inside the space object
			if((yPoints[i] > y) != (yPoints[j] > y) && (x < (xPoints[j] - xPoints[i]) * (y - yPoints[i]) / (yPoints[j] - yPoints[i]) + xPoints[i])){
				inside = !inside;
			}
		}
		return inside;
	}
	
	public boolean hit(SpaceObject object) { //checks if a space object collides with another space object 
		double[] xPoints = object.getxPoints();
		double[] yPoints = object.getyPoints();
		for (int i = 0; i < xPoints.length; i++) {
			if(collides(xPoints[i], yPoints[i])) { //use the collides method to check all the points 
				return true;
			}
		}
		return false;
	}
	
	public void randomStart() { //randomly start the space object around the screen
		int side = (int) Math.round((Math.random()*4)); //randomly select which side the object will start on 
		
		//left side of the screen
		if(side == 1) {
			this.x = 0;
			this.y = Math.random()*(this.gameCanvas.getHeight()); //generate a random y position along that side
		} 
		
		//right side of the screen
		else if (side == 2) {
			this.x = this.gameCanvas.getWidth()+20;
			this.y = Math.random()*(this.gameCanvas.getHeight()); //generate a random y position along that side
		} 
		
		//top of the screen
		else if (side == 3) {
			this.x = Math.random()*(this.gameCanvas.getWidth()); //generate a random x position along that side
			this.y = 0;
		} 
		
		//bottom of the screen
		else {
			this.x = Math.random()*(this.gameCanvas.getWidth()); //generate a random x position along that side
			this.y = this.gameCanvas.getHeight()+20;
		}
	}
	
	void wrap() { //a simple method that wraps the space object when its x or y value hits the side of the screen
		if (x < 0) {
			x = gameCanvas.getWidth();
		}
		if (x > gameCanvas.getWidth()) {
			x = 0;
		}
		if (y < 0) {
			y = gameCanvas.getHeight();
		}
		if ( y > gameCanvas.getHeight()) {
			y = 0;
		}
	}
	
	public void cleanWrap() { //used for larger objects such as the enemy and the asteroid who are unable to use the basic wrap as it would look terrible 
		
		//set the outer most points of the space object 
		leftMostPoint = 100000;
		rightMostPoint = 0;
		bottomMostPoint = 0;
		topMostPoint = 10000;
		
		
		for (int i = 0; i < xPoints.length; i++) { 
			if (xPoints[i] < leftMostPoint) { //if the point is less than the left most point it becomes the new left most point 
				leftMostPoint = xPoints[i];
			}
			if (xPoints[i] > rightMostPoint) { //if the point is more than the right most point it becomes the new right most point 
				rightMostPoint = xPoints[i];
			}
			
			if (yPoints[i] > bottomMostPoint) { //if the point is more than the bottom most point it becomes the new bottom most point 
				bottomMostPoint = yPoints[i];
			}
			if (yPoints[i] < topMostPoint) { //if the point is less than the top most point it becomes the new top most point 
				topMostPoint = yPoints[i];
			}
		}
		
		
		
		if (leftMostPoint > gameCanvas.getWidth()) { //if the left most point goes off the right side of the screen (the whole asteroid is off screen)
			for (int i = 0; i < xPoints.length; i++) {//set all the points x positions to the other side of the screen
				xPoints[i] -= (leftMostPoint + (rightMostPoint-leftMostPoint)); 
			}
			x -= (leftMostPoint + (rightMostPoint-leftMostPoint));//set the x of the asteroid to the other side of the screen
		}
		
		if (rightMostPoint <= 0) { //if the right most point goes off the left side of the screen (the whole asteroid is off screen)
			for (int i = 0; i < xPoints.length; i++) { //set all the points x positions to the other side of the screen
				xPoints[i] += (gameCanvas.getWidth() - leftMostPoint);
			}
			x += (gameCanvas.getWidth() - leftMostPoint); //set the x of the asteroid to the other side of the screen
		}
		
		if (topMostPoint > gameCanvas.getHeight()) { //if the top most point goes off the bottom side of the screen (the whole asteroid is off screen)
			for (int i = 0; i < yPoints.length; i++) { //set all the points y positions to the other side of the screen
				yPoints[i] -= (topMostPoint + (bottomMostPoint-topMostPoint)); 
			}
			y -= (topMostPoint + (bottomMostPoint-topMostPoint)); //set the y of the asteroid to the other side of the screen
		}
		
		if (bottomMostPoint <= 0) { //if the bottom most point goes off the top side of the screen (the whole asteroid is off screen)
			for (int i = 0; i < yPoints.length; i++) { //set all the points y positions to the other side of the screen
				yPoints[i] += (gameCanvas.getHeight() - topMostPoint);
			}
			y += (gameCanvas.getHeight() - topMostPoint); //set the y of the asteroid to the other side of the screen
		}
	}
	
	
	/* getters and setters */
	public static int getScore() {
		return score;
	}

	public static void setScore(int score) {
		SpaceObject.score = score;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double[] getxPoints() {
		return xPoints;
	}

	public void setxPoints(double[] xPoints) {
		this.xPoints = xPoints;
	}

	public double[] getyPoints() {
		return yPoints;
	}

	public void setyPoints(double[] yPoints) {
		this.yPoints = yPoints;
	}
}
