/* Banana is a class that is in charge all the bananas in the game. It will initialize the banana depending on what parameters you give it when you create the banana object and contains all the variables needed as well as getters, setter, collision set up and a random location method */
/* Kieran Henderson */
/* 5/1/20 */
package monkeygame;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
//import the libraries 
public class Banana {
	static int bananasEaten = 0; //static variable to track the amount of bananas eaten in total
	static int numRottenBananas = 1; //static variable to track the amount of rotten bananas on screen
	
	/* variables that track the x position and y position of the banana */
	double x;
	double y;
	
	//setting the image for the banana
	String imageName = "images/banana.png";
	Image image = new Image(imageName);
	
	//initializing the graphics context and canvas
	GraphicsContext gc;
	@FXML
	Canvas gameCanvas;

	//constructor for the standard banana
	public Banana(GraphicsContext gc, Canvas gameCanvas) {
		this.gc = gc;
		this.gameCanvas = gameCanvas;
		randomBanana();
	}

	//constructor for the bananas that need a different image 
	public Banana(String imageName, GraphicsContext gc, Canvas gameCanvas) {
		this.imageName = imageName;
		image = new Image(imageName); //update the image 
		this.gc = gc;
		this.gameCanvas = gameCanvas;
		randomBanana();
	}
	
	//generate a random position for the banana on spawn
	public void randomBanana() {
		this.x = Math.random()*(this.gameCanvas.getWidth()-this.image.getWidth()); //choose a random number between 0 and the screen width - the banana width for the x position
		this.y = Math.random()*(this.gameCanvas.getHeight()-this.image.getHeight()); //choose a random number between 0 and the screen height - the banana height for the y position
	}
	
	//method to draw the banana
	public void drawBanana() {
		gc.drawImage(this.image, this.x, this.y);
	}

	/* getters and setters */
	public static int getNumRottenBananas() {
		return numRottenBananas;
	}

	public static void setNumRottenBananas(int numRottenBananas) {
		Banana.numRottenBananas = numRottenBananas;
	}

	public static int getBananasEaten() {
		return bananasEaten;
	}

	public static void setBananasEaten(int bananasEaten) {
		Banana.bananasEaten = bananasEaten;
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

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	public Rectangle2D getBoundry() { //method that gets the boundary of the coconut to check for collisions
		return new Rectangle2D(this.x,this.y,this.image.getWidth(),this.image.getHeight());
	}
	
}
