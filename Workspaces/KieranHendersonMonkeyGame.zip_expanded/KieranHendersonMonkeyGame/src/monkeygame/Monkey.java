/* Monkey is a class that is in charge all the monkeys in the game. It will initialize the monkey depending on what parameters you give it when you create the monkey object and contains all the variables needed as well as getters, setter, collision set up, collision detection, update and a movement method */
/* Kieran Henderson */
/* 5/1/20 */
package monkeygame;
//import the libraries 
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Monkey {

	int speed = 2; // initial speed of the monkey 
	
	double x = 100; //monkey start x
	double y = 100; //monkey start y
	double dx; //initialize the dx of the monkey 
	double dy; //initialize the dy of the monkey

	int lives = 3; //amount of lives the monkey has 
	
	String state = "normal"; //setting the initial state of the monkey to normal 

	/* direction variables for movement */
	String left = "LEFT";
	String right = "RIGHT";
	String up = "UP";
	String down = "DOWN";

	//setting the image of the monkey 
	String imageName = "images/monkey.png";
	Image image = new Image(imageName);

	//initializing the graphics context and canvas
	GraphicsContext gc;
	@FXML
	Canvas gameCanvas;
	
	ArrayList<String> input; //initialize the input array list for monkey movement 

	//default constructor for the monkey
	public Monkey(GraphicsContext gc, Canvas gameCanvas, ArrayList<String> input) {
		this.gc = gc;
		this.gameCanvas = gameCanvas;
		this.input = input;
	}

	//constructor for a monkey with a different picture
	public Monkey(String imageName, GraphicsContext gc, Canvas gameCanvas, ArrayList<String> input) {
		this.imageName = imageName;
		this.gc = gc;
		this.gameCanvas = gameCanvas;
		this.input = input;
	}
	
	//method that will update the monkeys speed and picture based of the state of the monkey 
	public void update() {
		if (this.state == "normal") { //if the monkeys state is normal then make his speed the default 2 and set his image to the normal monkey image
			this.speed = 2;
			this.imageName = "images/monkey.png";
		} else if (this.state == "star") { // if the state of the monkey is star then make his speed 4 and set his image to the glowing monkey image 
			this.speed = 4;
			this.imageName = "images/monkeyStar.png";
		} else if (this.state == "rStar") { //if the monkeys state is rStar then make his speed 6 and set his image to the rainbow monkey image
			this.speed = 6;
			this.imageName = "images/monkeyRainbow.png";
		}
		image = new Image(imageName); //update the image variable
	}
	
	//method for when the monkey gets hit by a coconut so that the monkey does not keep getting hit 
	public void randomXY() {
		this.x = Math.random()*(this.gameCanvas.getWidth()-this.image.getWidth()); //choose a random number between 0 and the screen width - the monkey width to teleport the monkey to
		this.y = Math.random()*(this.gameCanvas.getHeight()-this.image.getHeight()); //choose a random number between 0 and the screen height - the monkey height to teleport the monkey to
	}

	/* getters and setters */
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

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void move() { //method that moves the monkey 

		// for left and right buttons
		if (this.input.contains(this.left)) { //if the left arrow key is pushed move left 
			this.dx = -this.speed;
		} else if (this.input.contains(this.right)) { //if the right arrow key is pressed move right
			this.dx = this.speed;
		} else {
			this.dx = 0; //else don't move horizontally 
		}

		// for up and down buttons
		if (this.input.contains(this.up)) { //if the up arrow key is pressed move up
			this.dy = -this.speed;
		} else if (this.input.contains(this.down)) { //if the down arrow key is pushed move down
			this.dy = this.speed;
		} else {
			this.dy = 0; //else don't move vertically 
		}
		
		//save old position
		double x = this.x;
		double y = this.y;
		//move the monkey
		this.x += this.dx;
		this.y += this.dy;
		
		if (this.x < 0 || this.x > gameCanvas.getWidth() - this.image.getWidth()) { //if the updated x is off screen then reset it to the old x to keep the monkey on screen
			this.x = x;
			this.y = y;
		}
		
		if (this.y < 0 || this.y > gameCanvas.getHeight() - this.image.getHeight()) { //if the updated y is off screen then reset it to the old y to keep the monkey on screen
			this.x = x;
			this.y = y;
		}
		
		this.gc.drawImage(this.image, this.x, this.y); //draw the monkey 
	}
	
	public Rectangle2D getBoundry() { //get a rectangle around the monkey to use for collision
		return new Rectangle2D(this.x,this.y,this.image.getWidth(),this.image.getHeight());
	}
	
	public boolean collisionBanana(Banana banana) { //check for collision with a banana 
		boolean collide = this.getBoundry().intersects(banana.getBoundry());
		return collide;
	}
	
	public boolean collisionPowerUp(Powers power) { //checks for collision with a power up 
		boolean collide = this.getBoundry().intersects(power.getBoundry());
		return collide;
	}
	
	public boolean collisionCoconut(Coconut coconut) { //checks for collision with a coconut 
		if (this.state == "rStar") { //if the rStar state is active then coconut collision is disabled 
			return false;
		}
		boolean collide = this.getBoundry().intersects(coconut.getBoundry());
		return collide;
	}
	
	
}
