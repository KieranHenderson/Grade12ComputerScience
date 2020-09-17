/* MonkeyGameController is a class that is in charge of controlling all the actions and events that may occur while running the Monkey Game application. This includes collecting both types of bananas, generating both types of bananas, generating both types of power ups, and generating additional coconuts */
/* Kieran Henderson */
/* 4/15/20 */
package monkeygame;
//import the libraries 
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class MonkeyGameController {
	
	//import game canvas, scene and graphics context
	@FXML
	Canvas gameCanvas;
	Scene gameScene;
	GraphicsContext gc;
	
	//declare a true or false state depending on weather the game is over or not
	boolean gameOver = false;
	
	//declare collision variables which see if a collision has occurred
	boolean collidedBanana = false;
	boolean collidedCoconut = false;
	boolean collidedPowerUp = false;
	
	
	int numToNextCoconut = 1; //number of points that must be scored to spawn another coconut
	int powerUpTime = 5; //time in seconds of how long a power up will last
	int timeTillRottenBananaSpawn = 10; //time in seconds of how long it takes a rotten banana to spawn
		
	//time variables 
	long startTime = System.nanoTime()/1_000_000_000; //time the program started 
	long activatedTime = (long) Double.POSITIVE_INFINITY; //initializing the time that a power up was activated as a huge number
	long time;
	long timeOfLastRottenBanana;
	
	//method to get the game scene
	public void getScene(Stage primaryStage) {
		gameScene = primaryStage.getScene();
	}
	
	//main game loop method
	public void gameLoop() {
		gameOver = false;
		gc = gameCanvas.getGraphicsContext2D();//making the graphics context a 2D one 
		Image background = new Image("images/background.png"); //setting the background image
		
		Banana banana = new Banana(gc, gameCanvas); //creating the good banana 
		
		ArrayList<Banana> rottenBananaList = new ArrayList<Banana>(); //rotten banana array list that stores all the rotten banana object
		for (int i = 0; i < Banana.numRottenBananas; i++) { //creates a number of rotten bananas based on the rotten banana variable in the banana class
			rottenBananaList.add(new Banana("images/rottenBanana.png",gc, gameCanvas));
		}
		
		Powers star = new Powers(gc, gameCanvas); //creates the star power up
				
		ArrayList<Coconut> coconutList = new ArrayList<Coconut>(); //coconut array list that stores all the coconut objects
		for (int i = 0; i < Coconut.numCoconuts; i++) { //creates a number of coconuts based of the number of coconuts variable in the coconut class
			coconutList.add(new Coconut(gc,gameCanvas));
		}
		
		ArrayList<String> input = new ArrayList<String>(); //array list to hold all the buttons that are being pressed 
		
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>(){ //when a key is pressed 
			@Override
			public void handle(KeyEvent e) {
				String code = e.getCode().toString();
				if (!input.contains(code)) { //if the input array list does not contain the button that was pressed add it 
					input.add(code);
				} 
				if ("Q".equals(code) && gameOver == true) { //if the button equals q and the game is over then quit 
					System.exit(0);
				} else if ("P".equals(code) && gameOver == true) { //if the button equals p and the game is over restart the game
					gameLoop();
				}
			}
		});
		
		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>(){ //when a key is released 
			@Override
			public void handle(KeyEvent e) {
				String code = e.getCode().toString();
				if (input.contains(code)) { //if the key is in the input list then remove it 
					input.remove(code);
				}
			}
		});
		
		Monkey monkey = new Monkey(gc,gameCanvas,input); //create the monkey object
		
		Score score = new Score(gc,gameCanvas); //create the score object 
		
		new AnimationTimer() {
			public void handle (long currentNanoTime) {
				time = (currentNanoTime / 1_000_000_000) - startTime; //update the game time 

				gc.clearRect(gameCanvas.getWidth(), gameCanvas.getHeight(), 0, 0); //clear the screen
				gc.drawImage(background, 0, 0); //draw the background
				
				banana.drawBanana(); //draw the banana 
				star.drawStar(); //draw the star
				monkey.move(); //move the monkey 
				
				for (int i = 0; i < Banana.numRottenBananas; i++) { //draw each banana in the rotten banana list 
					rottenBananaList.get(i).drawBanana(); 
				}
				
				for (int i = 0; i < Coconut.numCoconuts; i++) { //draw each coconut in the coconut list 
					coconutList.get(i).move();
				}
				
				if (Banana.bananasEaten - Coconut.numCoconuts >= numToNextCoconut) { //check if a new coconut needs to be created and update the variables accordingly
					Coconut.numCoconuts+=1;
					coconutList.add(new Coconut(gc,gameCanvas));
					numToNextCoconut +=1;
				}
				
				if (time % timeTillRottenBananaSpawn == 0 && time != 0 && timeOfLastRottenBanana != time) { //spawn one banana every time that timeTillRottenBanana will fit into the current time without remainder
					Banana.numRottenBananas++;
					rottenBananaList.add(new Banana("images/rottenBanana.png",gc, gameCanvas)); //add one to the number of bananas and create a new banana
					timeOfLastRottenBanana = time; //makes sure there is no repeat, having multiple bananas spawn in the same second
				}
				
				collidedBanana = monkey.collisionBanana(banana); //update the collided banana variable to see if the monkey collided with a banana
				if (collidedBanana) {
					Banana.bananasEaten += 1; //if the monkey collides with a banana then add one to bananas eaten and change the position of the banana
					banana.randomBanana();
				}

				for (int i = 0; i < Banana.numRottenBananas; i++) { //for each rotten banana
					Banana b = rottenBananaList.get(i); //check if the monkey is colliding with it 
					collidedBanana = monkey.collisionBanana(b);
					if (collidedBanana) {
						Banana.bananasEaten -= 1; //if the monkey is colliding with a rotten banana then subtract one from bananas eaten, number of rotten bananas and remove the banana from the rotten banana list 
						Banana.numRottenBananas -=1;
						rottenBananaList.remove(i);
					}
				}

				collidedPowerUp = monkey.collisionPowerUp(star); //check if the monkey collides with the power
				if(collidedPowerUp) { //if it collides with the power up
					activatedTime = time; //set the activated time to the current time 
					star.setX(2000); //move the star off screen so the player can't pick it up again
					star.timesPoweredUp++;
					if (star.getImageName() == "images/star.png") { //if the star is a normal star then set the monkey state to star
						monkey.setState("star");
					} else if (star.getImageName() == "images/rainbowStar.png") { //if the star is a rainbow star then set the monkey state to rStar
						monkey.setState("rStar");
					}
					monkey.update(); //update the monkey
				}
				
				for (int i = 0; i < Coconut.numCoconuts; i++) { //for every coconut 
					Coconut c = coconutList.get(i); 
					collidedCoconut = monkey.collisionCoconut(c); //check collision with the monkey 
					if (collidedCoconut) { //if a coconut collides with the monkey then the monkey loses a life and teleport away
						monkey.lives -= 1;
						monkey.randomXY();
					}
				}
				
				if (time - activatedTime >= powerUpTime) { //if the time that the power up has been active for is greater than or equal to the power up time
					monkey.setState("normal"); //set the monkey state back to normal and update the monkey
					monkey.update();
					star.randomStar(); //move the star to a random location on the screen
					if ((star.timesPoweredUp + 1) % 3 == 0) { //if the power up has been collected 3 times then switch it to the rainbow star 
						star.setImageName("images/rainbowStar.png");
					} else {
						star.setImageName("images/star.png"); //else its a normal star
					}                                                                                                                                
					star.updateImage(); //update the star
					activatedTime = (long) Double.POSITIVE_INFINITY; //reset the activated time to infinity
				}
				score.display(monkey); //display the score 
				
				if(monkey.lives == 0) { //if the monkey loses all its lives
					score.deathScreen(); //display the death screen
					Coconut.numCoconuts = 1; //reset all the base variables to prepare for a new game
					monkey.lives = 3;
					Banana.bananasEaten = 0;
					gameOver = true;
					this.stop(); //leave the game loop
				}
			}
		}.start();
	}
	
}
