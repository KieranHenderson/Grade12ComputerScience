/* Coconut is a class that is in charge all the coconuts in the game. It will initialize the coconut depending on what parameters you give it when you create the coconut object and contains all the variables needed as well as getters, setter, collision set up and a movement method */
/* Kieran Henderson */
/* 5/1/20 */
package monkeygame;
//import the libraries 
import java.util.Random;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Coconut {
	static int numCoconuts = 1; //variable that tracks the amount of coconuts, starts with one coconut
	
	// initializing position and speed variables 
	double x; 
	double y;
	double dx;
	double dy;

	//setting the image for the coconut
	String imageName = "images/coconut.png";
	Image image = new Image(imageName);

	//initializing the graphics context and canvas
	GraphicsContext gc;
	@FXML
	Canvas gameCanvas;

	//constructor for a standard coconut
	public Coconut(GraphicsContext gc, Canvas gameCanvas) {
		this.gc = gc;
		this.gameCanvas = gameCanvas;
		randomStart();
	}

	//constructor if I wanted to change the image of the coconut
	public Coconut(String imageName, GraphicsContext gc, Canvas gameCanvas) {
		this.imageName = imageName;
		this.gc = gc;
		this.gameCanvas = gameCanvas;
		randomStart();
	}
	
	//random start method
	public void randomStart() {
		Random random = new Random(); //make a random object
		this.dx = random.nextInt(4+4)-4; //choose a random number between -4 and 4 for the dx
		this.dy = random.nextInt(4+4)-4; //choose a random number between -4 and 4 for the dy
		this.x = Math.random()*(this.gameCanvas.getWidth()-this.image.getWidth()); //choose a random number between 0 and the screen width - the coconut width for the starting x position
		this.y = Math.random()*(this.gameCanvas.getHeight()-this.image.getHeight()); //choose a random number between 0 and the screen height - the coconut height for the starting y position 
	}

	/* getters and setters */
	public static int getNumCoconuts() {
		return numCoconuts;
	}

	public static void setNumCoconuts(int numCoconuts) {
		Coconut.numCoconuts = numCoconuts;
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

	public double getDx() {
		return dx;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

	public double getDy() {
		return dy;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	
	public void move() { //method that moves the coconuts
		this.x += this.dx; //x movements 
		this.y += this.dy; //y movements 

		if (this.x <=0 || this.x >= this.gameCanvas.getWidth() - this.image.getWidth()) { //flip the dx of the coconut if the coconut hit the edge of a screen
			this.dx = -this.dx;
		}
		if (this.y <= 0 || this.y >= this.gameCanvas.getHeight() - this.image.getHeight()) { //flip the dy of the coconut if the coconut hit the edge of a screen
			this.dy = -this.dy;
		}
		this.gc.drawImage(this.image, this.x, this.y); //draw the coconut in the new location 
	}
	
	public Rectangle2D getBoundry() { //method that gets the boundary of the coconut to check for collisions
		return new Rectangle2D(this.x,this.y,this.image.getWidth(),this.image.getHeight()); 
	}
}
