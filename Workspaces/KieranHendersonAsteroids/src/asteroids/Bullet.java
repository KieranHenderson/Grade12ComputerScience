/* Bullet is a class that allows me to generate bullets, it extends space object and creates a point with a speed, a direction, and a life span. This class allows for the rest of the program to simply create bullets  */
/* Kieran Henderson */
/* 6/7/20 */

//My project is a recreation the classic arcade game asteroids from 1979, 
//there are some aspects of the game that may not be exactly how the original game worked as I have never played the original game but I did my best to include everything I found during research
//a more in depth description can be found at the bottom of the extras text file

package asteroids;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet extends SpaceObject{

	double lifeTime; //how long the bullet has been on screen for 
	double lifeTimer; //how long the bullet can be on screen for
	
	int width; //weight of the bullet 
	int height; //height of the bullet 
	
	private boolean remove = false; //if the bullet should be removed 
	
	public Bullet(GraphicsContext gc, Canvas gameCanvas, double x, double y, double radians, double speed) {
		this.gc = gc;
		this.gameCanvas = gameCanvas;
		
		this.x = x;
		this.y = y; 
		this.radians = radians;
		this.speed = speed;
		
		//set the x speed and y speed of the bullet 
		dx = Math.cos(radians) * speed;
		dy = Math.sin(radians) * speed;
		
		width = height = 4;
		
		lifeTime = 1;
		lifeTimer = 0;
	}
	
	public Bullet(GraphicsContext gc, Canvas gameCanvas, double x, double y) {
		this.gc = gc;
		this.gameCanvas = gameCanvas;
		
		this.x = x;
		this.y = y; 
		
		//set the speed
		speed = 4;
		//set the x and y speed of the bullet 
		dx = Math.random() * speed;
		dy = Math.random() * speed;
		
		width = height = 4;
		
		lifeTime = 1;
		lifeTimer = 0;
	}
	
	public void update() {
		//move the bullet 
		x += dx;
		y += dy;
				
		//if the bullet has been on screen for its life span then remove is true 
		lifeTimer += 0.01;
		if(lifeTimer > lifeTime) {
			remove = true;
		}
		
		//wrap the bullet around the screen
		wrap();

	}
	
	public void draw() { //draw the bullet 
		gc.setFill(Color.WHITE);
		gc.fillOval(x - width / 2, y - height / 2, width/2, height/2);
	}

	public boolean isRemove() {
		return remove;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}
}
