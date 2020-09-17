/* Powers is a class that is in charge all the power ups in the game. It will initialize the power up depending on what parameters you give it when you create the power up object and contains all the variables needed as well as getters, setter, collision set up, an update methode and a randomized movement method */
/* Kieran Henderson */
/* 5/1/20 */
package monkeygame;
//import the libraries 
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Powers {
	
	int timesPoweredUp = 0; //used to keep track of how many power ups have been used 
	
	double x; //initialize the x value 
	double y; //initialize the y value 
	
	//setting the image for the banana
	String imageName = "images/star.png";
	Image image = new Image(imageName);
	
	//initializing the graphics context and canvas
	GraphicsContext gc;
	@FXML
	Canvas gameCanvas;

	//basic constructor for the powers object  
	public Powers(GraphicsContext gc, Canvas gameCanvas) {
		this.gc = gc;
		this.gameCanvas = gameCanvas;
		randomStar();
	}

	//constructor for the powers who need a different image 
	public Powers(String imageName, GraphicsContext gc, Canvas gameCanvas) {
		this.imageName = imageName;
		this.gc = gc;
		this.gameCanvas = gameCanvas;
		randomStar();
	}
	
	//method to determine a random position for the star thats on screen
	public void randomStar() {
		this.x = Math.random()*(this.gameCanvas.getWidth()-this.image.getWidth()); //choose a random number between 0 and the screen width - the star width for the starting x position
		this.y = Math.random()*(this.gameCanvas.getHeight()-this.image.getHeight()); ////choose a random number between 0 and the screen height - the star height for the starting y position
	}
	
	//method to draw the star
	public void drawStar() {
		gc.drawImage(this.image, this.x, this.y);
	}
	
	//update the image 
	public void updateImage() {
		image = new Image(imageName);
	}
	
	/* getters and setters */
	public int getTimesPoweredUp() {
		return timesPoweredUp;
	}

	public void setTimesPoweredUp(int timesPoweredUp) {
		this.timesPoweredUp = timesPoweredUp;
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
	
	public Rectangle2D getBoundry() { //gets a rectangle around the power up to use for collision detection 
		return new Rectangle2D(this.x,this.y,this.image.getWidth(),this.image.getHeight());
	}
}
