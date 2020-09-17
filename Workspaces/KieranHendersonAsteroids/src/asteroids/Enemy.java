/* Enemy is a class that holds methods for creating enemy objects throughout out the program, it also extends space object so it can access it methods and variables */
/* Kieran Henderson */
/* 6/7/20 */

//My project is a recreation the classic arcade game asteroids from 1979, 
//there are some aspects of the game that may not be exactly how the original game worked as I have never played the original game but I did my best to include everything I found during research
//a more in depth description can be found at the bottom of the extras text file

package asteroids;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Enemy extends SpaceObject {
	
	int scoreValue; //how much score the player receives when the kill the enemy 

	boolean active; //if the enemy is active 
	
	boolean exit = false; //if the enemy should exit the screen
	
	boolean mainMenu; //if the enemy is on the main menu or not 
	
	private final int BULLET_MAX = 1; //how many bullets the enemy can have on the screen
	
	ArrayList<Bullet> enemyBulletList; //list of enemy bullets 
	double bulletSpeed;
	double speed = 1; //speed of the enemy
	
	double maxHeightDiff; //variable to track the max height the enemy can travel before having its speeds randomized 
	double yStart; //the start y of the enemy / the y of the enemy when it changes speeds 
	
	double width;
	
	String size = "large"; //the size of the enemy which can be either small or large, the large has a bigger hit box and randomly shoots, the small has a smaller hit box and pin point accurate shots towards the player
	 //the small one rarely appears at the beginning of the game and the likelihood that it appears becomes greater the closer you get to 40 000 points at which point it is a 100% chance
	
	Point[] debris = new Point[20]; //array to hold all the debris pieces 
	double[][] debrisSpeed = new double[debris.length][2]; //array that holds the speeds of each piece of debris 
	double[] debrisLife = new double[debris.length]; //array that hold the life span of each piece of debris 
	double lifeTimer = 0; //tracks how long each piece of debris should be on screen for 
	
	Random random = new Random(); //make a random object

	int respawnTime = random.nextInt(10)+30; //how long it will take the enemy to re-spawn

	
	public Enemy(GraphicsContext gc, Canvas gameCanvas, ArrayList<Bullet> enemyBulletList) {
		this.gc = gc;
		this.gameCanvas = gameCanvas;		
		this.enemyBulletList = enemyBulletList;
		
		maxHeightDiff = 200+Math.random()*200-100; //generate the max height difference 
		randomStart(); //randomly spawn the enemy 
		
		while(this.dx < speed - 0.5 && this.dx > - speed + 0.5) { //randomly generate the x speed of the enemy 
			this.dx = Math.random()*(speed*2)-speed;
		}
		while(this.dy < speed - 0.5 && this.dy > - speed + 0.5) { //randomly generate the y speed of the enemy 
			this.dy = Math.random()*(speed*2)-speed;
		}

		this.yStart = y; //set the start y 
		
		xPoints = new double[10]; //array of the x points 
		yPoints = new double[10]; //array of the y points 
		
		scoreValue = 200; //set the score value of the enemy
		
		//set all the points 
		setPoints(); 
		
	}
	
	private void setPoints() {
		if(active) { //if the enemy is active / on screen
			if(size == "large") { //set the points according to the x values
				xPoints[0] = x;
				yPoints[0] = y;
		
				xPoints[1] = x+7;
				yPoints[1] = y-7;
				
				xPoints[2] = x+15;
				yPoints[2] = y-7;
				
				xPoints[3] = x+20;
				yPoints[3] = y-14;
				
				xPoints[4] = x+30; 
				yPoints[4] = y-14;
				
				xPoints[5] = x+35;
				yPoints[5] = y-7;
				
				xPoints[6] = x+42.5;
				yPoints[6] = y-7;
				
				xPoints[7] = x+50;
				yPoints[7] = y;
				
				xPoints[8] = x+40;
				yPoints[8] = y+7;
				
				xPoints[9] = x+10;
				yPoints[9] = y+7;
			} else {
				xPoints[0] = x;
				yPoints[0] = y;
		
				xPoints[1] = x+5.5;
				yPoints[1] = y-3.5;
				
				xPoints[2] = x+7;
				yPoints[2] = y-3.5;
				
				xPoints[3] = x+10;
				yPoints[3] = y-9;
				
				xPoints[4] = x+17; 
				yPoints[4] = y-9;
				
				xPoints[5] = x+20;
				yPoints[5] = y-3.5;
				
				xPoints[6] = x+21.5;
				yPoints[6] = y-3.5;
				
				xPoints[7] = x+27;
				yPoints[7] = y;
				
				xPoints[8] = x+21.5;
				yPoints[8] = y+3.5;
				
				xPoints[9] = x+5.5;
				yPoints[9] = y+3.5;
			}
			
			width = (xPoints[7] - xPoints[0])/2; //width is equal to the distance between the left most point and right most point / 2
			
		} 
		
		else if (debris[0] != null) { //if the enemy has been killed 
			for (int i = 0; i < debris.length; i++) { //update the debris positions 
				debris[i].x += debrisSpeed[i][0];
				debris[i].y += debrisSpeed[i][1];
			}
		}
	}
	
	public void update() {
		
		if (!active) { //increment the life timer
			lifeTimer+=.035;
		}
		

		
		if (lifeTimer >= respawnTime && !active) { //if the correct amount of time has passed for the enemy to re-spawn
			active = true;
			lifeTimer = 0;
			respawnTime = random.nextInt(10)+30;
			randomStart();
		}
		
		for (int i = 0; i < xPoints.length; i++) {
			xPoints[i]+=dx;
			yPoints[i]+=dy;
		}
		
		//update the enemy x and y positions 
		x+=dx;
		y+=dy;

		//changing the y value to make for a harder target
		if (yStart - maxHeightDiff >= yPoints[0] || yStart + maxHeightDiff <= yPoints[0]) { //if the enemy has changed its y position more than the max height difference
			
			do { //randomly assign it a new x and y speed 
				this.dx = Math.random()*(speed*2)-speed;
			} while(this.dx < speed - 0.5 && this.dx > - speed + 0.5);
			
			do {
				this.dy = Math.random()*(speed*2)-speed;
			} while(this.dy < speed - 0.5 && this.dy > - speed + 0.5);

			yStart = yPoints[0]; //update the yStart 
			maxHeightDiff = 200+Math.random()*200-100;//update the max height difference 
		}

		//shoot
		if(active && !exit && !mainMenu) {
			shoot();
		}
		
		//set bulletSpeed which is used exclusively for the small enemy
		bulletSpeed = 3+score/10000;
		
		//setting the new points
		setPoints();

		
		if(!active) {
			updateSize(); //check to see what size it should be
			exit = false; //reset the exit variable 
			
			while(this.dx < speed - 0.5 && this.dx > - speed + 0.5) { //update the x speed 
				this.dx = Math.random()*(speed*2)-speed;
			}
			
			while(this.dy < speed - 0.5 && this.dy > - speed + 0.5) { //update the y speed 
				this.dy = Math.random()*(speed*2)-speed;
			}
		}

		if(exit) { //if the enemy should exit 
			if(x-width >= gameCanvas.getWidth() + 500) { //if the enemy if off screen kill it 
				die();
			}

		} 
		
		else { 
			//wrap
			cleanWrap();
		}
	}
	
	public void draw() {
		gc.setStroke(Color.WHITE);
		gc.beginPath();
		
		//draw spaceship 
		if(active) {
			for (int i = 0, j = xPoints.length-1; i < xPoints.length; j = i++) { //draw the outline of the enemy 
				gc.strokeLine(xPoints[i], yPoints[i], xPoints[j], yPoints[j]);
			}
			gc.strokeLine(xPoints[0], yPoints[0], xPoints[7], yPoints[7]); //lines going through the enemy 
			gc.strokeLine(xPoints[2], yPoints[2], xPoints[5], yPoints[5]);
		} 
		
		//draw the debris 
		else { 
			for (int i = 0; i < debris.length; i++) {
				if(lifeTimer <= debrisLife[i]) { //if the debris life span has not run out
					gc.setFill(Color.WHITE);//draw the debris 
					gc.fillOval(debris[i].x, debris[i].y, 2, 2);
				}
			}
		}
		gc.closePath();
	}	
	
	public void die() { 
		active = false; 
		speed+=score/10000; //increase the speed of the enemy as the players score increases 
		
		for (int i = 0; i < debris.length; i++) { //for each piece of debris 
			Point p = new Point(x,y); //generate a new point at the position of the enemy 
			debris[i] = p; //add the point to the debris list 
			debrisSpeed[i][0] = Math.random()*4-2; //generate the x speed for the debris 
			debrisSpeed[i][1] = Math.random()*4-2; //generate the y speed for the debris 
			debrisLife[i] = Math.random()*1.5; //generate the life span of the debris 
		}
	}
	
	public void shoot() {
		if(enemyBulletList.size() == BULLET_MAX) { //if there are already enough bullets then return 
			return;
		}
		
		if(size == "large") {
			enemyBulletList.add(new Bullet(gc, gameCanvas, x+width, y)); //generate a bullet with random speeds 
		} 
		
		else { //if the size is small
			enemyBulletList.add(new Bullet(gc, gameCanvas, x+width, y, radians, bulletSpeed)); //shoot a bullet directly at the player 
		}
	}
	
	public void updateShotAngle(SpaceObject o) { //calculate the angle between the player and the enemy using simple trigonometry 
		double startX = this.x+width;
		double startY = this.y;
		double destinationX = o.x;
		double destinationY = o.y;
		double changeX = destinationX - startX;
		double changeY = (destinationY - startY);
		radians = Math.atan2(changeY,changeX);
	}
	
	public void updateSize() { //update the size of the enemy
		double smallChance = (score/400); //the chance for the enemy to be small
		
		if(Math.random()*100 < smallChance) {
			size = "small";
			scoreValue = 1000;
		}
		
		else {
			size = "large";
			scoreValue = 200;
		}
	}
	
	public void leaveScreen() { //have the enemy leave the screen
		exit = true;
		dy = 0;
		dx = 3;
		
	}

	/*getters and setters */
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getScoreValue() {
		return scoreValue;
	}

	public void setScoreValue(int scoreValue) {
		this.scoreValue = scoreValue;
	}

	public boolean isMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(boolean mainMenu) {
		this.mainMenu = mainMenu;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
}
