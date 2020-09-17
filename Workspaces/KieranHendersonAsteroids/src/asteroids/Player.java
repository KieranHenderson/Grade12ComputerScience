/* Player is the class for the player object that contains all the methods for action the player may perform as well as variables involving the player such as lives */
/* Kieran Henderson */
/* 6/7/20 */

//My project is a recreation the classic arcade game asteroids from 1979, 
//there are some aspects of the game that may not be exactly how the original game worked as I have never played the original game but I did my best to include everything I found during research
//a more in depth description can be found at the bottom of the extras text file

package asteroids;

import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player extends SpaceObject{
	
	private final int BULLET_MAX = 4; //the max amount of bullets the play can have out at one time 
	
	private ArrayList<Bullet> bulletList; //array list for the players bullets 
	
	private double[] xFlame; //x points of the flame 
	private double[] yFlame; //y points of the flame 
	
	static int lives = 3; //amount of lives the player has 

	double maxSpeed; //the max speed the player can go at 
	double acceleration; //how fast the players speed increases
	double deceleration; //how fast the players speed decreases
	double accelerationTimer; //used to calculate the points of the flame
	
	double bulletSpeed = 4; //speed of the bullets
	
	double rotationSpeed; //speed at which the player rotates 
	
	/* direction variables for movement */
	String left = "LEFT";
	String right = "RIGHT";
	String up = "UP";
	String shoot = "SPACE";
	
	boolean jumped = false; //has the player recently jumped to hyper-space?
	double hyperspaceTimer = 10; // greater than the cool down at the start so you can jump when the game starts
	double hyperspaceCooldown = 5;	// how long it takes before you can jump again
	
	ArrayList<String> input; //initialize the input array list for monkey movement 
	
	Point[] deadLines = new Point[8]; //the array containing the points of the lines that break apart upon death
	double[][] deadLineSpeed = new double[4][2]; //array containing the speeds for those lines 
	double[] debrisLife = new double[4]; //how long the lines will stay on screen
	double lifeTimer = 0; //timer to know if they should be taken off
	
	int respawnTime = 10; //how long until the player will re-spawn

	boolean dead = false;
	
	boolean safeToSpawn = true;

	public Player(GraphicsContext gc, Canvas gameCanvas, ArrayList<String> input, ArrayList<Bullet> bulletList) { //main player constructor that is used for the player
		this.gc = gc;
		this.gameCanvas = gameCanvas;
		this.input = input;
		this.bulletList = bulletList;
		
		x = gameCanvas.getWidth()/2; //start the player in the middle of the screen
		y = gameCanvas.getHeight()/2;
		
		maxSpeed = 2.5; //max speed of the player 
		acceleration = 0.1; //how fast the player accelerates 
		deceleration = 0.0075; //how fast the player decelerates 
		
		xPoints = new double[4]; //array for the x points of the player ship
		yPoints = new double[4]; //array for the y points of the player ship
		xFlame = new double[4]; //array for the x points of the player flame
		yFlame = new double[4]; //array for the y points of the player flame 
		
		radians = -Math.PI/2; //the direction that the player is pointing towards in terms of radians 
		rotationSpeed = 0.06; //how fast the player can turn
		
		setPoints();//initialize the points of the player 

	}
	
	public Player(GraphicsContext gc, Canvas gameCanvas) { //smaller constructor used to display how many lives the player has left 
		this.gc = gc;
		this.gameCanvas = gameCanvas;
		
		//starting position
		x = 45; 
		y = 100;
		
		//no need for flame when drawing this player as it is not moving 
		xPoints = new double[4]; //array for the x points of the player ship
		yPoints = new double[4]; //array for the y points of the player ship
		
		radians = -Math.PI/2; //the direction that the player is pointing towards in terms of radians 
		
		setPoints();//initialize the points of the player 
	}
	
	private void setPoints() {
		if (!dead) { //use math to determine the points of the player based of its direction 
			xPoints[0] = x + Math.cos(radians) * 16;
			yPoints[0] = y + Math.sin(radians) * 16;
			
			xPoints[1] = x + Math.cos(radians - 4 * Math.PI/5) * 16;
			yPoints[1] = y + Math.sin(radians - 4 * Math.PI/5) * 16;
			
			xPoints[2] = x + Math.cos(radians + Math.PI) * 10;
			yPoints[2] = y + Math.sin(radians + Math.PI) * 10;
			
			xPoints[3] = x + Math.cos(radians + 4 * Math.PI/5) * 16;
			yPoints[3] = y + Math.sin(radians + 4 * Math.PI/5) * 16;	
			
		} else { //update the debris 
			for (int j = 0; j < deadLines.length; j+=2) {  //go through each pair of points and update them
				if (j == 0) { 
					deadLines[j].x += deadLineSpeed[j/2][0];
					deadLines[j].y += deadLineSpeed[j/2][1];
					deadLines[7].x += deadLineSpeed[j/2][0];
					deadLines[7].y += deadLineSpeed[j/2][1];
				} else {
					deadLines[j].x += deadLineSpeed[j/2][0];
					deadLines[j].y += deadLineSpeed[j/2][1];
					deadLines[j-1].x += deadLineSpeed[j/2][0];
					deadLines[j-1].y += deadLineSpeed[j/2][1];
				}
			}
		}
	}
	
	private void setFlame() { //set the points for the flame by using the same math as the points for the player 
		xFlame[0] = xPoints[2];
		yFlame[0] = yPoints[2];
		
		xFlame[1] = x + Math.cos(radians - 5 * Math.PI / 6) * 14;
		yFlame[1] = y + Math.sin(radians - 5 * Math.PI / 6) * 14;
		
		//the only difference here from the player is that I use a variable that gets updated after every time the play which moves the outside point to animate the flame
		xFlame[2] = x + Math.cos(radians - Math.PI) * (20 + accelerationTimer * 50);
		yFlame[2] = y + Math.sin(radians - Math.PI) * (20 + accelerationTimer * 50);
		
		xFlame[3] = x + Math.cos(radians + 5 * Math.PI / 6) * 14;
		yFlame[3] = y + Math.sin(radians + 5 * Math.PI / 6) * 14;
	}
	
	public void shoot() { //method that is called when the player shoots 
		if(bulletList.size() == BULLET_MAX) { //if the player has shot all their bullets do nothing 
			return;
		}
		bulletList.add(new Bullet(gc, gameCanvas, x, y, radians, bulletSpeed)); 
	}
	
	public void hyperspace() { //method that will randomly teleport the player to a place on the screen
		hyperspaceTimer = 0;
		jumped = true;
		x = Math.random()*gameCanvas.getWidth();
		y = Math.random()*gameCanvas.getHeight();
	}
	
	public void update () { //method that updates the player 
		
		if (jumped) { //if the player recently jumped then start the hyper-space timer which acts as a cool down for the skill
			hyperspaceTimer += 0.01;
		}
		
		//if dead update debris life timer 
		if (dead) {
			lifeTimer+=.025;
		}
		
		//turning
		if(!dead) {
			if (this.input.contains(this.left)) { //if the left arrow key is pushed turn left 
				radians -= rotationSpeed; 
			} else if (this.input.contains(this.right)) { //if the right arrow key is pressed turn right
				radians += rotationSpeed;
			}
		}
		
		//accelerating
		if (this.input.contains(this.up)){
			dx += Math.cos(radians) * acceleration;
			dy += Math.sin(radians) * acceleration;
			
			accelerationTimer += .005; //this is so the player gradually gets faster
			
			if (accelerationTimer > 0.1) {
				accelerationTimer = 0;
			}
			
		} else {
			accelerationTimer = 0;
		}
		
		//deceleration
		double direction = (double) Math.sqrt(dx*dx+dy*dy); //use pythagorean theorem to solve for the direction of the player 
		
		//using the direction of the ship slow down the player 
		if (direction > 0) { 
			dx -= (dx / direction) * deceleration;
			dy -= (dy / direction) * deceleration;
		}
		if (direction > maxSpeed) { 
			dx = (dx / direction) * maxSpeed;
			dy = (dy / direction) * maxSpeed;
		}
		
		
		//update position
		if (!dead) {
			x += dx;
			y += dy;
		}
		
		//set shape
		setPoints();
		
		//set flame if the player is applying forward thrust (pushing the up arrow)
		if (this.input.contains(this.up)) {
			setFlame();
		}
		
		//screen wrap
		wrap();
		
	}
	
	public void draw() {
		gc.setStroke(Color.WHITE);
		gc.beginPath();
		
		//draw spaceship 
		if(lives!=0 && !dead) {
			for (int i = 0, j = xPoints.length-1; i < xPoints.length; j = i++) { //draw each line 
				gc.strokeLine(xPoints[i], yPoints[i], xPoints[j], yPoints[j]);
			}
		} 
		
		else if(dead) {
			//draw explosion 
			for (int i = 0, j = deadLines.length-1; i < deadLines.length; j+=2, i+=2) { //draw each line 
				if (lifeTimer <= debrisLife[i/2]) { //only draw the line if its life span is not over
					gc.strokeLine(deadLines[i].x, deadLines[i].y, deadLines[j].x, deadLines[j].y);
				}
				
				if(j == 7) { //to help loop through the points 
					j = -1;
				}
			}
		}
	
		
		//draw flames
		if(input != null && !dead && this.input.contains(this.up) ) {
			for (int i = 0, j = xFlame.length-1; i < xFlame.length; j = i++) { 
				gc.strokeLine(xFlame[i], yFlame[i], xFlame[j], yFlame[j]); //draw the lines 
			}
		}
		
		gc.closePath();
	}
	
	public void die() {
		if(!dead) {
			dead = true;
			lives -= 1; //update lives 
			
			int pointNumber = 0; //generate the points for the debris 
			for (int j = 0; j < deadLines.length; j+=2) { //two points are generated for every point on the player because each point has two lines connected to it and the lines are going to separate 
				Point p = new Point(xPoints[pointNumber], yPoints[pointNumber]);//had to make 2 different points for that are the exact same because if I used the same point for both the lines would stay connected
				Point p2 = new Point(xPoints[pointNumber], yPoints[pointNumber]);
					deadLines[j] = p;
					deadLines[j+1] = p2;
					pointNumber++;//increase point number
					
					for (int i = 0; i < 2; i++) { //randomly generate a x and y speed for each line 
						deadLineSpeed[j/2][i] = Math.random()*1-0.5;
					}
					debrisLife[j/2] = Math.random()*2.5; //generate a life span for each line
			}
		}
	}
	
	public void updateLives() { //method called to display the lives at the top of the screen
		if(lives != 0) {
			for (int i = 0; i < lives; i++) { //cycle the x to the positions of the lives on screen so I can use one player object and just move it to create the illusion of many of them, almost like an after image
				x+=30;
				setPoints();
				draw();
			}
			
			if(x >= 30*lives) { //if the player is in the last position then reset its position to the beginning 
				x=15;
			}
		}
	}
	
	/* getters and setter */
	public static int getLives() {
		return lives;
	}

	public static void setLives(int lives) {
		Player.lives = lives;
	}

	public GraphicsContext getGc() {
		return gc;
	}

	public void setGc(GraphicsContext gc) {
		this.gc = gc;
	}

	public Canvas getGameCanvas() {
		return gameCanvas;
	}

	public void setGameCanvas(Canvas gameCanvas) {
		this.gameCanvas = gameCanvas;
	}

	public ArrayList<String> getInput() {
		return input;
	}

	public void setInput(ArrayList<String> input) {
		this.input = input;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public boolean isSafeToSpawn() {
		return safeToSpawn;
	}

	public void setSafeToSpawn(boolean safeToSpawn) {
		this.safeToSpawn = safeToSpawn;
	}

	
}

