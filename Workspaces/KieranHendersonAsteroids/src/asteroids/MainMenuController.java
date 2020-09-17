/* Main menu controller is the controller for the main menu screen, it is in charge of all the events that take place on the main menu screen, it extends screens as it is one of the four screens
 * and it implements Initializable because it is called when the scene is loaded and implements controlled screen because it needs to be a controlled screen in the screen controller to allow for the scene to be changed */
/* Kieran Henderson */
/* 6/7/20 */

//My project is a recreation the classic arcade game asteroids from 1979, 
//there are some aspects of the game that may not be exactly how the original game worked as I have never played the original game but I did my best to include everything I found during research
//a more in depth description can be found at the bottom of the extras text file

package asteroids;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class MainMenuController extends Screens implements Initializable, ControlledScreen {
	
	@FXML 
	Text name;
	
	//initializing all my objects 
	ArrayList<Asteroid> startAsteroidList; 

	Enemy enemy;

	ArrayList<Bullet> enemyBulletList;
	
	DisplayText displayScore;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) { //runs when the screen is loaded the first time 
		gc = gameCanvas.getGraphicsContext2D();
		
		startAsteroidList = new ArrayList<Asteroid>(); //asteroid array list that stores all the asteroid objects
		for (int i = 0; i < Asteroid.getStartScreenAsteroids(); i++) { //creates a number of asteroid based of the number of the start screen asteroid variable in the asteroid class
			startAsteroidList.add(new Asteroid("big",gc,gameCanvas));
		}
						
		enemy = new Enemy(gc,gameCanvas, enemyBulletList); //create a new enemy 
		enemy.setActive(true); //set the enemy to be active 
		enemy.setMainMenu(true);
		
		displayScore = new DisplayText(gc, gameCanvas, 28); //display a score in the top left
		
		loop(); //start the main loop
	}
	
public void loop() { //main loop of the class
		new AnimationTimer() {
			@Override
			public void handle(long currentNanoTime) {	
				System.out.println(name);
				if(isMainPause() == false) { //if the main menu is not paused 
				gc.setFill(Color.BLACK);
				gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight()); //refresh the screen by making it black
						
				//update everything
				update();
				
				//draw everything
				draw();
				}
			}			
		}.start();
	}

	public void draw(){
		enemy.draw(); //draw the enemy 
		
		for (int i = 0; i < startAsteroidList.size(); i++) { //draw each asteroids in the asteroid list 
			startAsteroidList.get(i).draw();
		}

		displayScore.displayLeft(Integer.toString(100000), 20, 55); //display a score of 100000 in the top left 
		
	}
	
	public void update() {		
			for (int i = 0; i < startAsteroidList.size(); i++) { //update each asteroids in the asteroid list 
				startAsteroidList.get(i).update();
			}
			
		enemy.update(); //update the enemy 
	}
}
